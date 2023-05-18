package uz.online.study_sets.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.online.study_sets.common.exception.RecordNotFountException;
import uz.online.study_sets.common.util.SecurityUtils;
import uz.online.study_sets.dto.ChapDto;
import uz.online.study_sets.dto.TopicDto;
import uz.online.study_sets.entity.ChapEntity;
import uz.online.study_sets.entity.TopicEntity;
import uz.online.study_sets.entity.UserEntity;
import uz.online.study_sets.repository.ChapRepository;
import uz.online.study_sets.repository.UserRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChapService {
    private final ChapRepository repository;
    private final TopicService topicService;


    @Transactional
    public Boolean save(ChapDto chapDto) {
        Optional<ChapEntity> byName = repository.findByName(chapDto.getName());
        ChapEntity chapEntity = new ChapEntity();
        if (byName.isPresent()) {
            chapEntity.forCreate(SecurityUtils.getUserId());
            Integer status = repository.statusCreate(chapEntity.getId());
            return status>0;
        }
        BeanUtils.copyProperties(chapDto, chapEntity);
        chapEntity.forCreate(SecurityUtils.getUserId());
        repository.save(chapEntity);
        return true;
    }

    public List<ChapDto> getChapAll() {
        return repository.getChapList().stream().map(ChapEntity::toDto
        ).toList();

    }


    public ChapDto getChapInformation(Long id) {
        ChapEntity chapEntity = repository.findById(id)
                .orElseThrow(() -> new RecordNotFountException(String.format("  %s there is such a thing ", id)));
        ChapDto dto = chapEntity.toDto();
        List<TopicDto> topicDto1 = topicService.topicEntityList(chapEntity.getId()).stream().map(topicEntity -> {
                    TopicDto topicDto = topicEntity.toDto();
                    topicDto.setChapName(chapEntity.getName());
                    return topicDto;
                }
        ).toList();
        dto.setTopicEntityList(topicDto1);
        return dto;
    }

    @Transactional
    public boolean updateChap(Long id, ChapDto chapDto) {
        ChapEntity chapEntity = repository.getChapById(id, SecurityUtils.getUserId())
                .orElseThrow(() -> new RecordNotFountException(String.format("  %s there is such a thing or it doesn't apply to you", id)));
        chapEntity.forUpdate(SecurityUtils.getUserId());
        chapEntity.setName(chapDto.getName());
        repository.save(chapEntity);
        return true;
    }


    @Transactional
    public boolean delete(Long id) {
        Integer chapDeleteIsSuccess = repository.chapDelete(id, SecurityUtils.getUserId());
        return chapDeleteIsSuccess > 0;
    }
}
