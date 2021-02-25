package com.marco.scmexc.services;

import com.marco.scmexc.models.domain.*;
import com.marco.scmexc.models.requests.AddQuestionRequest;
import com.marco.scmexc.models.requests.MaterialRequest;
import com.marco.scmexc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
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

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SmxFileRepository fileRepository;

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

    public Material addItem(Long materialID,MultipartFile file) throws IOException {
        //add exception
        Material material = materialRepository.findById(materialID).orElse(null);
        SmxFile smxFile = new SmxFile();
        smxFile.setData(file.getBytes());
        smxFile.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
        smxFile.setDateUploaded(ZonedDateTime.now());
        smxFile = fileRepository.save(smxFile);
        Item item = new Item();
        item.setSmxFile(smxFile);
        item.setMaterial(material);
        if(file.getContentType().contains("jpeg") || file.getContentType().contains("png") ||
                file.getContentType().contains("gif") || file.getContentType().contains("jpg")) {
            item.setType(Type.IMAGE);
        }
        else {
            item.setType(Type.FILE);
        }
        item = itemRepository.save(item);

        material.getItems().add(item);
        return materialRepository.save(material);
    }

    public Material addQuestion(AddQuestionRequest request) {
        Material material = this.materialRepository.findById(request.materialID).orElse(null);
        Question question = new Question();
        question.setDescription(request.description);
        question = this.questionRepository.save(question);
        Item item = new Item();
        item.setQuestion(question);
        item.setMaterial(material);
        item.setType(Type.QUESTION);
        item = this.itemRepository.save(item);
        material.getItems().add(item);
        return materialRepository.save(material);
    }
}
