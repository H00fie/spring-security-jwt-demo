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

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

}
