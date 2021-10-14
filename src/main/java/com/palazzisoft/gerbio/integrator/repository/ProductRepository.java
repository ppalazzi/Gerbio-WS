package com.palazzisoft.gerbio.integrator.repository;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<AnyProduct, Long> {
}
