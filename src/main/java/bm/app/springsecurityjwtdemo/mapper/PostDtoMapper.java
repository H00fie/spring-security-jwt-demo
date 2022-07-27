package bm.app.springsecurityjwtdemo.mapper;

import bm.app.springsecurityjwtdemo.controller.dto.PostDto;
import bm.app.springsecurityjwtdemo.model.Post;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A mapper from Post to its Dto that allows me to prevent the comments being read
 * for every Post and thus escape the "N+1" problem with many queries being
 * generated.
 */
public class PostDtoMapper {

    /**
     * A private constructor is a good practice to prevent anyone from creating an
     * object of a class I do not want to have objects of. A mapper does not need
     * to be instantiated, it does not need multiple objects of itself.
     * For the same reason its methods are static.
     */
    private PostDtoMapper() {

    }

    public static List<PostDto> mapToPostDtos(List<Post> posts) {
        return posts.stream()
                .map(post -> mapToPostDto(post))
                .collect(Collectors.toList());
    }

    private static PostDto mapToPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .created(post.getCreated())
                .build();
    }
}