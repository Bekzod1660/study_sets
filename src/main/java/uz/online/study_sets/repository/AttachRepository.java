package uz.online.study_sets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.online.study_sets.entity.AttachEntity;

@Repository
public interface AttachRepository extends JpaRepository<AttachEntity,Long> {


}
