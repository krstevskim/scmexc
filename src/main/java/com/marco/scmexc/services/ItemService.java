package com.marco.scmexc.services;

import com.marco.scmexc.models.domain.Item;
import com.marco.scmexc.models.domain.Type;
import com.marco.scmexc.repository.ItemRepository;
import com.marco.scmexc.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final MaterialRepository materialRepository;
    private final ItemRepository itemRepository;

    public ItemService(MaterialRepository materialRepository, ItemRepository itemRepository) {
        this.materialRepository = materialRepository;
        this.itemRepository = itemRepository;
    }

    public List<Item> getItemsByMaterial(Long materialID) {
        // exception
       return this.itemRepository.findAllByMaterial_Id(materialID);
    }

    public List<Item> getAllFilesByMaterial(Long materialID) {
        return this.itemRepository.findAllByMaterial_IdAndType(materialID,Type.FILE);
    }
    public List<Item> getAllImagesByMaterial(Long materialID) {
        return this.itemRepository.findAllByMaterial_IdAndType(materialID,Type.IMAGE);
    }
    public List<Item> findAllQuestionsByMaterial(Long materialID) {
        return this.itemRepository.findAllByMaterial_IdAndType(materialID,Type.QUESTION);
    }




}
