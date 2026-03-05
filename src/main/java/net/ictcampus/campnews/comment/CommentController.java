package net.ictcampus.campnews.comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.ictcampus.campnews.auth.jwt.JwtDTO;
import net.ictcampus.campnews.comment.dto.CommentDTO;
import net.ictcampus.campnews.comment.dto.CreateCommentDTO;
import net.ictcampus.campnews.comment.dto.UpdateCommentDTO;
import net.ictcampus.campnews.tag.TagService;
import net.ictcampus.campnews.tag.dto.TagDTO;
import net.ictcampus.campnews.user.UserService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "CommentController", description = "Processes /comments endpoint requests")
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }
    @Operation(summary = "Create an new Comment", description = "Creates comments details based on provided information", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully created user", content = {@Content(mediaType = "application/json")}),
    })
    @PreAuthorize("@authz.canAccessComment(authentication, #id)")
    @PostMapping
    public ResponseEntity<Void> createComment(@Valid @RequestBody CreateCommentDTO dto) {
        commentService.createComment(dto);
        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "Get all existing Comments", description = "Gets all comments")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully got comments", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Comments not found"),
    })
    @GetMapping
    public ResponseEntity<List<CommentDTO>> getComments() {
        return ResponseEntity.ok(commentService.getComments());
    }

    @Operation(summary = "Gets an existing Comment", description = "Gets comments details based on provided information", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully found comment", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.getComment(id));
    }

    @Operation(summary = "Update an existing Comment", description = "Updates comment details based on provided information", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated comment", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "409", description = "Failed to update comment")
    })
    @PreAuthorize("@authz.canAccessComment(authentication, #id)")
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Void> updateComment(@PathVariable Integer id, @Valid @RequestBody UpdateCommentDTO dto) {
        commentService.updateComment(id, dto);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Delete an existing Comment", description = "Deletes comment based on provided information", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully deleted comment", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
    })
    @PreAuthorize("@authz.canAccessComment(authentication, #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
