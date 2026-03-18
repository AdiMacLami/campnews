package net.ictcampus.campnews.post;

import jakarta.persistence.EntityNotFoundException;
import net.ictcampus.campnews.post.dto.CreatePostDTO;
import net.ictcampus.campnews.post.dto.PostDTO;
import net.ictcampus.campnews.post.dto.PostMapper;
import net.ictcampus.campnews.post.dto.UpdatePostDTO;
import net.ictcampus.campnews.tag.Tag;
import net.ictcampus.campnews.tag.TagRepository;
import net.ictcampus.campnews.user.User;
import net.ictcampus.campnews.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
// Spring Framework
@Service
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    public PostService(PostRepository postRepository, TagRepository tagRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    public void createPost(CreatePostDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Set<Tag> tags = getTagsFromIds(dto.getTagIds());

        Post newPost = new Post()
                .setTitle(dto.getTitle())
                .setText(dto.getText())
                .setUser(currentUser)
                .setTags(tags);

        postRepository.save(newPost);
    }

    public List<PostDTO> getPosts() {
        return postRepository.findAll().stream()
                .map(PostMapper::toDto)
                .toList();
    }

    public PostDTO getPost(Integer id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + id));
        return PostMapper.toDto(post);
    }

    public void updatePost(Integer id, UpdatePostDTO dto){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        Set<Tag> tags = getTagsFromIds(dto.getTagIds());
        PostMapper.updatePostFromDto(post, dto, tags);

        postRepository.save(post);
    }

    public void deletePost(Integer id){
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post not found");
        }
        postRepository.deleteById(id);
    }

    // Helper for create and update
    private Set<Tag> getTagsFromIds(Set<Integer> tagIds) {
        return tagIds.stream()
                .map(tagId -> tagRepository.findById(tagId)
                        .orElseThrow(() -> new EntityNotFoundException("Tag not found: " + tagId)))
                .collect(Collectors.toSet());
    }
}
