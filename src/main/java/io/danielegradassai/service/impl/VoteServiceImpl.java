package io.danielegradassai.service.impl;

import io.danielegradassai.dto.comment.CommentOutputDto;
import io.danielegradassai.dto.vote.VoteInputDto;
import io.danielegradassai.dto.vote.VoteOutputDto;
import io.danielegradassai.entity.Article;
import io.danielegradassai.entity.Comment;
import io.danielegradassai.entity.User;
import io.danielegradassai.entity.Vote;
import io.danielegradassai.repository.ArticleRepository;
import io.danielegradassai.repository.UserRepository;
import io.danielegradassai.repository.VoteRepository;
import io.danielegradassai.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public VoteOutputDto voteArticle(VoteInputDto voteInputDto) {
        User user = userRepository.findById(voteInputDto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));

        Article article = articleRepository.findById(voteInputDto.getArticleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non trovato"));

        Vote voteUser = voteRepository.findByUserAndArticle(user, article);
        boolean liked = voteInputDto.isLiked();
        boolean disliked = voteInputDto.isDisliked();

        // Se l'utente ha già votato l'articolo
        if (voteUser != null) {
            // Se il nuovo voto è uguale al voto esistente, rimuovi il voto
            if ((liked && voteUser.isLiked()) || (disliked && voteUser.isDisliked())) {
                voteRepository.delete(voteUser);
                updateVoteCounts(article);
                return null;
            }

            // Altrimenti, aggiorna il voto esistente con i nuovi valori
            voteUser.setLiked(liked);
            voteUser.setDisliked(disliked);
            voteUser = voteRepository.save(voteUser);
        }
        // Se l'utente non ha ancora votato l'articolo, crea un nuovo voto
        else {
            if (liked || disliked) {
                voteUser = new Vote(liked, disliked, user, article);
                voteUser = voteRepository.save(voteUser);
            } else {
                return null;
            }
        }

        updateVoteCounts(article);

        VoteOutputDto voteOutputDto = modelMapper.map(voteUser, VoteOutputDto.class);
        voteOutputDto.setUserId(user.getId());
        voteOutputDto.setArticleId(article.getId());

        return voteOutputDto;
    }


    public void updateVoteCounts(Article article) {
        int likeCount = voteRepository.countByArticleAndLiked(article, true);
        int dislikeCount = voteRepository.countByArticleAndDisliked(article, true);
        article.setLikeCount(likeCount);
        article.setDislikeCount(dislikeCount);
        articleRepository.save(article);
    }

    @Override
    public List<VoteOutputDto> getVotesByArticleId(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non trovato"));

        List<Vote> votes = voteRepository.findByArticleId(articleId);

        return votes.stream()
                .map(vote -> modelMapper.map(vote, VoteOutputDto.class)).toList();
    }


}



