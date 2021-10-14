package com.palazzisoft.gerbio.integrator.repository;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<AnyCategory, Long> {

    AnyCategory findAnyCategoryByPartnerId(String partnerId);
}
