package com.uv.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.uv.app.service.UvCategoryService;
import com.uv.app.web.rest.errors.BadRequestAlertException;
import com.uv.app.web.rest.util.HeaderUtil;
import com.uv.app.service.dto.UvCategoryDTO;
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
 * REST controller for managing UvCategory.
 */
@RestController
@RequestMapping("/api")
public class UvCategoryResource {

    private final Logger log = LoggerFactory.getLogger(UvCategoryResource.class);

    private static final String ENTITY_NAME = "uvCategory";

    private final UvCategoryService uvCategoryService;

    public UvCategoryResource(UvCategoryService uvCategoryService) {
        this.uvCategoryService = uvCategoryService;
    }

    /**
     * POST  /uv-categories : Create a new uvCategory.
     *
     * @param uvCategoryDTO the uvCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uvCategoryDTO, or with status 400 (Bad Request) if the uvCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/uv-categories")
    @Timed
    public ResponseEntity<UvCategoryDTO> createUvCategory(@RequestBody UvCategoryDTO uvCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save UvCategory : {}", uvCategoryDTO);
        if (uvCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new uvCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UvCategoryDTO result = uvCategoryService.save(uvCategoryDTO);
        return ResponseEntity.created(new URI("/api/uv-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /uv-categories : Updates an existing uvCategory.
     *
     * @param uvCategoryDTO the uvCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uvCategoryDTO,
     * or with status 400 (Bad Request) if the uvCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the uvCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/uv-categories")
    @Timed
    public ResponseEntity<UvCategoryDTO> updateUvCategory(@RequestBody UvCategoryDTO uvCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update UvCategory : {}", uvCategoryDTO);
        if (uvCategoryDTO.getId() == null) {
            return createUvCategory(uvCategoryDTO);
        }
        UvCategoryDTO result = uvCategoryService.save(uvCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, uvCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /uv-categories : get all the uvCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of uvCategories in body
     */
    @GetMapping("/uv-categories")
    @Timed
    public List<UvCategoryDTO> getAllUvCategories() {
        log.debug("REST request to get all UvCategories");
        return uvCategoryService.findAll();
        }

    /**
     * GET  /uv-categories/:id : get the "id" uvCategory.
     *
     * @param id the id of the uvCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uvCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/uv-categories/{id}")
    @Timed
    public ResponseEntity<UvCategoryDTO> getUvCategory(@PathVariable Long id) {
        log.debug("REST request to get UvCategory : {}", id);
        UvCategoryDTO uvCategoryDTO = uvCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(uvCategoryDTO));
    }

    /**
     * DELETE  /uv-categories/:id : delete the "id" uvCategory.
     *
     * @param id the id of the uvCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/uv-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteUvCategory(@PathVariable Long id) {
        log.debug("REST request to delete UvCategory : {}", id);
        uvCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
