package com.marco.scmexc.api;

import com.marco.scmexc.models.requests.MaterialRequest;
import com.marco.scmexc.models.response.MaterialResponse;
import com.marco.scmexc.models.response.ResponseMessage;
import com.marco.scmexc.services.MaterialService;
import com.marco.scmexc.web.MaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/materials")
public class MaterialController {

    @Autowired
    private MaterialService service;
    @Autowired
    private MaterialMapper materialMapper;

    // cisto onaka da probam dali rabote :)
    @GetMapping("/all")
    public List<MaterialResponse> getAllMaterials(){
        return materialMapper.findAll();
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
    public MaterialResponse createNewMaterial(@RequestBody MaterialRequest request) {
        return materialMapper.save(request);
    }

    @PostMapping("/approve")
    public MaterialResponse approveMaterial(@RequestParam Long materialID,
                                            @RequestParam Long userID){
        return materialMapper.approve(materialID,userID);
    }
    @PostMapping("/{id}/addItem")
    public ResponseEntity<ResponseMessage> addItem(@PathVariable Long id, @RequestParam MultipartFile file) {
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
}
