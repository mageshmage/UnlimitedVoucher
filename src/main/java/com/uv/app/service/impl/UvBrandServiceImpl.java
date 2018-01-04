package com.uv.app.service.impl;

import com.uv.app.service.UvBrandService;
import com.uv.app.domain.UvBrand;
import com.uv.app.repository.UvBrandRepository;
import com.uv.app.service.dto.UvBrandDTO;
import com.uv.app.service.mapper.UvBrandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UvBrand.
 */
@Service
@Transactional
public class UvBrandServiceImpl implements UvBrandService{

    private final Logger log = LoggerFactory.getLogger(UvBrandServiceImpl.class);

    private final UvBrandRepository uvBrandRepository;

    private final UvBrandMapper uvBrandMapper;

    public UvBrandServiceImpl(UvBrandRepository uvBrandRepository, UvBrandMapper uvBrandMapper) {
        this.uvBrandRepository = uvBrandRepository;
        this.uvBrandMapper = uvBrandMapper;
    }

    /**
     * Save a uvBrand.
     *
     * @param uvBrandDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UvBrandDTO save(UvBrandDTO uvBrandDTO) {
        log.debug("Request to save UvBrand : {}", uvBrandDTO);
        UvBrand uvBrand = uvBrandMapper.toEntity(uvBrandDTO);
        uvBrand = uvBrandRepository.save(uvBrand);
        return uvBrandMapper.toDto(uvBrand);
    }

    /**
     * Get all the uvBrands.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UvBrandDTO> findAll() {
        log.debug("Request to get all UvBrands");
        return uvBrandRepository.findAll().stream()
            .map(uvBrandMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one uvBrand by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UvBrandDTO findOne(Long id) {
        log.debug("Request to get UvBrand : {}", id);
        UvBrand uvBrand = uvBrandRepository.findOne(id);
        return uvBrandMapper.toDto(uvBrand);
    }

    /**
     * Delete the uvBrand by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UvBrand : {}", id);
        uvBrandRepository.delete(id);
    }
}
