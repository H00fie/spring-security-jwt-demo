package bm.app.springsecurityjwtdemo.service;

import bm.app.springsecurityjwtdemo.model.Post;
import bm.app.springsecurityjwtdemo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    /**
     * A method using a Jpa's standard method for getting all results (generates a separate query for every
     * record.
     */
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    /**
     * .findById() returns an Optional with an object. If there's no object to be returned .orElseThrow()
     * will throw the NoSuchElementException("No value present") exception.
     */
    public Post getSinglePost(long id) {
        return postRepository.findById(id)
                .orElseThrow();
    }

    /**
     * A method with a custom query to bypass fetching every record with a separate query. PageRequest
     * allows for the pagination. Firstly, the repository method took a Pageable object as a parameter
     * which allows me to work with the pagination. Method .of() allows me to set what pages I want loaded.
     * Below I want just the first page (indexing from 0) and have 5 posts there.
     */
    public List<Post> getPostsWithCustomQuery() {
        return postRepository.findAllPosts(PageRequest.of(0, 1));
    }

    public List<Post> getPostsWithCustomQueryWithoutJoin() {
        return postRepository.findAllPostsWithoutJoin(PageRequest.of(0, 1));
    }
}
