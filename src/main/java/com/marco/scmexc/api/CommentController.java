package com.marco.scmexc.api;

import com.marco.scmexc.models.requests.CommentRequest;
import com.marco.scmexc.models.response.CommentResponse;
import com.marco.scmexc.web.CommentMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    private final CommentMapper commentMapper;

    public CommentController(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @GetMapping("/all")
    public List<CommentResponse> getAllComments(){
        return commentMapper.findAll();
    }

    @GetMapping("/all/{id}")
    public List<CommentResponse> findById(@PathVariable Long id){
        return commentMapper.findAllByMaterialId(id);
    }

    @PostMapping("/create")
    public CommentResponse createNewMaterial(@RequestBody CommentRequest request) {
        return commentMapper.save(request);
    }

}
