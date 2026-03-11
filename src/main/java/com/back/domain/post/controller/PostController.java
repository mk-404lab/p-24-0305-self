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

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping("/posts/write-form")
    public String writeForm() {
        return "write";
    }

    @AllArgsConstructor
    @Getter
    public static class WriteRequestForm {
        // 1. 액션 메서드의 입력값을 객체로 모아 받는다.
        // 이때 유효성 조건을 검사하기 위해 생성하는 필드는 액션의 name 값과 동일해야 함

        @NotBlank(message = "1 - 제목은 필수입니다.")
        @Size(min=2, max=10, message = "2 - 제목은 2자 이상 10자 이하로 입력해주세요.")
        private String title;

        @NotBlank(message = "3 - 내용은 필수입니다.")
        @Size(min=2, max=100, message = "4 - 내용은 2자 이상 100자 이하로 입력해주세요.")
        private String content;
    }

    @PostMapping("/posts/write")
    public String write(@Valid WriteRequestForm form,
                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            String errorMessages = bindingResult.getFieldErrors().stream()
                    .map((fieldError) -> fieldError.getField()
                    + "-" + fieldError.getDefaultMessage())
                    .map((message) -> {
                        String[] bits = message.split("-");
                        return "<!-- %s --> <li data-error-field=\"%s\">%s</li>".formatted(bits[1], bits[0], bits[2]);
                    })
                    .sorted()
                    .collect(Collectors.joining("\n"));

            return "write";
        }

        Post post = postService.write(form.title, form.content);

        return "%d번 글이 작성되었습니다.".formatted(post.getId());
    }

}
