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
        if (voteUser != null) {
            // CONDIZIONE SE MI PIACE E NON MI PIACE ENTRAMBI SU FALSE
            if (!liked && !disliked) {
                voteRepository.delete(voteUser);
                updateVoteCounts(article);
                return null;
            }

            // PER AZZERRARE CONTEGGIO
            if (liked && voteUser.isDisliked()) {
                voteUser.setDisliked(false);
                updateVoteCounts(article);
            }
            else if (disliked && voteUser.isLiked()) {
                voteUser.setLiked(false);
                updateVoteCounts(article);
            }
            // SE CLICCO DUE VOLTE STESSO VOTO
            else if (liked && voteUser.isLiked()) {
                voteRepository.delete(voteUser);
                updateVoteCounts(article);
                return null;
            } else if (disliked && voteUser.isDisliked()) {
                voteRepository.delete(voteUser);
                updateVoteCounts(article);
                return null;
            }

            // PER AGGIORNARE VOTO SE DIVERSO
            voteUser.setLiked(liked);
            voteUser.setDisliked(disliked);
            voteUser = voteRepository.save(voteUser);
            updateVoteCounts(article);
        } else {
            if (liked || disliked) {
                voteUser = new Vote(liked, disliked, user, article);
                voteUser = voteRepository.save(voteUser);
                updateVoteCounts(article);
            } else {
                return null;
            }
        }
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



