package net.ictcampus.campnews.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.ictcampus.campnews.tag.dto.TagDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostDTO {
    private Integer id;
    private String title;
    private String text;
    private String username;
    private List<TagDTO> tags;
}