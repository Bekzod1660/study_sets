package uz.online.study_sets.entity;

import uz.online.study_sets.constants.TableNames;
import uz.online.study_sets.dto.UserDto;
import uz.online.study_sets.entity.base.BaseServerModifierEntity;
import uz.online.study_sets.entity.role.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableNames.DEPARTMENT_USER)
public class UserEntity extends BaseServerModifierEntity implements UserDetails {

  private String firstname;
  private String lastname;
  private String middleName;
  private Date birtDate;
  @Column(unique = true,nullable = false)
  private String phoneNumber;
  private String password;
  @Enumerated(EnumType.STRING)
  private RoleEnum role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.asList(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return phoneNumber;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }


    /************************************************************
    * ******************** CONVERT TO DTO ***********************
    * ***********************************************************/
    public UserDto toDto(String... ignoreProperties){
      return toDto(this, new UserDto(), ignoreProperties);
    }
}
