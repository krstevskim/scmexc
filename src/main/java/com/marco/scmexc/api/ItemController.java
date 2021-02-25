package com.marco.scmexc.api;

import com.marco.scmexc.models.domain.Type;
import com.marco.scmexc.models.response.ItemResponse;
import com.marco.scmexc.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ItemController {

    private final ItemService itemService;


    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/api/materials/{id}/getItems")
    public List<ItemResponse> getItemsByMaterial(@PathVariable Long id){
        List<ItemResponse> items = this.itemService.getItemsByMaterial(id)
                .stream().map(item -> {
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
        return items;
    }
}
