package uz.pdp.lesson7jpaapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7jpaapp.entity.Address;
import uz.pdp.lesson7jpaapp.entity.University;
import uz.pdp.lesson7jpaapp.payload.UniversityDto;
import uz.pdp.lesson7jpaapp.repository.AddressRepository;
import uz.pdp.lesson7jpaapp.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class UniversityController {
    @Autowired
    UniversityRepository universityRepository;
    @Autowired
    AddressRepository addressRepository;
    @RequestMapping(value = "/university",method = RequestMethod.GET)
    public List<University> getUniversities(){
        List<University> all = universityRepository.findAll();
        return all;
    }
    @RequestMapping(value = "/university",method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto universityDto){
        Address address=new Address();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());
        //dbdan address tipidagi qiymat qaytib keldi
        Address saved = addressRepository.save(address);

        University university =new University();
        university.setName(universityDto.getName());
        university.setAddress(saved);
        universityRepository.save(university);
        return "University added";

    }
    @RequestMapping(value = "university/{id}",method = RequestMethod.PUT)
    public String edit(@PathVariable Integer id,@RequestBody UniversityDto universityDto){
        Optional<University> optional = universityRepository.findById(id);
        if (optional.isPresent()) {
            University university = optional.get();
            university.setName(universityDto.getName());

            Address address = university.getAddress();
            address.setStreet(universityDto.getStreet());
            address.setDistrict(universityDto.getDistrict());
            address.setCity(universityDto.getCity());
            university.setAddress(address);
            universityRepository.save(university);
            return "University edited";

        }
        return "University not found";
    }
    @RequestMapping(value ="university/{id}",method = RequestMethod.DELETE)
    public String deleteUniversity(@PathVariable Integer id){
        universityRepository.deleteById(id);
        return "Univer deleted";
    }

}
