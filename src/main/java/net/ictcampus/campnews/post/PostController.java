package net.ictcampus.campnews.post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.ictcampus.campnews.post.dto.CreatePostDTO;
import net.ictcampus.campnews.post.dto.PostDTO;
import net.ictcampus.campnews.post.dto.UpdatePostDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "PostController", description = "Processes /posts endpoint requests")
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Creates an new Post", description = "Creates a new post details based on provided information", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully created post", content = {@Content(mediaType = "application/json")}),
    })
    @PreAuthorize("@authz.canAccessPost(authentication, #id)")
    @PostMapping
    public ResponseEntity<Void> createPost(@Valid @RequestBody CreatePostDTO post) {
        postService.createPost(post);
        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "Gets all existing Posts", description = "Gets all users", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully found users", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @GetMapping
    public ResponseEntity<List<PostDTO>> getPosts() {
        return ResponseEntity.ok(postService.getPosts());
    }

    @Operation(summary = "Gets an existing Post", description = "Gets post details based on provided information", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully found post", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Post not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Integer id) {
        return ResponseEntity.ok(postService.getPost(id));
    }
    @Operation(summary = "Update an existing Post", description = "Updates post details based on provided information", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated post", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "409", description = "Failed to update post")
    })
    @PreAuthorize("@authz.canAccessPost(authentication, #id)")
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Integer id, @Valid @RequestBody UpdatePostDTO post) {
        postService.updatePost(id, post);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete an existing Post", description = "Deletes post details based on provided information", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully deleted post", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Post not found"),
    })
    @PreAuthorize("@authz.canAccessPost(authentication, #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
