package uz.online.study_sets.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.online.study_sets.dto.response.HttpResponse;
import uz.online.study_sets.sevice.AttachService;

@RestController
@RequestMapping("/api/vi/attach")
@RequiredArgsConstructor
@Tag(name = "attach Controller", description = "This Controller for Attach")
public class AttachController {
    private final AttachService attachService;
//    private final ContentService contentService;

   @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This method for ADD", description = "This method Attach ADD")
    @PostMapping("/add")
    public HttpResponse<Object> save(MultipartHttpServletRequest multipartFile) {
        HttpResponse<Object> response = new HttpResponse<>(true);
        try {

            if (attachService.save(multipartFile)>0) {

                return response.code(HttpResponse.Status.OK).body(true).success(true);
            }
        } catch (Exception ex) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
    @Operation(summary = "This method for ADD", description = "This method Attach ADD")
    @GetMapping("/get/{id}")
    public HttpResponse<Object> get(@PathVariable("id") Long id,HttpServletResponse response1) {
        HttpResponse<Object> response = new HttpResponse<>(true);
        try {
            attachService.getAttachById(id,response1);

        } catch (Exception ex) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
    @Operation(summary = "This user for delete", description = "This method is designed to delete a Chap by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> deleteById(@PathVariable("id") Long id) {
        HttpResponse<Object> response = new HttpResponse<>(true);
        try {
           if ( attachService.deleteById(id)){
               return response.code(HttpResponse.Status.OK).body(true).success(true);

           }

        } catch (Exception ex) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

}
