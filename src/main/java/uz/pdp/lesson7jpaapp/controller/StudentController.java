package uz.pdp.lesson7jpaapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7jpaapp.entity.Student;
import uz.pdp.lesson7jpaapp.repository.FacultyRepository;
import uz.pdp.lesson7jpaapp.repository.GroupRepository;
import uz.pdp.lesson7jpaapp.repository.StudentRepository;
import uz.pdp.lesson7jpaapp.repository.UniversityRepository;

@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    UniversityRepository universityRepository;
    @GetMapping("forMinistry")
    public Page<Student> getAllStudents(@RequestParam Integer page){
        Pageable pageable= PageRequest.of(page,10);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage;


    }
    @GetMapping("forUniversity/{universityId}")
    public Page<Student> getAllStudents(@RequestParam Integer page, @PathVariable Integer universityId){
        Pageable pageable= PageRequest.of(page,10);
        Page<Student> studentPage = studentRepository.findAllByGroup_Faculty_UniversityId(universityId,pageable);
        return studentPage;


    }
}
