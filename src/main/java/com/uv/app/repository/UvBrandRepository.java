package com.uv.app.repository;

import com.uv.app.domain.UvBrand;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UvBrand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UvBrandRepository extends JpaRepository<UvBrand, Long> {

}
