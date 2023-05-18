package uz.online.study_sets.entity.base;

import uz.online.study_sets.constants.EntityStatus;
import uz.online.study_sets.dto.base.BaseServerDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@MappedSuperclass
public class BaseServerEntity extends BaseObject{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", length = 32, columnDefinition = "varchar(32) default 'CREATED'")
    @Enumerated(value = EnumType.STRING)
    private EntityStatus status = EntityStatus.CREATED;

    /**********************************
     ***    convert ENTITY to DTO   ***
     **********************************/
    public <ENTITY extends BaseServerEntity, DTO extends BaseServerDto> DTO entityToDto(ENTITY entity, DTO dto) {
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public Long getUniqueId() {
        return getId();
    }
}
