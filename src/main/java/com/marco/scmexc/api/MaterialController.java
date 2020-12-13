package com.marco.scmexc.api;


import com.marco.scmexc.models.domain.Material;
import com.marco.scmexc.services.MaterialService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/materials")
public class MaterialController {

    private final MaterialService service;

    public MaterialController(MaterialService service) {
        this.service = service;
    }

    @GetMapping("/all/{courseId}")
    public List<Material> getAllMaterialsByCourse(@PathVariable Long courseId){
        return service.getAllMaterialsByCourse(courseId);
    }

}