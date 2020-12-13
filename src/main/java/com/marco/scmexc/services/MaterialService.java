package com.marco.scmexc.services;

import com.marco.scmexc.models.domain.Material;
import com.marco.scmexc.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository repository;

    public MaterialService(MaterialRepository repository) {
        this.repository = repository;
    }

    public List<Material> getAllMaterialsByCourse(Long courseId){
        return this.repository.findAllByCourse_Id(courseId);
    }


    public Material save(Material material){
        return this.repository.save(material);
    }

    public Material findById(Long materialId){
        //TODO exception
        return this.repository.findById(materialId).orElseThrow();
    }
}
