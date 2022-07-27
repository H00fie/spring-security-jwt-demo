package bm.app.springsecurityjwtdemo.controller;

import bm.app.springsecurityjwtdemo.model.Post;
import bm.app.springsecurityjwtdemo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public List<Post> getPost() {
        return postService.getPosts();
    }

    @GetMapping("/posts/{id}")
    public Post getSinglePost(@PathVariable long id) {
        return postService.getSinglePost(id);
    }

    @GetMapping("/custom_posts")
    public List<Post> getPostWithCustomQuery() {
        return postService.getPostsWithCustomQuery();
    }

    @GetMapping("/custom_posts_no_join")
    public List<Post> getPostWithCustomQueryWithoutJoin() {
        return postService.getPostsWithCustomQueryWithoutJoin();
    }
}


