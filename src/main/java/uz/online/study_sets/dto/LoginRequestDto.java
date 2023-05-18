package uz.online.study_sets.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

  @NotBlank(message = "phone number must not be empty")
  private String phoneNumber;
  @NotBlank(message = "password must not be empty")
  String password;
}
