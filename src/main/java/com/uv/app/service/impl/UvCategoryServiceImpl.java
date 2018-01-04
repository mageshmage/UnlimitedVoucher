package com.uv.app.service.impl;

import com.uv.app.service.UvCategoryService;
import com.uv.app.domain.UvCategory;
import com.uv.app.repository.UvCategoryRepository;
import com.uv.app.service.dto.UvCategoryDTO;
import com.uv.app.service.mapper.UvCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UvCategory.
 */
@Service
@Transactional
public class UvCategoryServiceImpl implements UvCategoryService{

    private final Logger log = LoggerFactory.getLogger(UvCategoryServiceImpl.class);

    private final UvCategoryRepository uvCategoryRepository;

    private final UvCategoryMapper uvCategoryMapper;

    public UvCategoryServiceImpl(UvCategoryRepository uvCategoryRepository, UvCategoryMapper uvCategoryMapper) {
        this.uvCategoryRepository = uvCategoryRepository;
        this.uvCategoryMapper = uvCategoryMapper;
    }

    /**
     * Save a uvCategory.
     *
     * @param uvCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UvCategoryDTO save(UvCategoryDTO uvCategoryDTO) {
        log.debug("Request to save UvCategory : {}", uvCategoryDTO);
        UvCategory uvCategory = uvCategoryMapper.toEntity(uvCategoryDTO);
        uvCategory = uvCategoryRepository.save(uvCategory);
        return uvCategoryMapper.toDto(uvCategory);
    }

    /**
     * Get all the uvCategories.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UvCategoryDTO> findAll() {
        log.debug("Request to get all UvCategories");
        return uvCategoryRepository.findAll().stream()
            .map(uvCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one uvCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UvCategoryDTO findOne(Long id) {
        log.debug("Request to get UvCategory : {}", id);
        UvCategory uvCategory = uvCategoryRepository.findOne(id);
        return uvCategoryMapper.toDto(uvCategory);
    }

    /**
     * Delete the uvCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UvCategory : {}", id);
        uvCategoryRepository.delete(id);
    }
}
