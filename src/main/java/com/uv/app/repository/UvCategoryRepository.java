package com.uv.app.repository;

import com.uv.app.domain.UvCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UvCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UvCategoryRepository extends JpaRepository<UvCategory, Long> {

}
