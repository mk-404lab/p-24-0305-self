package com.back.domain.post.controller;

import com.back.domain.post.entity.Post;
import com.back.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping("/posts/write-form")
    @ResponseBody
    public String writeForm() {
        return getWriteForm();
    }


    @PostMapping("/posts/write")
    @ResponseBody
    public String write(String title, String content) {

        //유효성 체크
        if (title.isBlank()) {
            return """
                    <div style="color:red">제목을 입력해주세요.</div>
                    %s
                    """.formatted(getWriteForm());

        }

        if (content.isBlank()) {
            return """
                    <div style="color:red">내용을 입력해주세요.</div>
                    %s
                    """.formatted(getWriteForm());
        }

        Post post = postService.write(title, content);

        return "%d번 글이 작성되었습니다.".formatted(post.getId());
    }


    private String getWriteForm() {
        return """
                <form method="post" action="/posts/write">
                  <input type="text" name="title">
                  <br>
                  <textarea name="content"></textarea>
                  <br>
                  <input type="submit" value="작성">
                </form>
                """;
    }
}
