package bm.app.springsecurityjwtdemo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {

    @Id
    private long id;
    private String title;
    private String content;
    private LocalDateTime created;

    /**
     * Hibernate firstly requests the posts and then requests the comments for each post because comments are
     * loaded lazily. First we have a list of all posts and only when Jackon (the mapper that serializes everything
     * into JSON) calls a getter on the comment field, the comments are loaded.
     * There's "N1 problem" related to that.
     */
    @OneToMany
    @JoinColumn(name = "postId")
    private List<Comment> comment;
}
