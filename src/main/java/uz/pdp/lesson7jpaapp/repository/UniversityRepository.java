package uz.pdp.lesson7jpaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson7jpaapp.entity.University;

public interface UniversityRepository extends JpaRepository <University,Integer> {
}
