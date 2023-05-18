package uz.online.study_sets.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.online.study_sets.constants.TableNames;
import uz.online.study_sets.dto.ContentDto;
import uz.online.study_sets.dto.TopicDto;
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
@Table(name = TableNames.DEPARTMENT_CONTENT)
public class ContentEntity extends BaseServerModifierEntity {
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition="TEXT")
    private String description;
    @Column(name = "topicId", nullable = false)
    private Long topicId;
    @ManyToOne
    @JoinColumn(name = "topicId", insertable = false, updatable = false)
    private TopicEntity topicEntity;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AttachEntity>attachEntityList;



    public ContentDto toDto(String... ignoreProperties){
        return toDto(this, new ContentDto(),ignoreProperties);
    }

}
