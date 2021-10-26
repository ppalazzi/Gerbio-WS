package com.palazzisoft.gerbio.integrator.repository;

import com.palazzisoft.gerbio.integrator.model.IntegratorError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegratorErrorRepository extends JpaRepository<IntegratorError, Long> {
}
