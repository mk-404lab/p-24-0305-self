package com.back.domain.post.controller;

import com.back.domain.post.entity.Post;
import com.back.domain.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
        return getWriteForm("", "", "", "");
    }

    @AllArgsConstructor
    @Getter
    public static class WriteRequestForm {
        // 1. 액션 메서드의 입력값을 객체로 모아 받는다.
        // 이때 유효성 조건을 검사하기 위해 생성하는 필드는 액션의 name 값과 동일해야 함

        @NotBlank
        @Size(min=2, max=10)
        private String title;

        @NotBlank
        @Size(min=2, max=100)
        private String content;
    }

    @PostMapping("/posts/write")
    @ResponseBody
    public String write(@Valid WriteRequestForm form,
                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String fieldName = bindingResult.getFieldError().getField();

            String errorMessage = bindingResult.getFieldError().getDefaultMessage();

            return getWriteForm(errorMessage, form.title, form.content, fieldName);
        }

        Post post = postService.write(form.title, form.content);

        return "%d번 글이 작성되었습니다.".formatted(post.getId());
    }


    private String getWriteForm(String errorMessage, String title, String content, String errorFieldName) {
        return """
                <div style="color:red">%s</div>
                <form method="post" action="/posts/write">
                  <input type="text" name="title" value="%s" autoFocus>
                  <br>
                  <textarea name="content">%s</textarea>
                  <br>
                  <input type="submit" value="작성">
                </form>
                
                <script>
                    const errorFieldName = "%s"; 
                    if(errorFieldName.length > 0) {
                        const form = document.querySelector("form");
                        form[errorFieldName].focus();
                    }
                </script>
                """.formatted(errorMessage, title, content, errorFieldName);
    }
}
