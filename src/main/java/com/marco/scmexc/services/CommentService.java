package com.marco.scmexc.services;

import com.marco.scmexc.models.domain.Comment;
import com.marco.scmexc.models.domain.Course;
import com.marco.scmexc.models.domain.Material;
import com.marco.scmexc.models.domain.SmxUser;
import com.marco.scmexc.models.requests.CommentRequest;
import com.marco.scmexc.models.requests.MaterialRequest;
import com.marco.scmexc.models.response.MaterialResponse;
import com.marco.scmexc.repository.CommentRepository;
import com.marco.scmexc.repository.MaterialRepository;
import com.marco.scmexc.repository.SmxUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private SmxUserRepository userRepository;
    @Autowired
    private MaterialRepository materialRepository;

    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    public List<Comment> getAllCommentForMaterial(Long materialID){
        return this.commentRepository.findALlByMaterialId(materialID);
    }

    public Comment save(CommentRequest commentRequest){
        Comment comment = new Comment();
        comment.setId(commentRequest.id);
        comment.setDescription(commentRequest.description);
        SmxUser createdBy = userRepository.findSmxUserByEmail(commentRequest.createdByUserEmail).orElse(null);
        comment.setCreatedBy(createdBy);
        Material material = materialRepository.findById(commentRequest.materialId).orElse(null);
        comment.setMaterial(material);
        comment.setUpvotes(0);
        comment.setDownvotes(0);
        comment.setDatePosted(ZonedDateTime.now());
        return this.commentRepository.save(comment);
    }

}
