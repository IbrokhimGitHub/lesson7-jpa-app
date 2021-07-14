package uz.pdp.lesson7jpaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson7jpaapp.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    boolean existsByName(String name);
}
