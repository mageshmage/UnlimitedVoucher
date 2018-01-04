package com.uv.app.web.rest;

import com.uv.app.UnlimitedVoucherApp;

import com.uv.app.domain.UvCategory;
import com.uv.app.repository.UvCategoryRepository;
import com.uv.app.service.UvCategoryService;
import com.uv.app.service.dto.UvCategoryDTO;
import com.uv.app.service.mapper.UvCategoryMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.uv.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UvCategoryResource REST controller.
 *
 * @see UvCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnlimitedVoucherApp.class)
public class UvCategoryResourceIntTest {

    private static final String DEFAULT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private UvCategoryRepository uvCategoryRepository;

    @Autowired
    private UvCategoryMapper uvCategoryMapper;

    @Autowired
    private UvCategoryService uvCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUvCategoryMockMvc;

    private UvCategory uvCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UvCategoryResource uvCategoryResource = new UvCategoryResource(uvCategoryService);
        this.restUvCategoryMockMvc = MockMvcBuilders.standaloneSetup(uvCategoryResource)
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
    public static UvCategory createEntity(EntityManager em) {
        UvCategory uvCategory = new UvCategory()
            .categoryName(DEFAULT_CATEGORY_NAME)
            .categoryCode(DEFAULT_CATEGORY_CODE)
            .isEnabled(DEFAULT_IS_ENABLED)
            .createdOn(DEFAULT_CREATED_ON)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON);
        return uvCategory;
    }

    @Before
    public void initTest() {
        uvCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createUvCategory() throws Exception {
        int databaseSizeBeforeCreate = uvCategoryRepository.findAll().size();

        // Create the UvCategory
        UvCategoryDTO uvCategoryDTO = uvCategoryMapper.toDto(uvCategory);
        restUvCategoryMockMvc.perform(post("/api/uv-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the UvCategory in the database
        List<UvCategory> uvCategoryList = uvCategoryRepository.findAll();
        assertThat(uvCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        UvCategory testUvCategory = uvCategoryList.get(uvCategoryList.size() - 1);
        assertThat(testUvCategory.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testUvCategory.getCategoryCode()).isEqualTo(DEFAULT_CATEGORY_CODE);
        assertThat(testUvCategory.isIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
        assertThat(testUvCategory.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testUvCategory.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void createUvCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uvCategoryRepository.findAll().size();

        // Create the UvCategory with an existing ID
        uvCategory.setId(1L);
        UvCategoryDTO uvCategoryDTO = uvCategoryMapper.toDto(uvCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUvCategoryMockMvc.perform(post("/api/uv-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UvCategory in the database
        List<UvCategory> uvCategoryList = uvCategoryRepository.findAll();
        assertThat(uvCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUvCategories() throws Exception {
        // Initialize the database
        uvCategoryRepository.saveAndFlush(uvCategory);

        // Get all the uvCategoryList
        restUvCategoryMockMvc.perform(get("/api/uv-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uvCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME.toString())))
            .andExpect(jsonPath("$.[*].categoryCode").value(hasItem(DEFAULT_CATEGORY_CODE.toString())))
            .andExpect(jsonPath("$.[*].isEnabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(DEFAULT_LAST_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getUvCategory() throws Exception {
        // Initialize the database
        uvCategoryRepository.saveAndFlush(uvCategory);

        // Get the uvCategory
        restUvCategoryMockMvc.perform(get("/api/uv-categories/{id}", uvCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uvCategory.getId().intValue()))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME.toString()))
            .andExpect(jsonPath("$.categoryCode").value(DEFAULT_CATEGORY_CODE.toString()))
            .andExpect(jsonPath("$.isEnabled").value(DEFAULT_IS_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.lastUpdatedOn").value(DEFAULT_LAST_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUvCategory() throws Exception {
        // Get the uvCategory
        restUvCategoryMockMvc.perform(get("/api/uv-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUvCategory() throws Exception {
        // Initialize the database
        uvCategoryRepository.saveAndFlush(uvCategory);
        int databaseSizeBeforeUpdate = uvCategoryRepository.findAll().size();

        // Update the uvCategory
        UvCategory updatedUvCategory = uvCategoryRepository.findOne(uvCategory.getId());
        // Disconnect from session so that the updates on updatedUvCategory are not directly saved in db
        em.detach(updatedUvCategory);
        updatedUvCategory
            .categoryName(UPDATED_CATEGORY_NAME)
            .categoryCode(UPDATED_CATEGORY_CODE)
            .isEnabled(UPDATED_IS_ENABLED)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);
        UvCategoryDTO uvCategoryDTO = uvCategoryMapper.toDto(updatedUvCategory);

        restUvCategoryMockMvc.perform(put("/api/uv-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the UvCategory in the database
        List<UvCategory> uvCategoryList = uvCategoryRepository.findAll();
        assertThat(uvCategoryList).hasSize(databaseSizeBeforeUpdate);
        UvCategory testUvCategory = uvCategoryList.get(uvCategoryList.size() - 1);
        assertThat(testUvCategory.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testUvCategory.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
        assertThat(testUvCategory.isIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
        assertThat(testUvCategory.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUvCategory.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingUvCategory() throws Exception {
        int databaseSizeBeforeUpdate = uvCategoryRepository.findAll().size();

        // Create the UvCategory
        UvCategoryDTO uvCategoryDTO = uvCategoryMapper.toDto(uvCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUvCategoryMockMvc.perform(put("/api/uv-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the UvCategory in the database
        List<UvCategory> uvCategoryList = uvCategoryRepository.findAll();
        assertThat(uvCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUvCategory() throws Exception {
        // Initialize the database
        uvCategoryRepository.saveAndFlush(uvCategory);
        int databaseSizeBeforeDelete = uvCategoryRepository.findAll().size();

        // Get the uvCategory
        restUvCategoryMockMvc.perform(delete("/api/uv-categories/{id}", uvCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UvCategory> uvCategoryList = uvCategoryRepository.findAll();
        assertThat(uvCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UvCategory.class);
        UvCategory uvCategory1 = new UvCategory();
        uvCategory1.setId(1L);
        UvCategory uvCategory2 = new UvCategory();
        uvCategory2.setId(uvCategory1.getId());
        assertThat(uvCategory1).isEqualTo(uvCategory2);
        uvCategory2.setId(2L);
        assertThat(uvCategory1).isNotEqualTo(uvCategory2);
        uvCategory1.setId(null);
        assertThat(uvCategory1).isNotEqualTo(uvCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UvCategoryDTO.class);
        UvCategoryDTO uvCategoryDTO1 = new UvCategoryDTO();
        uvCategoryDTO1.setId(1L);
        UvCategoryDTO uvCategoryDTO2 = new UvCategoryDTO();
        assertThat(uvCategoryDTO1).isNotEqualTo(uvCategoryDTO2);
        uvCategoryDTO2.setId(uvCategoryDTO1.getId());
        assertThat(uvCategoryDTO1).isEqualTo(uvCategoryDTO2);
        uvCategoryDTO2.setId(2L);
        assertThat(uvCategoryDTO1).isNotEqualTo(uvCategoryDTO2);
        uvCategoryDTO1.setId(null);
        assertThat(uvCategoryDTO1).isNotEqualTo(uvCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(uvCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(uvCategoryMapper.fromId(null)).isNull();
    }
}
