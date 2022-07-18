package bm.app.springsecurityjwtdemo.repository;

import bm.app.springsecurityjwtdemo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
