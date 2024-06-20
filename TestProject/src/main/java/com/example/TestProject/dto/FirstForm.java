package com.example.TestProject.dto;

import com.example.TestProject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // 새 어노테이션 추가
@ToString
public class FirstForm {
    private Long id;
    private String title; //제목을 받을 필드
    private String content; //내용을 받을 필드





    public Article toEntity() {

        return new Article(id, title, content);
    }
}
