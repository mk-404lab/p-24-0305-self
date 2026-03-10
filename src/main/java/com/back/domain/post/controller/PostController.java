package com.back.domain.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {


    @GetMapping("/posts/write-form")
    @ResponseBody
    public String writeForm() {
        return """
                <form action="http://localhost:8080/posts/write">
                  <input type="text" name="title">
                  <br>
                  <textarea name="content"></textarea>
                  <br>
                  <input type="submit" value="작성">
                </form>
                """;
    }


    @GetMapping("/posts/write")
    @ResponseBody
    public String write() {
        return "글이 작성되었습니다.";
    }
}
