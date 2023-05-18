package uz.online.study_sets.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.online.study_sets.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
    @Modifying
    @Query(value = "UPDATE d_user SET status = 'DELETED' WHERE id = :userId", nativeQuery = true)
    Integer userDelete(@Param("userId") Long userId);
    @Query(value = "SELECT * FROM d_user WHERE id = :id AND status <> 'DELETE'", nativeQuery = true)
    UserEntity getUserInformation(@Param("id") Long id);
    @Query(value = "SELECT * FROM d_user WHERE status <> 'DELETE'", nativeQuery = true)
    List<UserEntity> getAllUser();
}
