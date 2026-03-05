package net.ictcampus.campnews.tag.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTagDTO {

    @NotBlank(message = "Tag is required")
    private String name;
}