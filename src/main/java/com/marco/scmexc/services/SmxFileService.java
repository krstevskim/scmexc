package com.marco.scmexc.services;

import com.marco.scmexc.models.domain.SmxFile;
import com.marco.scmexc.repository.SmxFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class SmxFileService {

    @Autowired
    private SmxFileRepository repository;

    public SmxFile getFile(Long id) {
        //exception to add
        return repository.findById(id).orElse(null);
    }

    public Stream<SmxFile> getAllFiles() {
        return repository.findAll().stream();
    }

    public Stream<SmxFile> getAllFilesByMaterial(Long materialID) {
        return repository.findAll().stream().filter(smxFile -> smxFile.getItem().getMaterial().getId().equals(materialID));
    }
}
