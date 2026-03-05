package net.ictcampus.campnews.comment;

import jakarta.persistence.EntityNotFoundException;
import net.ictcampus.campnews.comment.dto.CommentDTO;
import net.ictcampus.campnews.comment.dto.CommentMapper;
import net.ictcampus.campnews.comment.dto.CreateCommentDTO;
import net.ictcampus.campnews.comment.dto.UpdateCommentDTO;
import net.ictcampus.campnews.post.Post;
import net.ictcampus.campnews.post.PostRepository;
import net.ictcampus.campnews.tag.Tag;
import net.ictcampus.campnews.user.User;
import net.ictcampus.campnews.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public void createComment(CreateCommentDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        Comment newComment = new Comment();
        newComment.setText(dto.getText());
        newComment.setUser(null);
        newComment.setPost(post);
        newComment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(newComment);
    }

    public List<CommentDTO> getComments() {
        return commentRepository.findAll().stream()
                .map(CommentMapper::toDto)
                .toList();
    }

    public CommentDTO getComment(Integer id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        return CommentMapper.toDto(comment);
    }

    public void updateComment(Integer id, UpdateCommentDTO dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        CommentMapper.updateFromDto(comment, dto);
        CommentMapper.toDto(commentRepository.save(comment));
    }

    public void deleteComment(Integer id) {
        if (!commentRepository.existsById(id)) {
            throw new EntityNotFoundException("Comment not found");
        }
        commentRepository.deleteById(id);
    }
}
