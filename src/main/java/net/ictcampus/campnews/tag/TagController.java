package net.ictcampus.campnews.tag;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.ictcampus.campnews.tag.dto.CreateTagDTO;
import net.ictcampus.campnews.tag.dto.TagDTO;
import net.ictcampus.campnews.tag.dto.UpdateTagDTO;
import net.ictcampus.campnews.user.User;
import net.ictcampus.campnews.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Tag(name = "TagController", description = "Processes /tags endpoint requests")
@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }
    @Operation(summary = "Create an  new Tag", description = "Create tag details based on provided information", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully created tag", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Tag not found"),
    })
    @PostMapping
    public ResponseEntity<Void> createTag(@Valid @RequestBody CreateTagDTO tag) {
        tagService.createTag(tag);
        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "Gets all existing Tags", description = "Gets all tags", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully found tag", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TagDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Tag not found"),
    })
    @GetMapping
    public ResponseEntity<List<TagDTO>> getTags() {
        return ResponseEntity.ok(tagService.getTags());
    }

    @Operation(summary = "Get an existing Tag", description = "Gets  tag details based on provided information", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully found tag", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TagDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Tag not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTag(@PathVariable Integer id) {
        return ResponseEntity.ok(tagService.getTag(id));
    }

    @Operation(summary = "Update an existing Tag", description = "Updates tag details based on provided information", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated tag", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Tag not found"),
            @ApiResponse(responseCode = "409", description = "Failed to update tag")
    })
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Void> updateTag(@PathVariable Integer id, @Valid @RequestBody UpdateTagDTO tag) {
        tagService.updateTag(id, tag);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete an existing Tag", description = "Deletes tag details based on provided information", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully deleted tag", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Tag not found"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Integer id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok().build();
    }
}
