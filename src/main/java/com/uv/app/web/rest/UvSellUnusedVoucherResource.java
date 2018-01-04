package com.uv.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.uv.app.service.UvSellUnusedVoucherService;
import com.uv.app.web.rest.errors.BadRequestAlertException;
import com.uv.app.web.rest.util.HeaderUtil;
import com.uv.app.web.rest.util.PaginationUtil;
import com.uv.app.service.dto.UvSellUnusedVoucherDTO;
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
 * REST controller for managing UvSellUnusedVoucher.
 */
@RestController
@RequestMapping("/api")
public class UvSellUnusedVoucherResource {

    private final Logger log = LoggerFactory.getLogger(UvSellUnusedVoucherResource.class);

    private static final String ENTITY_NAME = "uvSellUnusedVoucher";

    private final UvSellUnusedVoucherService uvSellUnusedVoucherService;

    public UvSellUnusedVoucherResource(UvSellUnusedVoucherService uvSellUnusedVoucherService) {
        this.uvSellUnusedVoucherService = uvSellUnusedVoucherService;
    }

    /**
     * POST  /uv-sell-unused-vouchers : Create a new uvSellUnusedVoucher.
     *
     * @param uvSellUnusedVoucherDTO the uvSellUnusedVoucherDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uvSellUnusedVoucherDTO, or with status 400 (Bad Request) if the uvSellUnusedVoucher has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/uv-sell-unused-vouchers")
    @Timed
    public ResponseEntity<UvSellUnusedVoucherDTO> createUvSellUnusedVoucher(@RequestBody UvSellUnusedVoucherDTO uvSellUnusedVoucherDTO) throws URISyntaxException {
        log.debug("REST request to save UvSellUnusedVoucher : {}", uvSellUnusedVoucherDTO);
        if (uvSellUnusedVoucherDTO.getId() != null) {
            throw new BadRequestAlertException("A new uvSellUnusedVoucher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UvSellUnusedVoucherDTO result = uvSellUnusedVoucherService.save(uvSellUnusedVoucherDTO);
        return ResponseEntity.created(new URI("/api/uv-sell-unused-vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /uv-sell-unused-vouchers : Updates an existing uvSellUnusedVoucher.
     *
     * @param uvSellUnusedVoucherDTO the uvSellUnusedVoucherDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uvSellUnusedVoucherDTO,
     * or with status 400 (Bad Request) if the uvSellUnusedVoucherDTO is not valid,
     * or with status 500 (Internal Server Error) if the uvSellUnusedVoucherDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/uv-sell-unused-vouchers")
    @Timed
    public ResponseEntity<UvSellUnusedVoucherDTO> updateUvSellUnusedVoucher(@RequestBody UvSellUnusedVoucherDTO uvSellUnusedVoucherDTO) throws URISyntaxException {
        log.debug("REST request to update UvSellUnusedVoucher : {}", uvSellUnusedVoucherDTO);
        if (uvSellUnusedVoucherDTO.getId() == null) {
            return createUvSellUnusedVoucher(uvSellUnusedVoucherDTO);
        }
        UvSellUnusedVoucherDTO result = uvSellUnusedVoucherService.save(uvSellUnusedVoucherDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, uvSellUnusedVoucherDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /uv-sell-unused-vouchers : get all the uvSellUnusedVouchers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of uvSellUnusedVouchers in body
     */
    @GetMapping("/uv-sell-unused-vouchers")
    @Timed
    public ResponseEntity<List<UvSellUnusedVoucherDTO>> getAllUvSellUnusedVouchers(Pageable pageable) {
        log.debug("REST request to get a page of UvSellUnusedVouchers");
        Page<UvSellUnusedVoucherDTO> page = uvSellUnusedVoucherService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/uv-sell-unused-vouchers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /uv-sell-unused-vouchers/:id : get the "id" uvSellUnusedVoucher.
     *
     * @param id the id of the uvSellUnusedVoucherDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uvSellUnusedVoucherDTO, or with status 404 (Not Found)
     */
    @GetMapping("/uv-sell-unused-vouchers/{id}")
    @Timed
    public ResponseEntity<UvSellUnusedVoucherDTO> getUvSellUnusedVoucher(@PathVariable Long id) {
        log.debug("REST request to get UvSellUnusedVoucher : {}", id);
        UvSellUnusedVoucherDTO uvSellUnusedVoucherDTO = uvSellUnusedVoucherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(uvSellUnusedVoucherDTO));
    }

    /**
     * DELETE  /uv-sell-unused-vouchers/:id : delete the "id" uvSellUnusedVoucher.
     *
     * @param id the id of the uvSellUnusedVoucherDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/uv-sell-unused-vouchers/{id}")
    @Timed
    public ResponseEntity<Void> deleteUvSellUnusedVoucher(@PathVariable Long id) {
        log.debug("REST request to delete UvSellUnusedVoucher : {}", id);
        uvSellUnusedVoucherService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
