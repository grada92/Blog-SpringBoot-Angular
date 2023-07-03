package io.danielegradassai.service;

import io.danielegradassai.dto.vote.VoteInputDto;
import io.danielegradassai.dto.vote.VoteOutputDto;
import io.danielegradassai.entity.Article;

import java.util.List;

public interface VoteService {

    VoteOutputDto voteArticle(VoteInputDto voteInputDto);

    List<VoteOutputDto> getVotesByArticleId(Long articleId);
}
