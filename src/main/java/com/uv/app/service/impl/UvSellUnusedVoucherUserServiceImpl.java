package com.uv.app.service.impl;

import com.uv.app.service.UvSellUnusedVoucherUserService;
import com.uv.app.domain.UvSellUnusedVoucherUser;
import com.uv.app.repository.UvSellUnusedVoucherUserRepository;
import com.uv.app.service.dto.UvSellUnusedVoucherUserDTO;
import com.uv.app.service.mapper.UvSellUnusedVoucherUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UvSellUnusedVoucherUser.
 */
@Service
@Transactional
public class UvSellUnusedVoucherUserServiceImpl implements UvSellUnusedVoucherUserService{

    private final Logger log = LoggerFactory.getLogger(UvSellUnusedVoucherUserServiceImpl.class);

    private final UvSellUnusedVoucherUserRepository uvSellUnusedVoucherUserRepository;

    private final UvSellUnusedVoucherUserMapper uvSellUnusedVoucherUserMapper;

    public UvSellUnusedVoucherUserServiceImpl(UvSellUnusedVoucherUserRepository uvSellUnusedVoucherUserRepository, UvSellUnusedVoucherUserMapper uvSellUnusedVoucherUserMapper) {
        this.uvSellUnusedVoucherUserRepository = uvSellUnusedVoucherUserRepository;
        this.uvSellUnusedVoucherUserMapper = uvSellUnusedVoucherUserMapper;
    }

    /**
     * Save a uvSellUnusedVoucherUser.
     *
     * @param uvSellUnusedVoucherUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UvSellUnusedVoucherUserDTO save(UvSellUnusedVoucherUserDTO uvSellUnusedVoucherUserDTO) {
        log.debug("Request to save UvSellUnusedVoucherUser : {}", uvSellUnusedVoucherUserDTO);
        UvSellUnusedVoucherUser uvSellUnusedVoucherUser = uvSellUnusedVoucherUserMapper.toEntity(uvSellUnusedVoucherUserDTO);
        uvSellUnusedVoucherUser = uvSellUnusedVoucherUserRepository.save(uvSellUnusedVoucherUser);
        return uvSellUnusedVoucherUserMapper.toDto(uvSellUnusedVoucherUser);
    }

    /**
     * Get all the uvSellUnusedVoucherUsers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UvSellUnusedVoucherUserDTO> findAll() {
        log.debug("Request to get all UvSellUnusedVoucherUsers");
        return uvSellUnusedVoucherUserRepository.findAll().stream()
            .map(uvSellUnusedVoucherUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one uvSellUnusedVoucherUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UvSellUnusedVoucherUserDTO findOne(Long id) {
        log.debug("Request to get UvSellUnusedVoucherUser : {}", id);
        UvSellUnusedVoucherUser uvSellUnusedVoucherUser = uvSellUnusedVoucherUserRepository.findOne(id);
        return uvSellUnusedVoucherUserMapper.toDto(uvSellUnusedVoucherUser);
    }

    /**
     * Delete the uvSellUnusedVoucherUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UvSellUnusedVoucherUser : {}", id);
        uvSellUnusedVoucherUserRepository.delete(id);
    }
}
