package bm.app.springsecurityjwtdemo.repository;

import bm.app.springsecurityjwtdemo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JpaRepository has a lot of its methods and extends different interfaces such as PagingAndSortingRepository and
 * QueryByExampleExecutor/
 * PagingAndSortingRepository allows for sorting and paging and extends CrudRepository which contains operations\
 * such as saving, finding by id, finding all, deleting by id and so forth. CrudRepository extends the Repository
 * interface which has no methods.
 * The methods within these interfaces are implemented elsewhere. E.g. JpaRepository has its methods actually
 * implemented in SimpleJpaRepository.
 * SimpleJpaRepository implements a method called .getQuery() which uses a methods .getDomainClass() which loads
 * the data that I set in my repository (it's the Post.class in this case). Due to that, Spring Data's mechanism
 * can generate separate queries for any class.
 * Upon the starting of the application, Spring Data reads the data about that selected class (Post here) using
 * reflection - what are the fields and the relations between them and using these data can generate a proper
 * query for the database.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * There are two ways of implementing the database related methods in Spring Data Jpa.
     * One of them is to create my own queries in @Query annotation. In Jpa I refer to objects, not tables.
     * "?1" points at the first parameter.
     * Spring Data will generate the implementation of the method will be based on SimpleJpaRepository.
     */
    @Query("select p from Post p where title = ?1")
    List<Post> findAllByTitleExampleOne(String title);

    /**
     * The other way of providing a parameter to the query (that still counts as the first way of creating methods
     * here).
     * This way (regardless which way of providing parameters was chosen) is useful when I create queries that
     * join multiple tables. When I am doing so, @Query is the only way to go.
     */
    @Query("select p from Post p where title = :title")
    List<Post> findAllByTitleExampleTwo(@Param("title") String title);

    /**
     * The second way of creating methods in Spring Data Jpa. It's useful where the queries are based on a simple
     * "where" without any joins.
     * Spring Data Jpa uses a specific naming convention to be used in its methods' implementation. If I adhere to
     * that convention properly - my query will be generated automatically.
     */
    List<Post> findAllByTitle(String title);

    /**
     * Untill now I was using a standard .findAll(); method which generates multiple queries - one query for every
     * record. It's a performance issue and I can work around it.
     * "Jpa requires me to use "fetch" keyword to get a related entity. If I used an inner join, I would only get
     * the posts that have comments while the left join ensures that all posts will be loaded even if they are
     * void of any comments.
     */
    @Query("select p from Post p" + " left join fetch p.comment")
    List<Post> findAllPosts();

}
