package net.ictcampus.campnews.comment.dto;

import net.ictcampus.campnews.comment.Comment;

public class CommentMapper {
    public static CommentDTO toDto(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getText(),
                comment.getUser().getUsername(),
                comment.getPost().getId(),
                comment.getCreatedAt() != null ? comment.getCreatedAt().toString() : null

        );
    }

    public static void updateFromDto(Comment comment, UpdateCommentDTO dto) {
        comment.setText(dto.getText());
    }
}
