package net.ictcampus.campnews.tag;

import jakarta.persistence.EntityNotFoundException;
import net.ictcampus.campnews.tag.dto.CreateTagDTO;
import net.ictcampus.campnews.tag.dto.TagDTO;
import net.ictcampus.campnews.tag.dto.TagMapper;
import net.ictcampus.campnews.tag.dto.UpdateTagDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public void createTag(CreateTagDTO dto) {
        Tag newTag = new Tag()
                .setName(dto.getName());

        tagRepository.save(newTag);
    }

    public List<TagDTO> getTags() {
        return tagRepository.findAll().stream()
                .map(TagMapper::toDto)
                .toList();
    }

    public TagDTO getTag(Integer id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));
        return TagMapper.toDto(tag);
    }

    public void updateTag(Integer id, UpdateTagDTO dto) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));
        TagMapper.updateFromDto(tag, dto);
        TagMapper.toDto(tagRepository.save(tag));
    }

    public void deleteTag(Integer id) {
        if (!tagRepository.existsById(id)) {
            throw new EntityNotFoundException("Tag not found");
        }
        tagRepository.deleteById(id);
    }
}
