package com.uv.app.service;

import com.uv.app.service.dto.UvFreeVoucherDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing UvFreeVoucher.
 */
public interface UvFreeVoucherService {

    /**
     * Save a uvFreeVoucher.
     *
     * @param uvFreeVoucherDTO the entity to save
     * @return the persisted entity
     */
    UvFreeVoucherDTO save(UvFreeVoucherDTO uvFreeVoucherDTO);

    /**
     * Get all the uvFreeVouchers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UvFreeVoucherDTO> findAll(Pageable pageable);

    /**
     * Get the "id" uvFreeVoucher.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UvFreeVoucherDTO findOne(Long id);

    /**
     * Delete the "id" uvFreeVoucher.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
