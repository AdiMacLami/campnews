package net.ictcampus.campnews.post.dto;

import net.ictcampus.campnews.post.Post;
import net.ictcampus.campnews.tag.Tag;
import net.ictcampus.campnews.tag.dto.TagDTO;

import java.util.Set;

public class PostMapper {

    public static PostDTO toDto(Post post) {
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getText(),
                post.getUser().getUsername(),
                post.getTags().stream()
                        .map(tag -> new TagDTO(tag.getId(), tag.getName()))
                        .toList()
        );
    }


    public static void updatePostFromDto(Post post, UpdatePostDTO dto, Set<Tag> tags) {
        post.setTitle(dto.getTitle());
        post.setText(dto.getText());
        post.setTags(tags);
    }
}
