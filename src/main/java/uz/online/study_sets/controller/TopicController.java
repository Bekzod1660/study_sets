package uz.online.study_sets.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.online.study_sets.dto.TopicDto;
import uz.online.study_sets.dto.response.HttpResponse;
import uz.online.study_sets.sevice.TopicService;


@RestController
@RequestMapping("/api/vi/topic")
@RequiredArgsConstructor
@Tag(name = "Topic Controller", description = "This Controller for Topic")
public class TopicController {
    private final TopicService topicService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This method for ADD", description = "This method Topic ADD")
    @PostMapping("/add")
    public HttpResponse<Object> save(@RequestBody TopicDto topicDto) {
        HttpResponse<Object> response = new HttpResponse<>(true);
        try {

            if (topicService.save(topicDto)) {
                return response.code(HttpResponse.Status.OK).body(true).success(true);
            }
        } catch (Exception ex) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This method for update", description = "This method updates the Topic data")
    @PostMapping("/update/{id}")
    public HttpResponse<Object> update(
            @PathVariable("id") Long id,
            @RequestBody TopicDto topic) {
        HttpResponse<Object> response = new HttpResponse<>(true);
        try {

            if (topicService.updateTopic(id,topic)) {
                return response.code(HttpResponse.Status.OK).body(true).success(true);
            }
        } catch (Exception ex) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }



    @Operation(summary = "This user for delete", description = "This method is designed to delete a Topic by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> userDelete(@PathVariable("id") Long id) {

        HttpResponse<Object> response = new HttpResponse<>(true);

        try {
            if (topicService.delete(id)) {
                return response.code(HttpResponse.Status.OK).success(true).body(Boolean.TRUE).message("Chap deleted successfully");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Operation(summary = "This method for get", description = "This method is used to get user administrator information")
    @GetMapping("/info/{id}")
    public HttpResponse<Object> getTopicInformation(@PathVariable("id") Long id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            TopicDto topicDto = topicService.getTopicInformation(id);
            response.code(HttpResponse.Status.OK).success(true).body(topicDto).message("successfully!!!");
        } catch (Exception e) {
            e.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

}
