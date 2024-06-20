package com.example.TestProject.controller;

import com.example.TestProject.dto.CommentDto;
import com.example.TestProject.dto.FirstForm;
import com.example.TestProject.entity.Article;
import com.example.TestProject.repository.ArticleRepository;
import com.example.TestProject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
@Slf4j
@Controller
public class FirstController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService; //서비스 객체 주입
    @GetMapping("/first/new")
    public String newFirstForm(){
        return "first/new";
    }

    @PostMapping("/first/create")
    public String createFirst(FirstForm form){
        System.out.println(form.toString());
        //1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        System.out.println(article.toString());
        //2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        System.out.println(saved.toString());
        return "redirect:/first/" + saved.getId(); //리다이렉트 작성
    }
    @GetMapping("/first/{id}") // 데이터 조회 요청 접수
    public String show(@PathVariable Long id, Model model){ // 매개변수로 id 받아 오기
        log.info("id = " + id);
        //1.
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentsDtos = commentService.comments(id);
        //2. 모델에 데이터 등록
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentsDtos); // 댓글 목록 모델에 등록
        //3. 뷰 페이지 설정
        return "first/show";
         }
         @PreAuthorize("hasRole('USER')")
         @GetMapping("/first")
         public String index(Model model){
            //1.
             List<Article> articleEntityList = articleRepository.findAll();
            //2.
             model.addAttribute("articleList", articleEntityList);
            //3.
             return "first/index";

         }
         @GetMapping("/first/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //모델에 데이터 등록
        model.addAttribute("article", articleEntity);

        //뷰페이지
             return "first/edit";
         }
         @PostMapping("first/update")
        public String update(FirstForm form){
            //1. dto를 엔티티로 변환
             Article articleEntity = form.toEntity();
             log.info(articleEntity.toString());
            //2. 엔티티를 db에 저장
            //2-1 db에 기존 데이터 가져오기
            Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
            //2-2 기존 데이터 값을 갱신하기
             if(target != null){
                 articleRepository.save(articleEntity); //엔티티를 db에 저장
             }
            //3. 수정 결과 페이지로 리다이렉트
             return "redirect:/first/" + articleEntity.getId();
         }
         @GetMapping("/first/{id}/delete")
        public String delete(@PathVariable Long id, RedirectAttributes rttr){
            log.info("삭제요청이 들어옴");
            //1. 삭제할 대상 가져오기
             Article target = articleRepository.findById(id).orElse(null);
             log.info(target.toString());
            //2. 대상 엔티티 삭제하기
             if(target != null){
                 articleRepository.delete(target);
                 rttr.addFlashAttribute("msg", "삭제됐습니다!");
             }
            //3. 결과 페이지로 리다이렉트 하기
            return "redirect:/first";
         }
}
