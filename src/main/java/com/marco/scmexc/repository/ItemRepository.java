package com.marco.scmexc.repository;

import com.marco.scmexc.models.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {



}
