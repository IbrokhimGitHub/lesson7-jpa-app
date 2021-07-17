package uz.pdp.lesson7jpaapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7jpaapp.entity.Address;
import uz.pdp.lesson7jpaapp.entity.Group;
import uz.pdp.lesson7jpaapp.entity.Student;
import uz.pdp.lesson7jpaapp.entity.Subject;
import uz.pdp.lesson7jpaapp.payload.StudentDto;
import uz.pdp.lesson7jpaapp.repository.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AddressRepository addressRepository;
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
    public Page<Student> getUniversityStudents(@RequestParam Integer page, @PathVariable Integer universityId){
        Pageable pageable= PageRequest.of(page,10);
        Page<Student> studentPage = studentRepository.findAllByGroup_Faculty_UniversityId(universityId,pageable);
        return studentPage;


    }
    @GetMapping("forFaculty/{facultyId}")
    public Page<Student> getFacultyStudents(@RequestParam Integer page, @PathVariable Integer facultyId){
        Pageable pageable= PageRequest.of(page,10);
        Page<Student> studentPage = studentRepository.findAllByGroup_FacultyId(facultyId,pageable);
        return studentPage;


    }
    @GetMapping("forFaculty/{groupId}")
    public Page<Student> getGroupStudents(@RequestParam Integer page, @PathVariable Integer groupId){
        Pageable pageable= PageRequest.of(page,10);
        Page<Student> studentPage = studentRepository.findAllByGroupId(groupId,pageable);
        return studentPage;


    }
    @PostMapping
    public String addStudent(@RequestBody StudentDto studentDto){
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            Optional<Address> optionalAddress = addressRepository.findById(studentDto.getAddressId());
            if (optionalAddress.isPresent()) {
                Address address = optionalAddress.get();
                List<Subject> subjectList = subjectRepository.findAllById(studentDto.getSubjectsId());
                Student student=new Student();
                student.setAddress(address);
                student.setGroup(group);
                student.setSubject(subjectList);
                student.setFirstName(studentDto.getFirstName());
                student.setLastName(studentDto.getLastName());
                Student savedStudent = studentRepository.save(student);
                return "new student added with id: "+savedStudent.getId();


            }else {return "such address not found";}

        }else {return "such group not found";}
    }
    @DeleteMapping
    public String delete(@PathVariable Integer id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent()) {
            return "student with id: "+id+" not found";
        }
        studentRepository.deleteById(id);
        return "student with id: "+id+" deleted";
    }
    @PutMapping("{studentId}")
    public String edit(@PathVariable Integer studentId,@RequestBody StudentDto studentDto){
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            Optional<Address> optionalAddress = addressRepository.findById(studentDto.getAddressId());
            if (optionalAddress.isPresent()) {
                Address address = optionalAddress.get();
                List<Subject> subjectList = subjectRepository.findAllById(studentDto.getSubjectsId());
                Optional<Student> optionalStudent = studentRepository.findById(studentId);
                if (!optionalStudent.isPresent()) {
                    return "student with id: "+studentId+" not found";
                }
                Student student = optionalStudent.get();
                student.setAddress(address);
                student.setGroup(group);
                student.setSubject(subjectList);
                student.setFirstName(studentDto.getFirstName());
                student.setLastName(studentDto.getLastName());
                studentRepository.save(student);
                return "student edited with id: "+student.getId();


            }else {return "such address not found";}

        }else {return "such group not found";}
    }

}
