package com.marco.scmexc.api;

import com.marco.scmexc.models.domain.Material;
import com.marco.scmexc.models.requests.AddQuestionRequest;
import com.marco.scmexc.models.requests.MaterialRequest;
import com.marco.scmexc.models.response.MaterialResponse;
import com.marco.scmexc.models.response.ResponseMessage;
import com.marco.scmexc.security.CurrentUser;
import com.marco.scmexc.security.UserPrincipal;
import com.marco.scmexc.services.ItemService;
import com.marco.scmexc.services.MaterialService;
import com.marco.scmexc.web.MaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("api/materials")
public class MaterialController {

    @Autowired
    private MaterialService service;
    @Autowired
    private ItemService itemService;
    @Autowired
    private MaterialMapper materialMapper;

    // cisto onaka da probam dali rabote :)
    @GetMapping("/all")
    public List<MaterialResponse> getAllMaterials(){
        return materialMapper.findAll();
    }

    @GetMapping("/paged")
    public Page<Material> getAllMaterialsPaged(@RequestParam(required = false, defaultValue = "", name = "q") String searchQuery,
                                               @RequestParam(required = false) Long course, Pageable pageable, @CurrentUser UserPrincipal userPrincipal) {
        return materialMapper.getAllMaterialsPaged(searchQuery, course, pageable, userPrincipal);
    }

    @GetMapping("/all/approved/{courseId}")
    public List<MaterialResponse> getAllApprovedMaterialsByCourse(@PathVariable Long courseId){
        return materialMapper.findAllApprovedMaterialsByCourseId(courseId);
    }

    @GetMapping("/all/pending/{courseId}")
    public List<MaterialResponse> getAllPendingMaterialsByCourse(@PathVariable Long courseId){
        return materialMapper.findAllPendingMaterialsByCourseId(courseId);
    }

    @GetMapping("/all/pending")
    public List<MaterialResponse> getAllPendingMaterials(){
        return materialMapper.findAllPendingMaterials();
    }

    @GetMapping("/{id}")
    public MaterialResponse findById(@PathVariable Long id){
        return materialMapper.findById(id);
    }

    @PostMapping("/create")
    public MaterialResponse createNewMaterial(@RequestBody MaterialRequest request, @CurrentUser UserPrincipal userPrincipal) {
        return materialMapper.save(request, userPrincipal);
    }

    @PostMapping("/approve")
    public MaterialResponse approveMaterial(@RequestParam Long materialID, @CurrentUser UserPrincipal userPrincipal){
        return materialMapper.approve(materialID,userPrincipal);
    }
    @PostMapping("/{id}/addFile")
    public ResponseEntity<ResponseMessage> addFile(@PathVariable Long id, @RequestParam MultipartFile file) {
       String message ;
        try {
             service.addItem(id, file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }

    }

    @PostMapping("/addQuestion")
    public ResponseEntity<ResponseMessage> addQuestion(@RequestBody AddQuestionRequest request){
        service.addQuestion(request);
        return  ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Question has been added"));
    }

    @PostMapping("can-access/{id}")
    public ResponseEntity<Boolean> canAccessMaterial(@PathVariable Long id,
            @CurrentUser UserPrincipal userPrincipal) {
        boolean canAccess = service.canUserAccessEditMaterial(id, userPrincipal);
                return canAccess ? ResponseEntity.ok(true) : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
