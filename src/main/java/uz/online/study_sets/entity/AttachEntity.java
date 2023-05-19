package uz.online.study_sets.entity;

import jakarta.persistence.*;
import uz.online.study_sets.constants.TableNames;
import uz.online.study_sets.entity.base.BaseServerModifierEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableNames.DEPARTMENT_ATTACH)
public class AttachEntity extends BaseServerModifierEntity {

    @Column(name = "origin_name")
    private String originName;

    @Column
    private Long size;

    @Column(name = "content_type")
    private String contentType;

    private String name;



}
