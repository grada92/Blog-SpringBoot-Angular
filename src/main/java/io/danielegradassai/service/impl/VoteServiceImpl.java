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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
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
    @Transactional
    public VoteOutputDto voteArticle(VoteInputDto voteInputDto) {
        User user = userRepository.findById(voteInputDto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));

        Article article = articleRepository.findById(voteInputDto.getArticleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non trovato"));

        Vote existingVote = voteRepository.findByUserAndArticle(user, article);
        boolean liked = voteInputDto.isLiked();

        // SE UTENTE HA GIA' VOTATO
        if (existingVote != null) {
            if (liked == existingVote.isLiked()) {
                voteRepository.delete(existingVote);
                updateVoteCounts(article);

                if (liked) {
                    user.getLikedArticles().remove(article);
                }

                userRepository.save(user);

                return null;
            }

            existingVote.setLiked(liked);
            existingVote = voteRepository.save(existingVote);
        }
        else {
            if (liked) {
                existingVote = new Vote(true, false, user, article);
                existingVote = voteRepository.save(existingVote);
                user.getLikedArticles().add(article);
                userRepository.save(user);
            } else {
                return null;
            }
        }

        updateVoteCounts(article);

        VoteOutputDto voteOutputDto = modelMapper.map(existingVote, VoteOutputDto.class);
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



