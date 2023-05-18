package uz.online.study_sets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.online.study_sets.entity.TopicEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity,Long> {
    Optional<TopicEntity>findByName(String name);

    @Query(value = "SELECT dt.* FROM d_topic dt\n" +
            "    INNER JOIN d_chap dc ON  dt.chap_id = dc.id\n" +
            "            WHERE dc.status<>'DELETE' AND dc.id= :chapId ",nativeQuery = true)
    List<TopicEntity> getChapRespectiveByChapId(@Param("chapId") Long chapId);

    @Query(value = "SELECT * FROM d_topic WHERE status <> 'DELETED' AND id = :topicId AND created_by = :adminId ",nativeQuery = true)
    Optional<TopicEntity> getChapById(@Param("topicId")  Long topicId, @Param("adminId") Long adminId);

    @Modifying
    @Query(value = "UPDATE d_topic SET status = 'DELETED' WHERE id = :topicId AND created_by = :adminId ",nativeQuery = true)
    Integer topicDelete(@Param("topicId")  Long topicId, @Param("adminId") Long adminId);
    @Modifying
    @Query(value = "UPDATE d_topic SET status = 'CREATED' WHERE id = :topicId ",nativeQuery = true)
    Integer statusCreated(@Param("topicId")Long topicId);
}
