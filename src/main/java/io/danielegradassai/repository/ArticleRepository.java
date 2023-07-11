package io.danielegradassai.repository;

import io.danielegradassai.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByIsApproved(boolean isApproved);
    List<Article> findByIsApprovedOrderByCreatedAtDesc(boolean isApproved);
    List<Article> findTop5ByOrderByLikeCountDesc();

}
