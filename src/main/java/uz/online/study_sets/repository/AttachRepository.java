package uz.online.study_sets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.online.study_sets.entity.AttachEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachRepository extends JpaRepository<AttachEntity,Long> {


}
