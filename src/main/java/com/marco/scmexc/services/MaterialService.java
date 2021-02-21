package com.marco.scmexc.services;

import com.marco.scmexc.models.domain.Course;
import com.marco.scmexc.models.domain.Material;
import com.marco.scmexc.models.domain.SmxUser;
import com.marco.scmexc.models.requests.MaterialRequest;
import com.marco.scmexc.repository.CourseRepository;
import com.marco.scmexc.repository.MaterialRepository;
import com.marco.scmexc.repository.SmxUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private SmxUserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    public List<Material> findAll(){
        return materialRepository.findAll();
    }

    public List<Material> getAllMaterialsByCourse(Long courseId){
        return this.materialRepository.findAllByCourse_Id(courseId);
    }

    public Material save(MaterialRequest materialRequest){
        Material material = new Material();
        material.setId(materialRequest.id);
        material.setTitle(materialRequest.title);
        material.setDescription(materialRequest.description);
        SmxUser createdBy = userRepository.findSmxUserByEmail(materialRequest.createdByEmail).orElse(null);
        material.setCreatedBy(createdBy);
        Course course = courseRepository.findById(materialRequest.courseId).orElse(null);
        material.setCourse(course);
        material.setUpVotes(0);
        material.setDownVotes(0);
        material.setDateCreated(ZonedDateTime.now());
        return this.materialRepository.save(material);
    }

    public Material findById(Long materialId){
        //TODO exception
        return this.materialRepository.findById(materialId).orElse(null);
    }

    public Material approve(Long materialID,Long userID){
        // sredi exceptions posle
        Material material = materialRepository.findById(materialID).orElse(null);
        SmxUser user = userRepository.findById(userID).orElse(null);
        if(material != null && user != null){
            material.setApprovedBy(user);
            material.setPublished(true);
        }
        return materialRepository.save(material);
    }
}
