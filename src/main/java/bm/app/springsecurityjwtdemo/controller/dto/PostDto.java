package bm.app.springsecurityjwtdemo.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * I do not want multiple queries to be generated when I am trying to get all posts. It's happening,
 * because Post has a List of comments for every post as one of the fields.
 * A query for the database is generated whenever that field is read. So I am creating a dto to
 * prevent that field from being read at all and to bypass the "N+1" problem.
 * Builder will be helpful for mapping.
 */
@Getter
@Builder
@Setter
public class PostDto {

    private long id;
    private String title;
    private String content;
    private LocalDateTime created;

}
