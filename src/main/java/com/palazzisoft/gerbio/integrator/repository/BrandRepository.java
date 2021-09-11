package com.palazzisoft.gerbio.integrator.repository;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<AnyBrand, Long> {
}
