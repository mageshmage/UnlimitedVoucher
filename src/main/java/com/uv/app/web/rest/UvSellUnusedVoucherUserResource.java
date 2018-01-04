package com.uv.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.uv.app.service.UvSellUnusedVoucherUserService;
import com.uv.app.web.rest.errors.BadRequestAlertException;
import com.uv.app.web.rest.util.HeaderUtil;
import com.uv.app.service.dto.UvSellUnusedVoucherUserDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UvSellUnusedVoucherUser.
 */
@RestController
@RequestMapping("/api")
public class UvSellUnusedVoucherUserResource {

    private final Logger log = LoggerFactory.getLogger(UvSellUnusedVoucherUserResource.class);

    private static final String ENTITY_NAME = "uvSellUnusedVoucherUser";

    private final UvSellUnusedVoucherUserService uvSellUnusedVoucherUserService;

    public UvSellUnusedVoucherUserResource(UvSellUnusedVoucherUserService uvSellUnusedVoucherUserService) {
        this.uvSellUnusedVoucherUserService = uvSellUnusedVoucherUserService;
    }

    /**
     * POST  /uv-sell-unused-voucher-users : Create a new uvSellUnusedVoucherUser.
     *
     * @param uvSellUnusedVoucherUserDTO the uvSellUnusedVoucherUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uvSellUnusedVoucherUserDTO, or with status 400 (Bad Request) if the uvSellUnusedVoucherUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/uv-sell-unused-voucher-users")
    @Timed
    public ResponseEntity<UvSellUnusedVoucherUserDTO> createUvSellUnusedVoucherUser(@RequestBody UvSellUnusedVoucherUserDTO uvSellUnusedVoucherUserDTO) throws URISyntaxException {
        log.debug("REST request to save UvSellUnusedVoucherUser : {}", uvSellUnusedVoucherUserDTO);
        if (uvSellUnusedVoucherUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new uvSellUnusedVoucherUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UvSellUnusedVoucherUserDTO result = uvSellUnusedVoucherUserService.save(uvSellUnusedVoucherUserDTO);
        return ResponseEntity.created(new URI("/api/uv-sell-unused-voucher-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /uv-sell-unused-voucher-users : Updates an existing uvSellUnusedVoucherUser.
     *
     * @param uvSellUnusedVoucherUserDTO the uvSellUnusedVoucherUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uvSellUnusedVoucherUserDTO,
     * or with status 400 (Bad Request) if the uvSellUnusedVoucherUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the uvSellUnusedVoucherUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/uv-sell-unused-voucher-users")
    @Timed
    public ResponseEntity<UvSellUnusedVoucherUserDTO> updateUvSellUnusedVoucherUser(@RequestBody UvSellUnusedVoucherUserDTO uvSellUnusedVoucherUserDTO) throws URISyntaxException {
        log.debug("REST request to update UvSellUnusedVoucherUser : {}", uvSellUnusedVoucherUserDTO);
        if (uvSellUnusedVoucherUserDTO.getId() == null) {
            return createUvSellUnusedVoucherUser(uvSellUnusedVoucherUserDTO);
        }
        UvSellUnusedVoucherUserDTO result = uvSellUnusedVoucherUserService.save(uvSellUnusedVoucherUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, uvSellUnusedVoucherUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /uv-sell-unused-voucher-users : get all the uvSellUnusedVoucherUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of uvSellUnusedVoucherUsers in body
     */
    @GetMapping("/uv-sell-unused-voucher-users")
    @Timed
    public List<UvSellUnusedVoucherUserDTO> getAllUvSellUnusedVoucherUsers() {
        log.debug("REST request to get all UvSellUnusedVoucherUsers");
        return uvSellUnusedVoucherUserService.findAll();
        }

    /**
     * GET  /uv-sell-unused-voucher-users/:id : get the "id" uvSellUnusedVoucherUser.
     *
     * @param id the id of the uvSellUnusedVoucherUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uvSellUnusedVoucherUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/uv-sell-unused-voucher-users/{id}")
    @Timed
    public ResponseEntity<UvSellUnusedVoucherUserDTO> getUvSellUnusedVoucherUser(@PathVariable Long id) {
        log.debug("REST request to get UvSellUnusedVoucherUser : {}", id);
        UvSellUnusedVoucherUserDTO uvSellUnusedVoucherUserDTO = uvSellUnusedVoucherUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(uvSellUnusedVoucherUserDTO));
    }

    /**
     * DELETE  /uv-sell-unused-voucher-users/:id : delete the "id" uvSellUnusedVoucherUser.
     *
     * @param id the id of the uvSellUnusedVoucherUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/uv-sell-unused-voucher-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteUvSellUnusedVoucherUser(@PathVariable Long id) {
        log.debug("REST request to delete UvSellUnusedVoucherUser : {}", id);
        uvSellUnusedVoucherUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
