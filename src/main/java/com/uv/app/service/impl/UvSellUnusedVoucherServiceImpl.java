package com.uv.app.service.impl;

import com.uv.app.service.UvSellUnusedVoucherService;
import com.uv.app.domain.UvSellUnusedVoucher;
import com.uv.app.repository.UvSellUnusedVoucherRepository;
import com.uv.app.service.dto.UvSellUnusedVoucherDTO;
import com.uv.app.service.mapper.UvSellUnusedVoucherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing UvSellUnusedVoucher.
 */
@Service
@Transactional
public class UvSellUnusedVoucherServiceImpl implements UvSellUnusedVoucherService{

    private final Logger log = LoggerFactory.getLogger(UvSellUnusedVoucherServiceImpl.class);

    private final UvSellUnusedVoucherRepository uvSellUnusedVoucherRepository;

    private final UvSellUnusedVoucherMapper uvSellUnusedVoucherMapper;

    public UvSellUnusedVoucherServiceImpl(UvSellUnusedVoucherRepository uvSellUnusedVoucherRepository, UvSellUnusedVoucherMapper uvSellUnusedVoucherMapper) {
        this.uvSellUnusedVoucherRepository = uvSellUnusedVoucherRepository;
        this.uvSellUnusedVoucherMapper = uvSellUnusedVoucherMapper;
    }

    /**
     * Save a uvSellUnusedVoucher.
     *
     * @param uvSellUnusedVoucherDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UvSellUnusedVoucherDTO save(UvSellUnusedVoucherDTO uvSellUnusedVoucherDTO) {
        log.debug("Request to save UvSellUnusedVoucher : {}", uvSellUnusedVoucherDTO);
        UvSellUnusedVoucher uvSellUnusedVoucher = uvSellUnusedVoucherMapper.toEntity(uvSellUnusedVoucherDTO);
        uvSellUnusedVoucher = uvSellUnusedVoucherRepository.save(uvSellUnusedVoucher);
        return uvSellUnusedVoucherMapper.toDto(uvSellUnusedVoucher);
    }

    /**
     * Get all the uvSellUnusedVouchers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UvSellUnusedVoucherDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UvSellUnusedVouchers");
        return uvSellUnusedVoucherRepository.findAll(pageable)
            .map(uvSellUnusedVoucherMapper::toDto);
    }

    /**
     * Get one uvSellUnusedVoucher by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UvSellUnusedVoucherDTO findOne(Long id) {
        log.debug("Request to get UvSellUnusedVoucher : {}", id);
        UvSellUnusedVoucher uvSellUnusedVoucher = uvSellUnusedVoucherRepository.findOne(id);
        return uvSellUnusedVoucherMapper.toDto(uvSellUnusedVoucher);
    }

    /**
     * Delete the uvSellUnusedVoucher by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UvSellUnusedVoucher : {}", id);
        uvSellUnusedVoucherRepository.delete(id);
    }
}
