package net.ictcampus.campnews.comment.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Integer id;
    private String text;
    private String username;
    private Integer postId;
    private String createdAt;
}