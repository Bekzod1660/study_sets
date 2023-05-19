package uz.online.study_sets.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.online.study_sets.common.exception.RecordNotFountException;
import uz.online.study_sets.common.util.SecurityUtils;
import uz.online.study_sets.dto.ContentDto;
import uz.online.study_sets.entity.AttachEntity;
import uz.online.study_sets.entity.ContentEntity;
import uz.online.study_sets.repository.AttachRepository;
import uz.online.study_sets.repository.ContentRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository repository;
    private final AttachRepository attachRepository;

    @Transactional
    public Boolean save(ContentDto dto) {
        for (ContentEntity contentEntity : getContentListByTopicId(dto.getTopicId())) {
            if (contentEntity.getName().equals(dto.getName())){
                Integer status=repository.statusCreatet(contentEntity.getId());
                return  status>0;
            }
        }
        ContentEntity contentEntity = dto.toEntity();
        List<AttachEntity>attachEntityList=new ArrayList<>();
        for (Long attach:dto.getAttachIdList()){
           // AttachEntity attachEntity = attachRepository.findById(attach).get();
          //  attachEntityList.add(attachEntity);
        }
        contentEntity.setAttachEntityList(attachEntityList);
        contentEntity.forCreate(SecurityUtils.getUserId());
        repository.save(contentEntity);
        return true;
    }

    public List<ContentEntity>getContentListByTopicId(Long topicId){
        return  repository.getContentByTopicId(topicId);
    }

    @Transactional
    public Boolean delete(Long id) {
   Integer delete= repository.deleteContent(id,SecurityUtils.getUserId());
    return delete>0;
    }

    public List<Long>getAttachIdListByContentId(Long contentId){
        return repository.getAttachIdList(contentId);
    }

    @Transactional
    public Boolean updateContent(Long id, ContentDto contentDto) {
        ContentEntity contentEntity = repository.getContentById(id,SecurityUtils.getUserId())
                .orElseThrow(() -> new RecordNotFountException(String.format("", id)));
        contentEntity.forUpdate(SecurityUtils.getUserId());
        if (contentDto.getTopicId()!=null)contentEntity.setTopicId(contentEntity.getTopicId());
        if (contentDto.getName()!=null)contentEntity.setName(contentEntity.getName());
        if (contentDto.getDescription()!=null)contentEntity.setDescription(contentEntity.getDescription());
        // rasm pdf qoldi
        repository.save(contentEntity);
        return true;
    }

}
