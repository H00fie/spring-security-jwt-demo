package bm.app.springsecurityjwtdemo.controller;

import bm.app.springsecurityjwtdemo.model.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class PostController {

    @GetMapping("/posts")
    public List<Post> getPost() {
        throw new IllegalArgumentException("Not implemented yet!");
    }

    @GetMapping("/posts/{id}")
    public Post getSinglePost(@PathVariable long id) {
        throw new IllegalArgumentException("Not implemented yet!");
    }
}
