package com.marco.scmexc.web;

import com.marco.scmexc.models.domain.Material;
import com.marco.scmexc.models.requests.MaterialRequest;
import com.marco.scmexc.models.response.MaterialResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MaterialMapper {

    List<MaterialResponse> findAll();
    List<MaterialResponse> findAllApprovedMaterialsByCourseId(Long id);
    List<MaterialResponse> findAllPendingMaterialsByCourseId(Long id);
    List<MaterialResponse> findAllPendingMaterials();
    MaterialResponse findById(Long id);
    MaterialResponse save(MaterialRequest materialRequest);
    MaterialResponse approve(Long materialID,Long userID);

}
