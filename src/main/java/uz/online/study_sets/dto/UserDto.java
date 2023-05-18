package uz.online.study_sets.dto;

import uz.online.study_sets.dto.base.BaseServerModifierDto;
import uz.online.study_sets.entity.UserEntity;
import uz.online.study_sets.entity.role.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseServerModifierDto {
    private String firstname;
    private String lastname;
    private String middleName;
    private String birtDate;
    private String phoneNumber;
    private String password;
    private RoleEnum role;
    private String image;
    private String fingerprint;

    public UserEntity toEntity(String... ignoreProperties) {
        return super.toEntity(this, new UserEntity(), ignoreProperties);
    }

}
