package com.marco.scmexc.services;

import com.marco.scmexc.models.domain.Material;
import com.marco.scmexc.repository.MaterialRepository;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {

    private final MaterialRepository repository;

    public MaterialService(MaterialRepository repository) {
        this.repository = repository;
    }

}
