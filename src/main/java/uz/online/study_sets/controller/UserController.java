package uz.online.study_sets.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.online.study_sets.dto.UserDto;
import uz.online.study_sets.dto.response.HttpResponse;
import uz.online.study_sets.repository.UserRepository;
import uz.online.study_sets.sevice.UserService;


import java.util.List;

@RestController
@RequestMapping("api/vi/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "This Controller for user")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This method for get",description = "To get all users for admin")
    @GetMapping("/list")
    public HttpResponse<Object> getUserList() {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            List<UserDto> userList = userService.getUserAll();
            if(userList == null || userList.isEmpty()) response.code(HttpResponse.Status.NOT_FOUND).message("Not found any user!!!");
            else response.code(HttpResponse.Status.OK).success(true).body(userList).message("successfully!!!");
        } catch (Exception e) {
            e.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This method for get", description = "This method is used to get how many points the admin user has scored")
    @GetMapping("/info/{id}")
    public HttpResponse<Object> getUserInformation(@PathVariable("id") Long id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            UserDto userDto = userService.getUserInformation(id);
            response.code(HttpResponse.Status.OK).success(true).body(userDto).message("successfully!!!");
        } catch (Exception e) {
            e.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Operation(summary = "This method for update", description = "This method updates the user's data")
    @PostMapping("/update")
    public HttpResponse<Object> userUpdate(@RequestBody UserDto userDto) {
        HttpResponse<Object> response = new HttpResponse<>(true);

        try {

            if (userService.updateUser(userDto)) {
                return response.code(HttpResponse.Status.OK).body(true).success(true);
            }
        } catch (Exception ex) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Operation(summary = "This user for update", description = "This method is designed to delete a user by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> userDelete(@PathVariable("id") Long id) {

        HttpResponse<Object> response = new HttpResponse<>(true);

        try {
            if (userService.userDelete(id)) {
                return response.code(HttpResponse.Status.OK).success(true).body(Boolean.TRUE).message("User deleted successfully");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


}
