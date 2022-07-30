package bm.app.springsecurityjwtdemo.controller;

import bm.app.springsecurityjwtdemo.controller.dto.PostDto;
import bm.app.springsecurityjwtdemo.mapper.PostDtoMapper;
import bm.app.springsecurityjwtdemo.model.Post;
import bm.app.springsecurityjwtdemo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

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

    /**
     * A similar method to the above, but allowing the user to specify of which page they want
     * the records to load.
     * RequestParam annotation allows to get the parameter from the request.
     * The parameter "page" needs to be a reference and not a primitive type because it's supposed to be an optional
     * parameter and, if left a primitive and without it being provided, I'd get an error saying that a null cannot
     * be assigned to it.
     * The parameter is optional and, when not specified, Spring attempts to give it the value of null... so it needs
     * to be an object type.
     */
    @GetMapping("/custom_posts_no_join_dto_param")
    public List<PostDto> getPostByDtoCustomParam(@RequestParam(required = false) Integer page, Sort.Direction sort) {
        int pageNumber = page != null && page >= 0 ? page : 0; //Negative input will just trigger the first page to load.
        return PostDtoMapper.mapToPostDtos(postService.getPostsWithCustomQueryWithoutJoinWithCustomParam(pageNumber, sort));
    }

    /**
     * A method for actually loading the posts WITH comments - with pagination and of optimal performance.
     */
    @GetMapping("/posts/comments")
    public List<Post> getPostsWithComments(@RequestParam(required = false) int page, Sort.Direction sort) {
        int pageNumber = page >= 0 ? page : 0; //Negative input will just trigger the first page to load.
        return postService.getPostsWithComments(pageNumber, sort);
    }

}