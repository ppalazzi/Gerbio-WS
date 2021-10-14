package com.palazzisoft.gerbio.integrator.repository;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<AnyOrder, Long> {
}
