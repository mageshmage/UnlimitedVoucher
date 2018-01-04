package com.uv.app.service;

import com.uv.app.service.dto.UvBrandDTO;
import java.util.List;

/**
 * Service Interface for managing UvBrand.
 */
public interface UvBrandService {

    /**
     * Save a uvBrand.
     *
     * @param uvBrandDTO the entity to save
     * @return the persisted entity
     */
    UvBrandDTO save(UvBrandDTO uvBrandDTO);

    /**
     * Get all the uvBrands.
     *
     * @return the list of entities
     */
    List<UvBrandDTO> findAll();

    /**
     * Get the "id" uvBrand.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UvBrandDTO findOne(Long id);

    /**
     * Delete the "id" uvBrand.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
