package io.danielegradassai.controller;

import io.danielegradassai.dto.comment.CommentOutputDto;
import io.danielegradassai.dto.vote.VoteInputDto;
import io.danielegradassai.dto.vote.VoteOutputDto;
import io.danielegradassai.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<VoteOutputDto> voteArticle(@RequestBody VoteInputDto voteInputDto) {
        return new ResponseEntity<>(voteService.voteArticle(voteInputDto), HttpStatus.CREATED);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<List<VoteOutputDto>> getVoteByArticleId(@PathVariable Long articleId) {
        List<VoteOutputDto> votes = voteService.getVotesByArticleId(articleId);
        return new ResponseEntity(votes , HttpStatus.OK);
    }
}
