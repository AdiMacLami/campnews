package net.ictcampus.campnews.tag.dto;

import net.ictcampus.campnews.tag.Tag;

public class TagMapper {

    public static TagDTO toDto(Tag tag) {
        return new TagDTO(tag.getId(), tag.getName());
    }

    public static void updateFromDto(Tag tag, UpdateTagDTO dto) {
        tag.setName(dto.getName());
    }
}