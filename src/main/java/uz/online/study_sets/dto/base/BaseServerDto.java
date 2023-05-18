package uz.online.study_sets.dto.base;

import uz.online.study_sets.constants.EntityStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseServerDto {
    private Long id;

    private EntityStatus status;
}
