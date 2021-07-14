package uz.pdp.lesson7jpaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson7jpaapp.entity.Address;



public interface AddressRepository extends JpaRepository<Address, Integer> {
}
