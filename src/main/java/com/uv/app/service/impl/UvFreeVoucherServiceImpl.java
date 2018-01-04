package com.uv.app.service.impl;

import com.uv.app.service.UvFreeVoucherService;
import com.uv.app.domain.UvFreeVoucher;
import com.uv.app.repository.UvFreeVoucherRepository;
import com.uv.app.service.dto.UvFreeVoucherDTO;
import com.uv.app.service.mapper.UvFreeVoucherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing UvFreeVoucher.
 */
@Service
@Transactional
public class UvFreeVoucherServiceImpl implements UvFreeVoucherService{

    private final Logger log = LoggerFactory.getLogger(UvFreeVoucherServiceImpl.class);

    private final UvFreeVoucherRepository uvFreeVoucherRepository;

    private final UvFreeVoucherMapper uvFreeVoucherMapper;

    public UvFreeVoucherServiceImpl(UvFreeVoucherRepository uvFreeVoucherRepository, UvFreeVoucherMapper uvFreeVoucherMapper) {
        this.uvFreeVoucherRepository = uvFreeVoucherRepository;
        this.uvFreeVoucherMapper = uvFreeVoucherMapper;
    }

    /**
     * Save a uvFreeVoucher.
     *
     * @param uvFreeVoucherDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UvFreeVoucherDTO save(UvFreeVoucherDTO uvFreeVoucherDTO) {
        log.debug("Request to save UvFreeVoucher : {}", uvFreeVoucherDTO);
        UvFreeVoucher uvFreeVoucher = uvFreeVoucherMapper.toEntity(uvFreeVoucherDTO);
        uvFreeVoucher = uvFreeVoucherRepository.save(uvFreeVoucher);
        return uvFreeVoucherMapper.toDto(uvFreeVoucher);
    }

    /**
     * Get all the uvFreeVouchers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UvFreeVoucherDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UvFreeVouchers");
        return uvFreeVoucherRepository.findAll(pageable)
            .map(uvFreeVoucherMapper::toDto);
    }

    /**
     * Get one uvFreeVoucher by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UvFreeVoucherDTO findOne(Long id) {
        log.debug("Request to get UvFreeVoucher : {}", id);
        UvFreeVoucher uvFreeVoucher = uvFreeVoucherRepository.findOne(id);
        return uvFreeVoucherMapper.toDto(uvFreeVoucher);
    }

    /**
     * Delete the uvFreeVoucher by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UvFreeVoucher : {}", id);
        uvFreeVoucherRepository.delete(id);
    }
}
