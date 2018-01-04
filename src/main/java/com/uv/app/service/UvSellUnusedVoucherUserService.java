package com.uv.app.service;

import com.uv.app.service.dto.UvSellUnusedVoucherUserDTO;
import java.util.List;

/**
 * Service Interface for managing UvSellUnusedVoucherUser.
 */
public interface UvSellUnusedVoucherUserService {

    /**
     * Save a uvSellUnusedVoucherUser.
     *
     * @param uvSellUnusedVoucherUserDTO the entity to save
     * @return the persisted entity
     */
    UvSellUnusedVoucherUserDTO save(UvSellUnusedVoucherUserDTO uvSellUnusedVoucherUserDTO);

    /**
     * Get all the uvSellUnusedVoucherUsers.
     *
     * @return the list of entities
     */
    List<UvSellUnusedVoucherUserDTO> findAll();

    /**
     * Get the "id" uvSellUnusedVoucherUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UvSellUnusedVoucherUserDTO findOne(Long id);

    /**
     * Delete the "id" uvSellUnusedVoucherUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
