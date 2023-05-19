package uz.online.study_sets.dto.attachDto;

import lombok.Getter;
import lombok.Setter;
import uz.online.study_sets.dto.base.BaseServerModifierDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class AttachResponseDTO  extends BaseServerModifierDto {
    private String originalName;
    private Long size;

    private String name;

}
