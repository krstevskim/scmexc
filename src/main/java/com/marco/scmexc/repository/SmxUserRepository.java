package com.marco.scmexc.repository;

import com.marco.scmexc.models.domain.SmxUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmxUserRepository extends JpaRepository<SmxUser, Long> {
}
