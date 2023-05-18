package uz.online.study_sets.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.online.study_sets.dto.base.BaseServerModifierDto;
import uz.online.study_sets.entity.TopicEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChapDto extends BaseServerModifierDto {
    @NotBlank(message = "name must not be empty")
    private String name;

    private List<TopicDto>topicEntityList;
}
