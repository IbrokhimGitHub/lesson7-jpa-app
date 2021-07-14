package uz.pdp.lesson7jpaapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson7jpaapp.entity.Faculty;
import uz.pdp.lesson7jpaapp.entity.Group;
import uz.pdp.lesson7jpaapp.payload.GroupDto;
import uz.pdp.lesson7jpaapp.repository.FacultyRepository;
import uz.pdp.lesson7jpaapp.repository.GroupRepository;
import uz.pdp.lesson7jpaapp.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    FacultyRepository facultyRepository;
//vazirlik uchun
    @GetMapping
    public List<Group> get() {
        List<Group> all = groupRepository.findAll();
        return all;
    }
    @GetMapping("byUniversityId/{universityId}")
    public  List<Group> getGroupsByUniversityId(@PathVariable Integer universityId){
        List<Group> allByFaculty_universityId = groupRepository.findAllByFaculty_UniversityId(universityId);
        List<Group> groupsById = groupRepository.getGroupsById(universityId);
        List<Group> groupsByUniversityIdNative = groupRepository.getGroupsByUniversityIdNative(universityId);
        return allByFaculty_universityId;


    }
    @PostMapping
    public  String add(@RequestBody GroupDto groupDto){
        Group group =new Group();
        group.setName(groupDto.getName());
        Optional<Faculty> byId = facultyRepository.findById(groupDto.getFacultyId());
        if (!byId.isPresent()) {
            return "such faculty not found";
        }
        group.setFaculty(byId.get());
        groupRepository.save(group);
        return "group added";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        Optional<Group> byId = groupRepository.findById(id);
        if (byId.isPresent()) {
            groupRepository.deleteById(id);
            return "group deleted";
        }
        else {
            return "Cant find group";
        }
    }
    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id, @RequestBody GroupDto groupDto){
        Optional<Group> groupOptional = groupRepository.findById(id);
        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();

            Optional<Faculty> byId = facultyRepository.findById(groupDto.getFacultyId());
            if (byId.isPresent()) {
                group.setName(groupDto.name);
                group.setFaculty(byId.get());
                groupRepository.save(group);
                return "Group changed";

            }else {
                return "cant find such faculty";
            }
        }else {
            return "Cant find such group";
        }



    }


}
