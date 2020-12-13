package com.marco.scmexc.repository;

import com.marco.scmexc.models.domain.SmxUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmxUserRepository extends JpaRepository<SmxUser, Long> {
    Optional<SmxUser> findSmxUserByEmail(String email);
}
