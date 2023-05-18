package uz.online.study_sets.sevice;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import uz.online.study_sets.common.exception.RecordNotFountException;
import uz.online.study_sets.common.util.SecurityUtils;
import uz.online.study_sets.dto.ContentDto;
import uz.online.study_sets.dto.TopicDto;
import uz.online.study_sets.entity.AttachEntity;
import uz.online.study_sets.entity.TopicEntity;
import uz.online.study_sets.repository.AttachRepository;
import uz.online.study_sets.repository.ChapRepository;
import uz.online.study_sets.repository.TopicRepository;
import uz.online.study_sets.repository.UserRepository;

import java.io.FileInputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository repository;
    private final ContentService contentService;
    private final AttachRepository repository1;
    private static final String url = "F:\\AA PROJECT\\study_sets\\src\\main\\resources\\templates\\imag";




    public Boolean save(TopicDto topicDto) {
        for (TopicEntity topicEntity :topicEntityList(topicDto.getChapId())){
            if (topicEntity.getName().equals(topicDto.getName())){
                Integer status = repository.statusCreated(topicEntity.getId());
                return status>0;
            }
        }
        TopicEntity topicEntity=new TopicEntity();
        BeanUtils.copyProperties(topicDto,topicEntity);
        topicEntity.forCreate(SecurityUtils.getUserId());
        repository.save(topicEntity);
        return true;
    }
    @Transactional
    public Boolean updateTopic(Long id, TopicDto dto) {
        TopicEntity topicEntity = repository.getChapById(id, SecurityUtils.getUserId())
                .orElseThrow(() -> new RecordNotFountException(String.format("  %s there is such a thing or it doesn't apply to you", id)));
        topicEntity.forUpdate(SecurityUtils.getUserId());
        if (dto.getChapId()!=null)topicEntity.setChapId(dto.getChapId());
        if (dto.getInfo()!=null)topicEntity.setInfo(dto.getInfo());
        if (dto.getName()!= null)topicEntity.setName(dto.getName());
        repository.save(topicEntity);
        return true;
    }

    public List<TopicEntity>topicEntityList(Long chapId){
        return repository.getChapRespectiveByChapId(chapId);
    }

    @Transactional
    public Boolean delete(Long id) {
        Integer delete = repository.topicDelete(id,SecurityUtils.getUserId());
        return delete > 0;
    }

    public TopicDto getTopicInformation(Long id) {
        TopicEntity topicEntity = repository.findById(id)
                .orElseThrow(() -> new RecordNotFountException(String.format(" %s there is such a thing or it doesn't apply to you", id)));
        List<ContentDto> contentDtos = contentService.getContentListByTopicId(id).stream().map(contentEntity -> {
            ContentDto contentDto = contentEntity.toDto();
            contentDto.setAttachIdList(contentService.getAttachIdListByContentId(contentEntity.getId()));

            return contentDto;
        }).toList();
        TopicDto topicDto = topicEntity.toDto();
        topicDto.setContentEntityList(contentDtos);
        return topicDto;
    }
}
