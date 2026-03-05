package net.ictcampus.campnews.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentDTO {

    @NotBlank(message = "Text is required")
    private String text;

    @NotNull(message = "Post ID is required.")
    private Integer postId;
}
