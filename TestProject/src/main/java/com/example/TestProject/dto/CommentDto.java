package com.example.TestProject.dto;

import com.example.TestProject.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString

public class CommentDto {
    private Long id; //댓글 id
    private Long articleId; //댓글 부모 id
    private String nickname; //작성자
    private String body; //본문

    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(), //엔티티의 id
                comment.getArticle().getId(), //엔티티의 부모 id
                comment.getNickname(), // 댓글엔티티의 nickname
                comment.getBody() //댓글 엔티티의 body
        );
    }
}
