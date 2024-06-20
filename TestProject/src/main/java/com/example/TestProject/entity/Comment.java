package com.example.TestProject.entity;

import com.example.TestProject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="article_id") //외래키생성, Article 엔티티의 기본키와 매핑
    private Article article; // 댓글의 부모 게시글
    @Column
    private String nickname; //댓글 단 사람
    @Column
    private String body; //댓글본문

    public static Comment createComment(CommentDto dto, Article article) {
        //예외발생
        if(dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        if(dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐습니다.");
        //엔티티 생성 및 반환
        return new Comment(
                dto.getId(), //아이디
                article, //게시글
                dto.getNickname(), //닉네임
                dto.getBody() //본문
        );
    }

    public void patch(CommentDto dto) {
        //예외 발생
        if(this.id !=dto.getId())
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다.");
        //객체 갱신
        if(dto.getNickname() != null) //수정할 닉네임 데이터
            this.nickname = dto.getNickname(); //내용 반영
        if(dto.getBody()!= null) //수정할 본문 데이터
            this.body = dto.getBody(); //내용 반영
    }
}
