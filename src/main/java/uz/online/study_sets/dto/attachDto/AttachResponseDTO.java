package uz.online.study_sets.dto.attachDto;

import lombok.Getter;
import lombok.Setter;
import uz.online.study_sets.dto.base.BaseServerModifierDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class AttachResponseDTO  extends BaseServerModifierDto {
    private String originalName;
    private String path;
    private Long size;
    private String extension;
    private Double duration;
    private String name;
    private LocalDateTime createdData;
    private String url;
}
