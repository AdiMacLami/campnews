package net.ictcampus.campnews.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
//Lombok
@Getter
@Setter
public class CreatePostDTO {
    //Jakarta Validation
    @NotBlank(message = "Title is required")
    private String title;
    //Jakarta Validation
    @NotBlank(message = "Text is required")
    private String text;
    //Jakarta Validation
    @NotNull(message = "At least one tag must be provided")
    private Set<Integer> tagIds;
}
