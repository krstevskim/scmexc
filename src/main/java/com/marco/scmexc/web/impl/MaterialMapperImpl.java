package com.marco.scmexc.web.impl;

import com.marco.scmexc.models.domain.Item;
import com.marco.scmexc.models.domain.Material;
import com.marco.scmexc.models.domain.Type;
import com.marco.scmexc.models.requests.MaterialRequest;
import com.marco.scmexc.models.response.ItemResponse;
import com.marco.scmexc.models.response.MaterialResponse;
import com.marco.scmexc.repository.MaterialRepository;
import com.marco.scmexc.services.MaterialService;
import com.marco.scmexc.web.MaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialMapperImpl implements MaterialMapper {

    @Autowired
    private MaterialService materialService;

    @Override
    public List<MaterialResponse> findAll() {
        return materialService.findAll()
                .stream()
                .map(this::mapMaterialToMaterialResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MaterialResponse> findAllApprovedMaterialsByCourseId(Long id) {
        return materialService.getAllMaterialsByCourse(id)
                .stream()
                .filter(Material::isPublished)
                .map(this::mapMaterialToMaterialResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MaterialResponse> findAllPendingMaterialsByCourseId(Long id) {
        return materialService.getAllMaterialsByCourse(id)
                .stream()
                .filter(x -> !x.isPublished())
                .map(this::mapMaterialToMaterialResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MaterialResponse> findAllPendingMaterials() {
        return materialService.findAll()
                .stream()
                .filter(x -> !x.isPublished())
                .map(this::mapMaterialToMaterialResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MaterialResponse findById(Long id) {
        return mapMaterialToMaterialResponse(materialService.findById(id));
    }

    @Override
    public MaterialResponse save(MaterialRequest materialRequest) {
        Material material = materialService.save(materialRequest);
        return mapMaterialToMaterialResponse(material);
    }

    @Override
    public MaterialResponse approve(Long materialID, Long userID) {
        return mapMaterialToMaterialResponse(materialService.approve(materialID,userID));
    }



    private MaterialResponse mapMaterialToMaterialResponse(Material material){
        return MaterialResponse.of(material.getId(),material.getTitle(),material.getCreatedBy() != null ? material.getCreatedBy().getUsername() : null,
                material.getDateCreated(), material.isPublished(), material.getDescription(),material.getApprovedBy() != null ?  material.getApprovedBy().getUsername() : null,
                material.getUpVotes(), material.getDownVotes(), mapMaterialItemsToResponse(material.getItems()));
    }

    private List<ItemResponse> mapMaterialItemsToResponse(List<Item> items) {
        if(items == null) {
            return List.of();
        }
        return items.stream().map(item -> {
            if(item.getType() == Type.QUESTION) {
                return  ItemResponse.of(null,null,Type.QUESTION, ZonedDateTime.now(),item.getQuestion().getDescription(), item.getId());
            }
            else {
                String url = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/files/getFile/")
                        .path(item.getSmxFile().getId().toString())
                        .toUriString();

                return ItemResponse.of(item.getSmxFile().getFileName(),url,item.getType(),ZonedDateTime.now(),null, item.getId());
            }
        }).collect(Collectors.toList());
    }

}
