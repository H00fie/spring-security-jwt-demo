package bm.app.springsecurityjwtdemo.service;

import bm.app.springsecurityjwtdemo.model.Comment;
import bm.app.springsecurityjwtdemo.model.Post;
import bm.app.springsecurityjwtdemo.repository.CommentRepository;
import bm.app.springsecurityjwtdemo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    public static final int PAGE_SIZE = 20;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

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

    /**
     * The same method as above, but allowing the user to input the page number as a parameter.
     * If I already implemented the pagination, it's easy to add the sorting mechanism - it's
     * provided by the same mechanism and I can add it as a parameter of the PageRequest's .of()
     * method.
     * I can configure the direction of the sorting and specify what fields the sorting should
     * be carried out by.
     * I could implement the sorting by hard coding it into the method - to do that, I could
     * add e.g: "Sort.by(Sort.Order.asc("id"), Sort.Order.desc("created")" which would give me
     * two directions of how to sort.
     * The sorting parameter can be provided from the API level instead. The parameter required
     * for this is "Sort.Direction <param_name>".
     */
    public List<Post> getPostsWithCustomQueryWithoutJoinWithCustomParam(int page, Sort.Direction sort) {
        return postRepository.findAllPostsWithoutJoin(PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "id")));
    }

    /**
     * A method for retrieving posts with comments with pagination and of optimal performance.
     * Firstly, paginated posts will be loaded, then from the list of posts I will get all of
     * their ids which will be used to create a second query to get all comments.
     * This method allows me to get all posts with all of their comments without suffering
     * the "N+1" problem as there are only two queries altogether.
     * Instead of having Hibernate query my database for every single record separately, I
     * am joining posts with comments myself using two lists I created by having queried
     * Post table once and Comment table once.
     */
    public List<Post> getPostsWithComments(int page, Sort.Direction sort) {
        //Getting all posts...
        List<Post> allPosts = postRepository.findAllPostsWithoutJoin(PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "id")));
        //Using a stream to get a list of ids of the posts...
        List<Long> ids = allPosts.stream()
                .map(Post::getId)
                .toList();
        //I need to get all comments for the list of ids. A CommentRepository was created for
        //this. The name of the methods adheres to the Spring Data Jpa documentation. The "In"
        //addition makes sure the methods will retrieve all records for the parameters provided
        //understood as a scope, not a single value.
        List<Comment> comments = commentRepository.findAllByPostIdIn(ids);
        //Now to join comments with the posts, I am iterating through the list of posts and
        //for every one of them, I am setting its comment list to a list I get by iterating
        //through the comments' list with that post's id.
        allPosts.forEach(post -> post.setComment(extractComments(comments, post.getId())));
        return allPosts;
    }

    /**
     * A method for extracting all comments for a post whose id is provided as a parameter.
     */
    private List<Comment> extractComments(List<Comment> comments, long id) {
        return comments.stream()
                .filter(comment -> comment.getPostId() == id)
                .collect(Collectors.toList());
    }

    /**
     * Utilizing a standard saving method of Spring Data Jpa.
     */
    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    /**
     * Initially, the method used a simply Jpa's .save() like the .addPost() above and didn't
     * do anything else. In such a scenario, if someone tried to edit a post without providing
     * its is, the application would simply create a new post. It should throw an exception instead.
     * Without the @Transactional, there are two database transactions within the method. Without the
     * manual setting of the transactions' management, transactions function in an auto-commit mode
     * which means every database query is placed in a separate transaction. .findById() is the first
     * query and thus a transaction and .save() is the second. One method should not have multiple
     * transactions in order to make sure the updating of the data is cohesive. @Transactional makes
     * it all a single transaction.
     * The presence of @Transactional makes the .save() redundant. The annotation makes the method
     * function as a single transaction, so two queries to the database are not required. Hibernate
     * has a "dirty checking" mechanism which checks every loaded entity to see if its fields have
     * changed. If so, Hibernate sees the changes and automatically updates the entity within the
     * database.
     */
    @Transactional
    public Post editPost(Post post) {
        //.findById() returns an optional.
        Post editedPost = postRepository.findById(post.getId()).orElseThrow();
        //I am changing only the title and the content.
        editedPost.setTitle(post.getTitle());
        editedPost.setContent(post.getContent());
        return editedPost;
//        return postRepository.save(post);
    }

    /**
     * Deleting is just like that would not work, because I cannot delete a post that has a foreign
     * key to another entity (comments). In order to have all comments be deleted alongside their post,
     * I need to set the cascade argument in the relation between the two entities in Post.
     */
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }
}
