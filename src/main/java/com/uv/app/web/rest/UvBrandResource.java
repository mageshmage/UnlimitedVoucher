package com.uv.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.uv.app.service.UvBrandService;
import com.uv.app.web.rest.errors.BadRequestAlertException;
import com.uv.app.web.rest.util.HeaderUtil;
import com.uv.app.service.dto.UvBrandDTO;
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
 * REST controller for managing UvBrand.
 */
@RestController
@RequestMapping("/api")
public class UvBrandResource {

    private final Logger log = LoggerFactory.getLogger(UvBrandResource.class);

    private static final String ENTITY_NAME = "uvBrand";

    private final UvBrandService uvBrandService;

    public UvBrandResource(UvBrandService uvBrandService) {
        this.uvBrandService = uvBrandService;
    }

    /**
     * POST  /uv-brands : Create a new uvBrand.
     *
     * @param uvBrandDTO the uvBrandDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uvBrandDTO, or with status 400 (Bad Request) if the uvBrand has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/uv-brands")
    @Timed
    public ResponseEntity<UvBrandDTO> createUvBrand(@RequestBody UvBrandDTO uvBrandDTO) throws URISyntaxException {
        log.debug("REST request to save UvBrand : {}", uvBrandDTO);
        if (uvBrandDTO.getId() != null) {
            throw new BadRequestAlertException("A new uvBrand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UvBrandDTO result = uvBrandService.save(uvBrandDTO);
        return ResponseEntity.created(new URI("/api/uv-brands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /uv-brands : Updates an existing uvBrand.
     *
     * @param uvBrandDTO the uvBrandDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uvBrandDTO,
     * or with status 400 (Bad Request) if the uvBrandDTO is not valid,
     * or with status 500 (Internal Server Error) if the uvBrandDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/uv-brands")
    @Timed
    public ResponseEntity<UvBrandDTO> updateUvBrand(@RequestBody UvBrandDTO uvBrandDTO) throws URISyntaxException {
        log.debug("REST request to update UvBrand : {}", uvBrandDTO);
        if (uvBrandDTO.getId() == null) {
            return createUvBrand(uvBrandDTO);
        }
        UvBrandDTO result = uvBrandService.save(uvBrandDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, uvBrandDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /uv-brands : get all the uvBrands.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of uvBrands in body
     */
    @GetMapping("/uv-brands")
    @Timed
    public List<UvBrandDTO> getAllUvBrands() {
        log.debug("REST request to get all UvBrands");
        return uvBrandService.findAll();
        }

    /**
     * GET  /uv-brands/:id : get the "id" uvBrand.
     *
     * @param id the id of the uvBrandDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uvBrandDTO, or with status 404 (Not Found)
     */
    @GetMapping("/uv-brands/{id}")
    @Timed
    public ResponseEntity<UvBrandDTO> getUvBrand(@PathVariable Long id) {
        log.debug("REST request to get UvBrand : {}", id);
        UvBrandDTO uvBrandDTO = uvBrandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(uvBrandDTO));
    }

    /**
     * DELETE  /uv-brands/:id : delete the "id" uvBrand.
     *
     * @param id the id of the uvBrandDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/uv-brands/{id}")
    @Timed
    public ResponseEntity<Void> deleteUvBrand(@PathVariable Long id) {
        log.debug("REST request to delete UvBrand : {}", id);
        uvBrandService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
