package uz.online.study_sets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.online.study_sets.entity.ChapEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChapRepository extends JpaRepository<ChapEntity, Long> {
    Optional<ChapEntity> findByName(String name);
    @Query(value = "SELECT * FROM d_chap WHERE status <> 'DELETED' ",nativeQuery = true)
    List<ChapEntity> getChapList();
    @Query(value = "SELECT * FROM d_chap WHERE status <> 'DELETED' AND id = :chapId AND created_by = :adminId ",nativeQuery = true)
    Optional<ChapEntity> getChapById(@Param("chapId")  Long chapId, @Param("adminId") Long adminId);
    @Modifying
    @Query(value = "UPDATE d_chap SET status = 'DELETED' WHERE id = :chapId AND created_by = :adminId",nativeQuery = true)
    Integer chapDelete(@Param("chapId")  Long chapId, @Param("adminId") Long adminId);

    @Modifying
    @Query(value = "UPDATE d_chap SET status = 'CREATED' WHERE id = :chapId",nativeQuery = true)
    Integer statusCreate(@Param("chapId")  Long chapId);
}
