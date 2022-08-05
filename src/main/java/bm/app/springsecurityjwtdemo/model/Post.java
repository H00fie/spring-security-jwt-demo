package bm.app.springsecurityjwtdemo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {

    @Id
    /**
     * @GeneratedValue tells Hibernate to increment the value of id every time a new post is created. The IDENTITY
     * type makes Hibernate set the entity's id based on the database table's column so my primary key.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;
    private LocalDateTime created;

    /**
     * Hibernate firstly requests the posts and then requests the comments for each post because comments are
     * loaded lazily. First we have a list of all posts and only when Jacskon (the mapper that serializes everything
     * into JSON) calls a getter on the comment field, the comments are loaded.
     * There's "N1 problem" related to that.
     * If I tried to edit one of the posts that has comments, and the amended version of the post did not include
     * any comments, Hibernate would try to delete all comments assigned to that post. Firstly, Hibernate would clear
     * the field in the joined table that hold the connection (postId) and then it can, but doesn't have to delete
     * all comments that have postId field left empty.
     * If I wanted the comments bereft of postId deleted, I could add "orphanRemoval = true" to @OneToMany.
     * In order to prevent Hibernate from removing comments during such an editing of the post, I can add
     * "updatable = false" making Hibernate not try to delete the comments during an update and "insertable = false"
     * to not change the comments upon adding new ones. These attributes mean that the update of the comments or
     * the insertion of new ones is not possible to be executed from the level of the current entity here, which
     * is Post.
     */
    @OneToMany
    @JoinColumn(name = "postId", updatable = false, insertable = false)
    private List<Comment> comment;
}
