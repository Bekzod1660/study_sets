package uz.online.study_sets.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.online.study_sets.dto.ChapDto;
import uz.online.study_sets.dto.response.HttpResponse;
import uz.online.study_sets.sevice.ChapService;

import java.util.List;

@RestController
@RequestMapping("/api/vi/chap")
@RequiredArgsConstructor
@Tag(name = "Chap Controller", description = "This Controller for Chap")
public class ChapController {
    private final ChapService chapService;


    @Operation(summary = "This method for get",description = "users to get what all the admins did")
    @GetMapping("/list")
    public HttpResponse<Object> getChapList() {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            List<ChapDto> chapList = chapService.getChapAll();
            if(chapList == null || chapList.isEmpty()) response.code(HttpResponse.Status.NOT_FOUND).message("Not found any chap!!!");
            else response.code(HttpResponse.Status.OK).success(true).body(chapList).message("successfully!!!");
        } catch (Exception e) {
            e.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Operation(summary = "This method for get", description = "This method is used to get user administrator information")
    @GetMapping("/info/{id}")
    public HttpResponse<Object> getChapInformation(@PathVariable("id") Long id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            ChapDto chapDto = chapService.getChapInformation(id);
            response.code(HttpResponse.Status.OK).success(true).body(chapDto).message("successfully!!!");
        } catch (Exception e) {
            e.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This method for ADD", description = "This method Chap ADD")
    @PostMapping("/add")
    public HttpResponse<Object> save(@RequestBody ChapDto chapDto) {
        HttpResponse<Object> response = new HttpResponse<>(true);
        try {

            if (chapService.save(chapDto)) {
                return response.code(HttpResponse.Status.OK).body(true).success(true);
            }
        } catch (Exception ex) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This method for update", description = "This method updates the Chap data")
    @PostMapping("/update/{id}")
    public HttpResponse<Object> update(
            @PathVariable("id") Long id,
            @RequestBody ChapDto chapDto) {
        HttpResponse<Object> response = new HttpResponse<>(true);
        try {

            if (chapService.updateChap(id,chapDto)) {
                return response.code(HttpResponse.Status.OK).body(true).success(true);
            }
        } catch (Exception ex) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    @Operation(summary = "This user for delete", description = "This method is designed to delete a Chap by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> chapDelete(@PathVariable("id") Long id) {

        HttpResponse<Object> response = new HttpResponse<>(true);

        try {
            if (chapService.delete(id)) {
                return response.code(HttpResponse.Status.OK).success(true).body(Boolean.TRUE).message("Chap deleted successfully");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
