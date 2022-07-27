package bm.app.springsecurityjwtdemo.service;

import bm.app.springsecurityjwtdemo.model.Post;
import bm.app.springsecurityjwtdemo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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
     * A method with a custom query to bypass fetching every record with a separate query.
     */
    public List<Post> getPostsWithCustomQuery() {
        return postRepository.findAllPosts();
    }
}
