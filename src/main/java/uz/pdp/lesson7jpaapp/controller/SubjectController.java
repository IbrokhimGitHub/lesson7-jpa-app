package uz.pdp.lesson7jpaapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7jpaapp.entity.Subject;
import uz.pdp.lesson7jpaapp.repository.SubjectRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;

    @RequestMapping(method = RequestMethod.POST)
    public String add(@RequestBody Subject subject){
        boolean existsByName = subjectRepository.existsByName(subject.getName());
        if (existsByName)
            return "This subject already exist";

        subjectRepository.save(subject);
        return "Subject added";

    }
    @GetMapping
    public List<Subject> getSubjects(){
        List<Subject> all = subjectRepository.findAll();
        return all;
    }
    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id, @RequestBody Subject subject){
        Optional<Subject> byId = subjectRepository.findById(id);
        if (byId.isPresent()) {
            Subject subject1 = byId.get();
            subject1.setName(subject.getName());
        }else {
            return "no subject with such id";
        }
        return "subject changed";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        Optional<Subject> byId = subjectRepository.findById(id);
        if (byId.isPresent()) {
            subjectRepository.deleteById(id);
            return "Subject deleted";
        }else {
            return "no subject with such id";
        }
    }

}
