package uz.pdp.lesson7jpaapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7jpaapp.entity.Faculty;
import uz.pdp.lesson7jpaapp.entity.University;
import uz.pdp.lesson7jpaapp.payload.FacultyDataTransferObject;
import uz.pdp.lesson7jpaapp.repository.FacultyRepository;
import uz.pdp.lesson7jpaapp.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    UniversityRepository universityRepository;

    @PostMapping
    public String add(@RequestBody FacultyDataTransferObject facultyDataTransferObject) {
        boolean existsByNameAndUniversityId = facultyRepository.existsByNameAndUniversityId(facultyDataTransferObject.getName(), facultyDataTransferObject.getUniversityId());
        if (existsByNameAndUniversityId)
            return "such university is exist";

        Faculty faculty = new Faculty();
        faculty.setName(facultyDataTransferObject.getName());


        Optional<University> optionalUniversity = universityRepository.findById(facultyDataTransferObject.getUniversityId());
        if (!optionalUniversity.isPresent()) {
            return "University not found";
        }
        faculty.setUniversity(optionalUniversity.get());
        facultyRepository.save(faculty);
        return "Faculty saved";

    }
    //vazirlik uchun
    @GetMapping
    public List<Faculty> getFaculties() {
        List<Faculty> allByUniversityId = facultyRepository.findAll();
        return allByUniversityId;

    }
    //universitet uchun
    @GetMapping("byUniversityId/{universityId}")
     public List<Faculty> getFacultiesByUniversityId(@PathVariable Integer universityId) {
        List<Faculty> allByUniversityId = facultyRepository.findAllByUniversityId(universityId);
        return allByUniversityId;
    }
//    @GetMapping("byUniversityId/{id}")
//    public List<Faculty> getFacultiesByUniversityId(@PathVariable Integer universityId) {
//        List<Faculty> allByUniversityId = facultyRepository.findAllByUniversityId(universityId);
//        return allByUniversityId;
//    }
    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id){
        try{
            facultyRepository.deleteById(id);
            return "faculty deleted";
        }catch (Exception e){
            return "error in deleting";
        }
    }
    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id,@RequestBody FacultyDataTransferObject facultyDto){
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()) {
            Faculty faculty = optionalFaculty.get();
            faculty.setName(facultyDto.getName());
            Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
            if (!optionalUniversity.isPresent()) {
                return "faculty not found";
            }
            faculty.setUniversity(optionalUniversity.get());
            facultyRepository.save(faculty);
            return "Faculty edited";
        }


        return "faculty not found";
    }


}
