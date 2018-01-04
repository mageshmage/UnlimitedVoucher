package com.uv.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.uv.app.service.UvFreeVoucherService;
import com.uv.app.web.rest.errors.BadRequestAlertException;
import com.uv.app.web.rest.util.HeaderUtil;
import com.uv.app.web.rest.util.PaginationUtil;
import com.uv.app.service.dto.UvFreeVoucherDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UvFreeVoucher.
 */
@RestController
@RequestMapping("/api")
public class UvFreeVoucherResource {

    private final Logger log = LoggerFactory.getLogger(UvFreeVoucherResource.class);

    private static final String ENTITY_NAME = "uvFreeVoucher";

    private final UvFreeVoucherService uvFreeVoucherService;

    public UvFreeVoucherResource(UvFreeVoucherService uvFreeVoucherService) {
        this.uvFreeVoucherService = uvFreeVoucherService;
    }

    /**
     * POST  /uv-free-vouchers : Create a new uvFreeVoucher.
     *
     * @param uvFreeVoucherDTO the uvFreeVoucherDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uvFreeVoucherDTO, or with status 400 (Bad Request) if the uvFreeVoucher has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/uv-free-vouchers")
    @Timed
    public ResponseEntity<UvFreeVoucherDTO> createUvFreeVoucher(@RequestBody UvFreeVoucherDTO uvFreeVoucherDTO) throws URISyntaxException {
        log.debug("REST request to save UvFreeVoucher : {}", uvFreeVoucherDTO);
        if (uvFreeVoucherDTO.getId() != null) {
            throw new BadRequestAlertException("A new uvFreeVoucher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UvFreeVoucherDTO result = uvFreeVoucherService.save(uvFreeVoucherDTO);
        return ResponseEntity.created(new URI("/api/uv-free-vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /uv-free-vouchers : Updates an existing uvFreeVoucher.
     *
     * @param uvFreeVoucherDTO the uvFreeVoucherDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uvFreeVoucherDTO,
     * or with status 400 (Bad Request) if the uvFreeVoucherDTO is not valid,
     * or with status 500 (Internal Server Error) if the uvFreeVoucherDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/uv-free-vouchers")
    @Timed
    public ResponseEntity<UvFreeVoucherDTO> updateUvFreeVoucher(@RequestBody UvFreeVoucherDTO uvFreeVoucherDTO) throws URISyntaxException {
        log.debug("REST request to update UvFreeVoucher : {}", uvFreeVoucherDTO);
        if (uvFreeVoucherDTO.getId() == null) {
            return createUvFreeVoucher(uvFreeVoucherDTO);
        }
        UvFreeVoucherDTO result = uvFreeVoucherService.save(uvFreeVoucherDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, uvFreeVoucherDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /uv-free-vouchers : get all the uvFreeVouchers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of uvFreeVouchers in body
     */
    @GetMapping("/uv-free-vouchers")
    @Timed
    public ResponseEntity<List<UvFreeVoucherDTO>> getAllUvFreeVouchers(Pageable pageable) {
        log.debug("REST request to get a page of UvFreeVouchers");
        Page<UvFreeVoucherDTO> page = uvFreeVoucherService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/uv-free-vouchers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /uv-free-vouchers/:id : get the "id" uvFreeVoucher.
     *
     * @param id the id of the uvFreeVoucherDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uvFreeVoucherDTO, or with status 404 (Not Found)
     */
    @GetMapping("/uv-free-vouchers/{id}")
    @Timed
    public ResponseEntity<UvFreeVoucherDTO> getUvFreeVoucher(@PathVariable Long id) {
        log.debug("REST request to get UvFreeVoucher : {}", id);
        UvFreeVoucherDTO uvFreeVoucherDTO = uvFreeVoucherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(uvFreeVoucherDTO));
    }

    /**
     * DELETE  /uv-free-vouchers/:id : delete the "id" uvFreeVoucher.
     *
     * @param id the id of the uvFreeVoucherDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/uv-free-vouchers/{id}")
    @Timed
    public ResponseEntity<Void> deleteUvFreeVoucher(@PathVariable Long id) {
        log.debug("REST request to delete UvFreeVoucher : {}", id);
        uvFreeVoucherService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
