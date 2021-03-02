package com.marco.scmexc.services;

import com.marco.scmexc.models.domain.*;
import com.marco.scmexc.models.exceptions.CourseNotFoundException;
import com.marco.scmexc.models.exceptions.FileIsNullException;
import com.marco.scmexc.models.exceptions.MaterialNotFoundException;
import com.marco.scmexc.models.exceptions.UserNotFoundException;
import com.marco.scmexc.models.requests.AddQuestionRequest;
import com.marco.scmexc.models.requests.MaterialRequest;
import com.marco.scmexc.repository.*;
import com.marco.scmexc.security.UserPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class MaterialService {


    private final MaterialRepository materialRepository;

    private final SmxUserRepository userRepository;

    private final CourseRepository courseRepository;

    private final ItemRepository itemRepository;

    private final QuestionRepository questionRepository;


    private final SmxFileRepository fileRepository;

    public MaterialService(MaterialRepository materialRepository, SmxUserRepository userRepository, CourseRepository courseRepository, ItemRepository itemRepository, QuestionRepository questionRepository, SmxFileRepository fileRepository) {
        this.materialRepository = materialRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.itemRepository = itemRepository;
        this.questionRepository = questionRepository;
        this.fileRepository = fileRepository;
    }

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
        Course course = courseRepository.findById(materialRequest.courseId).orElseThrow(()->new CourseNotFoundException(materialRequest.courseId));
        material.setCourse(course);
        material.setUpVotes(0);
        material.setDownVotes(0);
        material.setDateCreated(ZonedDateTime.now());
        return this.materialRepository.save(material);
    }

    public Material findById(Long materialId){
        //TODO exception
        return this.materialRepository.findById(materialId).orElseThrow(()->new MaterialNotFoundException(materialId));
    }

    public Material approve(Long materialID,Long userID){
        // sredi exceptions posle
        Material material = materialRepository.findById(materialID).orElseThrow(()-> new MaterialNotFoundException(materialID));
        SmxUser user = userRepository.findById(userID).orElseThrow(()->new UserNotFoundException(userID));
        if(material != null && user != null){
            material.setApprovedBy(user);
            material.setPublished(true);
        }
        return materialRepository.save(material);
    }

    public Material addItem(Long materialID,MultipartFile file) throws IOException {
        //add exception
        Material material = materialRepository.findById(materialID).orElseThrow(() -> new MaterialNotFoundException(materialID));
        if(file==null) {
            throw new FileIsNullException();
        }
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
       itemRepository.save(item);


        return material;
    }

    public Boolean canUserAccessEditMaterial(Long materialId, UserPrincipal userPrincipal) {
        if(userPrincipal.hasRole(Role.ADMIN) || userPrincipal.hasRole(Role.SUPER_ADMIN) || userPrincipal.hasRole(Role.MODERATOR)) return  true;
        Material material = materialRepository.findById(materialId).orElseThrow(()->new MaterialNotFoundException(materialId));
        return material.getCreatedBy().getUsername() == userPrincipal.getUsername();
    }

    public Material addQuestion(AddQuestionRequest request) {
        Material material = this.materialRepository.findById(request.materialID).orElseThrow(()->new MaterialNotFoundException(request.materialID));
        Question question = new Question();
        question.setDescription(request.description);
        question = this.questionRepository.save(question);
        Item item = new Item();
        item.setQuestion(question);
        item.setMaterial(material);
        item.setType(Type.QUESTION);
        this.itemRepository.save(item);
        return  material;
    }
}
