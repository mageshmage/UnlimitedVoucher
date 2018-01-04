package com.uv.app.service;

import com.uv.app.service.dto.UvCategoryDTO;
import java.util.List;

/**
 * Service Interface for managing UvCategory.
 */
public interface UvCategoryService {

    /**
     * Save a uvCategory.
     *
     * @param uvCategoryDTO the entity to save
     * @return the persisted entity
     */
    UvCategoryDTO save(UvCategoryDTO uvCategoryDTO);

    /**
     * Get all the uvCategories.
     *
     * @return the list of entities
     */
    List<UvCategoryDTO> findAll();

    /**
     * Get the "id" uvCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UvCategoryDTO findOne(Long id);

    /**
     * Delete the "id" uvCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
