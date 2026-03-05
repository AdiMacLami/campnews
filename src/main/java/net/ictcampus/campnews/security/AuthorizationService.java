package net.ictcampus.campnews.security;

import net.ictcampus.campnews.comment.CommentRepository;
import net.ictcampus.campnews.post.PostRepository;
import net.ictcampus.campnews.user.UserRepository;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

// Macht einen Komponenten für die Ganze Applikation verfügbar
@Component("authz")
@EnableMethodSecurity
public class AuthorizationService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public AuthorizationService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    // Überprüft ob der User im JWT der gleiche ist wie der User vom User
    public boolean canAccessUser(Authentication auth, Integer userId) {
        return userRepository.findByUsername(auth.getName())
                .map(current -> current.getId().equals(userId))
                .orElse(false);
    }

    // Überprüft ob der User im JWT der gleiche ist wie der User vom Post
    public boolean canAccessPost(Authentication auth, Integer postId) {
        return postRepository.findById(postId)
                .map(post -> post.getUser().getUsername().equals(auth.getName()))
                .orElse(false);
    }

    // Überprüft ob der User im JWT der gleiche ist wie der User vom Comment
    public boolean canAccessComment(Authentication auth, Integer commentId) {
        return commentRepository.findById(commentId)
                .map(comment -> comment.getUser().getUsername().equals(auth.getName()))
                .orElse(false);
    }
}