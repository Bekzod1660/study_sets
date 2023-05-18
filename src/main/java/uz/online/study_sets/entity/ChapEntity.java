package uz.online.study_sets.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.online.study_sets.constants.TableNames;
import uz.online.study_sets.dto.ChapDto;
import uz.online.study_sets.dto.UserDto;
import uz.online.study_sets.entity.base.BaseServerModifierEntity;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableNames.DEPARTMENT_CHAP)
public class ChapEntity extends BaseServerModifierEntity {
    @Column(nullable = false,unique = true)
    private String name;

    public ChapDto toDto(String... ignoreProperties){
        return toDto(this, new ChapDto(), ignoreProperties);
    }


}
