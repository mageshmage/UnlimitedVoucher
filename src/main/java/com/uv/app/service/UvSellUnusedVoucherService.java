package com.uv.app.service;

import com.uv.app.service.dto.UvSellUnusedVoucherDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing UvSellUnusedVoucher.
 */
public interface UvSellUnusedVoucherService {

    /**
     * Save a uvSellUnusedVoucher.
     *
     * @param uvSellUnusedVoucherDTO the entity to save
     * @return the persisted entity
     */
    UvSellUnusedVoucherDTO save(UvSellUnusedVoucherDTO uvSellUnusedVoucherDTO);

    /**
     * Get all the uvSellUnusedVouchers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UvSellUnusedVoucherDTO> findAll(Pageable pageable);

    /**
     * Get the "id" uvSellUnusedVoucher.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UvSellUnusedVoucherDTO findOne(Long id);

    /**
     * Delete the "id" uvSellUnusedVoucher.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
