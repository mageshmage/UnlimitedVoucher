package com.uv.app.web.rest;

import com.uv.app.UnlimitedVoucherApp;

import com.uv.app.domain.UvBrand;
import com.uv.app.repository.UvBrandRepository;
import com.uv.app.service.UvBrandService;
import com.uv.app.service.dto.UvBrandDTO;
import com.uv.app.service.mapper.UvBrandMapper;
import com.uv.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.uv.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UvBrandResource REST controller.
 *
 * @see UvBrandResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnlimitedVoucherApp.class)
public class UvBrandResourceIntTest {

    private static final String DEFAULT_BRAND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRAND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BRAND_CODE = "BBBBBBBBBB";

    @Autowired
    private UvBrandRepository uvBrandRepository;

    @Autowired
    private UvBrandMapper uvBrandMapper;

    @Autowired
    private UvBrandService uvBrandService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUvBrandMockMvc;

    private UvBrand uvBrand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UvBrandResource uvBrandResource = new UvBrandResource(uvBrandService);
        this.restUvBrandMockMvc = MockMvcBuilders.standaloneSetup(uvBrandResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UvBrand createEntity(EntityManager em) {
        UvBrand uvBrand = new UvBrand()
            .brandName(DEFAULT_BRAND_NAME)
            .brandCode(DEFAULT_BRAND_CODE);
        return uvBrand;
    }

    @Before
    public void initTest() {
        uvBrand = createEntity(em);
    }

    @Test
    @Transactional
    public void createUvBrand() throws Exception {
        int databaseSizeBeforeCreate = uvBrandRepository.findAll().size();

        // Create the UvBrand
        UvBrandDTO uvBrandDTO = uvBrandMapper.toDto(uvBrand);
        restUvBrandMockMvc.perform(post("/api/uv-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvBrandDTO)))
            .andExpect(status().isCreated());

        // Validate the UvBrand in the database
        List<UvBrand> uvBrandList = uvBrandRepository.findAll();
        assertThat(uvBrandList).hasSize(databaseSizeBeforeCreate + 1);
        UvBrand testUvBrand = uvBrandList.get(uvBrandList.size() - 1);
        assertThat(testUvBrand.getBrandName()).isEqualTo(DEFAULT_BRAND_NAME);
        assertThat(testUvBrand.getBrandCode()).isEqualTo(DEFAULT_BRAND_CODE);
    }

    @Test
    @Transactional
    public void createUvBrandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uvBrandRepository.findAll().size();

        // Create the UvBrand with an existing ID
        uvBrand.setId(1L);
        UvBrandDTO uvBrandDTO = uvBrandMapper.toDto(uvBrand);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUvBrandMockMvc.perform(post("/api/uv-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvBrandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UvBrand in the database
        List<UvBrand> uvBrandList = uvBrandRepository.findAll();
        assertThat(uvBrandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUvBrands() throws Exception {
        // Initialize the database
        uvBrandRepository.saveAndFlush(uvBrand);

        // Get all the uvBrandList
        restUvBrandMockMvc.perform(get("/api/uv-brands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uvBrand.getId().intValue())))
            .andExpect(jsonPath("$.[*].brandName").value(hasItem(DEFAULT_BRAND_NAME.toString())))
            .andExpect(jsonPath("$.[*].brandCode").value(hasItem(DEFAULT_BRAND_CODE.toString())));
    }

    @Test
    @Transactional
    public void getUvBrand() throws Exception {
        // Initialize the database
        uvBrandRepository.saveAndFlush(uvBrand);

        // Get the uvBrand
        restUvBrandMockMvc.perform(get("/api/uv-brands/{id}", uvBrand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uvBrand.getId().intValue()))
            .andExpect(jsonPath("$.brandName").value(DEFAULT_BRAND_NAME.toString()))
            .andExpect(jsonPath("$.brandCode").value(DEFAULT_BRAND_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUvBrand() throws Exception {
        // Get the uvBrand
        restUvBrandMockMvc.perform(get("/api/uv-brands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUvBrand() throws Exception {
        // Initialize the database
        uvBrandRepository.saveAndFlush(uvBrand);
        int databaseSizeBeforeUpdate = uvBrandRepository.findAll().size();

        // Update the uvBrand
        UvBrand updatedUvBrand = uvBrandRepository.findOne(uvBrand.getId());
        // Disconnect from session so that the updates on updatedUvBrand are not directly saved in db
        em.detach(updatedUvBrand);
        updatedUvBrand
            .brandName(UPDATED_BRAND_NAME)
            .brandCode(UPDATED_BRAND_CODE);
        UvBrandDTO uvBrandDTO = uvBrandMapper.toDto(updatedUvBrand);

        restUvBrandMockMvc.perform(put("/api/uv-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvBrandDTO)))
            .andExpect(status().isOk());

        // Validate the UvBrand in the database
        List<UvBrand> uvBrandList = uvBrandRepository.findAll();
        assertThat(uvBrandList).hasSize(databaseSizeBeforeUpdate);
        UvBrand testUvBrand = uvBrandList.get(uvBrandList.size() - 1);
        assertThat(testUvBrand.getBrandName()).isEqualTo(UPDATED_BRAND_NAME);
        assertThat(testUvBrand.getBrandCode()).isEqualTo(UPDATED_BRAND_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingUvBrand() throws Exception {
        int databaseSizeBeforeUpdate = uvBrandRepository.findAll().size();

        // Create the UvBrand
        UvBrandDTO uvBrandDTO = uvBrandMapper.toDto(uvBrand);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUvBrandMockMvc.perform(put("/api/uv-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvBrandDTO)))
            .andExpect(status().isCreated());

        // Validate the UvBrand in the database
        List<UvBrand> uvBrandList = uvBrandRepository.findAll();
        assertThat(uvBrandList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUvBrand() throws Exception {
        // Initialize the database
        uvBrandRepository.saveAndFlush(uvBrand);
        int databaseSizeBeforeDelete = uvBrandRepository.findAll().size();

        // Get the uvBrand
        restUvBrandMockMvc.perform(delete("/api/uv-brands/{id}", uvBrand.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UvBrand> uvBrandList = uvBrandRepository.findAll();
        assertThat(uvBrandList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UvBrand.class);
        UvBrand uvBrand1 = new UvBrand();
        uvBrand1.setId(1L);
        UvBrand uvBrand2 = new UvBrand();
        uvBrand2.setId(uvBrand1.getId());
        assertThat(uvBrand1).isEqualTo(uvBrand2);
        uvBrand2.setId(2L);
        assertThat(uvBrand1).isNotEqualTo(uvBrand2);
        uvBrand1.setId(null);
        assertThat(uvBrand1).isNotEqualTo(uvBrand2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UvBrandDTO.class);
        UvBrandDTO uvBrandDTO1 = new UvBrandDTO();
        uvBrandDTO1.setId(1L);
        UvBrandDTO uvBrandDTO2 = new UvBrandDTO();
        assertThat(uvBrandDTO1).isNotEqualTo(uvBrandDTO2);
        uvBrandDTO2.setId(uvBrandDTO1.getId());
        assertThat(uvBrandDTO1).isEqualTo(uvBrandDTO2);
        uvBrandDTO2.setId(2L);
        assertThat(uvBrandDTO1).isNotEqualTo(uvBrandDTO2);
        uvBrandDTO1.setId(null);
        assertThat(uvBrandDTO1).isNotEqualTo(uvBrandDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(uvBrandMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(uvBrandMapper.fromId(null)).isNull();
    }
}
