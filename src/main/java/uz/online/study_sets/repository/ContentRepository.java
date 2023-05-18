package uz.online.study_sets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.online.study_sets.entity.ChapEntity;
import uz.online.study_sets.entity.ContentEntity;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<ContentEntity,Long> {
    @Query(value = "SELECT dc.* FROM d_content dc\n" +
            "    INNER JOIN d_topic dt ON  dc.topic_id = dt.id\n" +
            "            WHERE dc.status<>'DELETED' AND dt.id= :topicId ",nativeQuery = true)
    List<ContentEntity> getContentByTopicId(@Param("topicId") Long topicId);

    @Query(value = "SELECT * FROM d_content WHERE status <> 'DELETED' AND id = :contentId AND created_by = :adminId ",nativeQuery = true)
    Optional<ContentEntity> getContentById(@Param("contentId")  Long contentId, @Param("adminId") Long adminId);

    @Modifying
    @Query(value = "UPDATE d_content SET status = 'CREATED' WHERE id = :contentId ",nativeQuery = true)
    Integer statusCreatet(@Param("contentId") Long contentId);

    @Modifying
    @Query(value = "SELECT d.attach_entity_list_id FROM d_content_attach_entity_list d WHERE content_entity_id= :contentId",nativeQuery = true)
    List<Long>getAttachIdList(@Param("contentId")Long contentId);

    @Modifying
    @Query(value = "UPDATE d_content SET status = 'DELETED' WHERE id = :contentId AND created_by = :adminId",nativeQuery = true)
    Integer deleteContent(@Param("contentId")  Long contentId, @Param("adminId") Long adminId);
}
