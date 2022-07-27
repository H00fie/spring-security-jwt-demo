package bm.app.springsecurityjwtdemo.controller;

import bm.app.springsecurityjwtdemo.controller.dto.PostDto;
import bm.app.springsecurityjwtdemo.mapper.PostDtoMapper;
import bm.app.springsecurityjwtdemo.model.Post;
import bm.app.springsecurityjwtdemo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * The below acts similarly to the above, but, thanks to the usage of the Dto that does not
     * contain the list of comments, only posts will be loaded with the accordance to the set
     * pagination and no additional queries will be generated for every comment of every post.
     * Thus, "N+1" is averted.
     */
    @GetMapping("/custom_posts_no_join_dto")
    public List<PostDto> getPostByDto() {
        return PostDtoMapper.mapToPostDtos(postService.getPostsWithCustomQueryWithoutJoin());
    }
}


