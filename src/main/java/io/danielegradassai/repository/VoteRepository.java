package io.danielegradassai.repository;

import io.danielegradassai.entity.Article;
import io.danielegradassai.entity.User;
import io.danielegradassai.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Vote findByUserAndArticle(User user, Article article);
    int countByArticleAndLiked(Article article, boolean liked);
    int countByArticleAndDisliked(Article article, boolean disliked);
    List<Vote> findByArticleId(Long articleId);
}
