package com.marco.scmexc.repository;

import com.marco.scmexc.models.domain.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    List<Material> findAllByCourse_Id(Long id);

    Page<Material> findAllByTitleAndCourse_id(String title, Long courseId, Pageable pageable);

    Page<Material> findAllByCourse_id( Long courseId, Pageable pageable);

    Page<Material> findAllByTitle(String title, Pageable pageable);


}
