package uz.online.study_sets.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.online.study_sets.constants.TableNames;
import uz.online.study_sets.dto.TopicDto;
import uz.online.study_sets.dto.UserDto;
import uz.online.study_sets.entity.base.BaseServerModifierEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableNames.DEPARTMENT_TOPIC)
public class TopicEntity extends BaseServerModifierEntity {
    @Column(nullable = false)
    private String name;
    private String info;
    @Column(name = "chapId", nullable = false)
    private Long chapId;
    @ManyToOne
    @JoinColumn(name = "chapId", insertable = false, updatable = false)
    private ChapEntity chapEntity;
    public TopicDto toDto(String... ignoreProperties){
        return toDto(this, new TopicDto(),ignoreProperties);
    }

}
