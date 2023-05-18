package uz.online.study_sets.dto;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.online.study_sets.dto.base.BaseServerModifierDto;
import uz.online.study_sets.entity.ContentEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentDto extends BaseServerModifierDto {

    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private Long topicId;
    private String topicName;

    private List<Long>attachIdList;

    public ContentEntity toEntity(String... ignoreProperties) {
        return super.toEntity(this, new ContentEntity(), ignoreProperties);
    }
}
