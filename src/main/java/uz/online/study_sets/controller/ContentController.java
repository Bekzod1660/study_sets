package uz.online.study_sets.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.online.study_sets.dto.ContentDto;
import uz.online.study_sets.dto.response.HttpResponse;
import uz.online.study_sets.sevice.ContentService;

@RestController
@RequestMapping("/api/vi/content")
@RequiredArgsConstructor
@Tag(name = "content Controller", description = "This Controller for content")
public class ContentController {

    private final ContentService contentService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This method for ADD", description = "This method Content ADD")
    @PostMapping("/add")
    public HttpResponse<Object> save(@RequestBody ContentDto contentDto) {
        HttpResponse<Object> response = new HttpResponse<>(true);
        try {

            if (contentService.save(contentDto)) {
                return response.code(HttpResponse.Status.OK).body(true).success(true);
            }
        } catch (Exception ex) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This method for update", description = "This method updates the Content data")
    @PostMapping("/update/{id}")
    public HttpResponse<Object> update(
            @PathVariable("id") Long id,
            @RequestBody ContentDto contentDto) {
        HttpResponse<Object> response = new HttpResponse<>(true);
        try {

            if (contentService.updateContent(id,contentDto)) {
                return response.code(HttpResponse.Status.OK).body(true).success(true);
            }
        } catch (Exception ex) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }



    @Operation(summary = "This  for delete", description = "This method is designed to delete a Content by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> userDelete(@PathVariable("id") Long id) {

        HttpResponse<Object> response = new HttpResponse<>(true);

        try {
            if (contentService.delete(id)) {
                return response.code(HttpResponse.Status.OK).success(true).body(Boolean.TRUE).message("Chap deleted successfully");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


}
