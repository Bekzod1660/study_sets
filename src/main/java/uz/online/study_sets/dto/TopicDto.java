package uz.online.study_sets.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.online.study_sets.dto.base.BaseServerModifierDto;
import uz.online.study_sets.entity.ContentEntity;
import uz.online.study_sets.entity.TopicEntity;
import uz.online.study_sets.entity.UserEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto extends BaseServerModifierDto {
    @NotBlank(message = "topic name must not be empty")
    private String name;
    @NotBlank(message = "Chap name must not be empty")
    private String chapName;
    private String info;
    private Long chapId;
    private List<ContentDto> contentEntityList;
    public TopicEntity toEntity(String... ignoreProperties) {
        return super.toEntity(this, new TopicEntity(), ignoreProperties);
    }
}
