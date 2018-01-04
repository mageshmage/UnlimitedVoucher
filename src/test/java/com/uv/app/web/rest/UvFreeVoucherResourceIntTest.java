package com.uv.app.web.rest;

import com.uv.app.UnlimitedVoucherApp;

import com.uv.app.domain.UvFreeVoucher;
import com.uv.app.repository.UvFreeVoucherRepository;
import com.uv.app.service.UvFreeVoucherService;
import com.uv.app.service.dto.UvFreeVoucherDTO;
import com.uv.app.service.mapper.UvFreeVoucherMapper;
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
 * Test class for the UvFreeVoucherResource REST controller.
 *
 * @see UvFreeVoucherResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnlimitedVoucherApp.class)
public class UvFreeVoucherResourceIntTest {

    private static final String DEFAULT_VOUCHER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VOUCHER_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VALID = false;
    private static final Boolean UPDATED_IS_VALID = true;

    private static final Boolean DEFAULT_IS_EXPIRED = false;
    private static final Boolean UPDATED_IS_EXPIRED = true;

    private static final Boolean DEFAULT_CREATED_BY = false;
    private static final Boolean UPDATED_CREATED_BY = true;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private UvFreeVoucherRepository uvFreeVoucherRepository;

    @Autowired
    private UvFreeVoucherMapper uvFreeVoucherMapper;

    @Autowired
    private UvFreeVoucherService uvFreeVoucherService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUvFreeVoucherMockMvc;

    private UvFreeVoucher uvFreeVoucher;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UvFreeVoucherResource uvFreeVoucherResource = new UvFreeVoucherResource(uvFreeVoucherService);
        this.restUvFreeVoucherMockMvc = MockMvcBuilders.standaloneSetup(uvFreeVoucherResource)
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
    public static UvFreeVoucher createEntity(EntityManager em) {
        UvFreeVoucher uvFreeVoucher = new UvFreeVoucher()
            .voucherCode(DEFAULT_VOUCHER_CODE)
            .isValid(DEFAULT_IS_VALID)
            .isExpired(DEFAULT_IS_EXPIRED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON);
        return uvFreeVoucher;
    }

    @Before
    public void initTest() {
        uvFreeVoucher = createEntity(em);
    }

    @Test
    @Transactional
    public void createUvFreeVoucher() throws Exception {
        int databaseSizeBeforeCreate = uvFreeVoucherRepository.findAll().size();

        // Create the UvFreeVoucher
        UvFreeVoucherDTO uvFreeVoucherDTO = uvFreeVoucherMapper.toDto(uvFreeVoucher);
        restUvFreeVoucherMockMvc.perform(post("/api/uv-free-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvFreeVoucherDTO)))
            .andExpect(status().isCreated());

        // Validate the UvFreeVoucher in the database
        List<UvFreeVoucher> uvFreeVoucherList = uvFreeVoucherRepository.findAll();
        assertThat(uvFreeVoucherList).hasSize(databaseSizeBeforeCreate + 1);
        UvFreeVoucher testUvFreeVoucher = uvFreeVoucherList.get(uvFreeVoucherList.size() - 1);
        assertThat(testUvFreeVoucher.getVoucherCode()).isEqualTo(DEFAULT_VOUCHER_CODE);
        assertThat(testUvFreeVoucher.isIsValid()).isEqualTo(DEFAULT_IS_VALID);
        assertThat(testUvFreeVoucher.isIsExpired()).isEqualTo(DEFAULT_IS_EXPIRED);
        assertThat(testUvFreeVoucher.isCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testUvFreeVoucher.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testUvFreeVoucher.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void createUvFreeVoucherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uvFreeVoucherRepository.findAll().size();

        // Create the UvFreeVoucher with an existing ID
        uvFreeVoucher.setId(1L);
        UvFreeVoucherDTO uvFreeVoucherDTO = uvFreeVoucherMapper.toDto(uvFreeVoucher);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUvFreeVoucherMockMvc.perform(post("/api/uv-free-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvFreeVoucherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UvFreeVoucher in the database
        List<UvFreeVoucher> uvFreeVoucherList = uvFreeVoucherRepository.findAll();
        assertThat(uvFreeVoucherList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUvFreeVouchers() throws Exception {
        // Initialize the database
        uvFreeVoucherRepository.saveAndFlush(uvFreeVoucher);

        // Get all the uvFreeVoucherList
        restUvFreeVoucherMockMvc.perform(get("/api/uv-free-vouchers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uvFreeVoucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherCode").value(hasItem(DEFAULT_VOUCHER_CODE.toString())))
            .andExpect(jsonPath("$.[*].isValid").value(hasItem(DEFAULT_IS_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].isExpired").value(hasItem(DEFAULT_IS_EXPIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.booleanValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(DEFAULT_LAST_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getUvFreeVoucher() throws Exception {
        // Initialize the database
        uvFreeVoucherRepository.saveAndFlush(uvFreeVoucher);

        // Get the uvFreeVoucher
        restUvFreeVoucherMockMvc.perform(get("/api/uv-free-vouchers/{id}", uvFreeVoucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uvFreeVoucher.getId().intValue()))
            .andExpect(jsonPath("$.voucherCode").value(DEFAULT_VOUCHER_CODE.toString()))
            .andExpect(jsonPath("$.isValid").value(DEFAULT_IS_VALID.booleanValue()))
            .andExpect(jsonPath("$.isExpired").value(DEFAULT_IS_EXPIRED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.booleanValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.lastUpdatedOn").value(DEFAULT_LAST_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUvFreeVoucher() throws Exception {
        // Get the uvFreeVoucher
        restUvFreeVoucherMockMvc.perform(get("/api/uv-free-vouchers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUvFreeVoucher() throws Exception {
        // Initialize the database
        uvFreeVoucherRepository.saveAndFlush(uvFreeVoucher);
        int databaseSizeBeforeUpdate = uvFreeVoucherRepository.findAll().size();

        // Update the uvFreeVoucher
        UvFreeVoucher updatedUvFreeVoucher = uvFreeVoucherRepository.findOne(uvFreeVoucher.getId());
        // Disconnect from session so that the updates on updatedUvFreeVoucher are not directly saved in db
        em.detach(updatedUvFreeVoucher);
        updatedUvFreeVoucher
            .voucherCode(UPDATED_VOUCHER_CODE)
            .isValid(UPDATED_IS_VALID)
            .isExpired(UPDATED_IS_EXPIRED)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);
        UvFreeVoucherDTO uvFreeVoucherDTO = uvFreeVoucherMapper.toDto(updatedUvFreeVoucher);

        restUvFreeVoucherMockMvc.perform(put("/api/uv-free-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvFreeVoucherDTO)))
            .andExpect(status().isOk());

        // Validate the UvFreeVoucher in the database
        List<UvFreeVoucher> uvFreeVoucherList = uvFreeVoucherRepository.findAll();
        assertThat(uvFreeVoucherList).hasSize(databaseSizeBeforeUpdate);
        UvFreeVoucher testUvFreeVoucher = uvFreeVoucherList.get(uvFreeVoucherList.size() - 1);
        assertThat(testUvFreeVoucher.getVoucherCode()).isEqualTo(UPDATED_VOUCHER_CODE);
        assertThat(testUvFreeVoucher.isIsValid()).isEqualTo(UPDATED_IS_VALID);
        assertThat(testUvFreeVoucher.isIsExpired()).isEqualTo(UPDATED_IS_EXPIRED);
        assertThat(testUvFreeVoucher.isCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUvFreeVoucher.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUvFreeVoucher.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingUvFreeVoucher() throws Exception {
        int databaseSizeBeforeUpdate = uvFreeVoucherRepository.findAll().size();

        // Create the UvFreeVoucher
        UvFreeVoucherDTO uvFreeVoucherDTO = uvFreeVoucherMapper.toDto(uvFreeVoucher);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUvFreeVoucherMockMvc.perform(put("/api/uv-free-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvFreeVoucherDTO)))
            .andExpect(status().isCreated());

        // Validate the UvFreeVoucher in the database
        List<UvFreeVoucher> uvFreeVoucherList = uvFreeVoucherRepository.findAll();
        assertThat(uvFreeVoucherList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUvFreeVoucher() throws Exception {
        // Initialize the database
        uvFreeVoucherRepository.saveAndFlush(uvFreeVoucher);
        int databaseSizeBeforeDelete = uvFreeVoucherRepository.findAll().size();

        // Get the uvFreeVoucher
        restUvFreeVoucherMockMvc.perform(delete("/api/uv-free-vouchers/{id}", uvFreeVoucher.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UvFreeVoucher> uvFreeVoucherList = uvFreeVoucherRepository.findAll();
        assertThat(uvFreeVoucherList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UvFreeVoucher.class);
        UvFreeVoucher uvFreeVoucher1 = new UvFreeVoucher();
        uvFreeVoucher1.setId(1L);
        UvFreeVoucher uvFreeVoucher2 = new UvFreeVoucher();
        uvFreeVoucher2.setId(uvFreeVoucher1.getId());
        assertThat(uvFreeVoucher1).isEqualTo(uvFreeVoucher2);
        uvFreeVoucher2.setId(2L);
        assertThat(uvFreeVoucher1).isNotEqualTo(uvFreeVoucher2);
        uvFreeVoucher1.setId(null);
        assertThat(uvFreeVoucher1).isNotEqualTo(uvFreeVoucher2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UvFreeVoucherDTO.class);
        UvFreeVoucherDTO uvFreeVoucherDTO1 = new UvFreeVoucherDTO();
        uvFreeVoucherDTO1.setId(1L);
        UvFreeVoucherDTO uvFreeVoucherDTO2 = new UvFreeVoucherDTO();
        assertThat(uvFreeVoucherDTO1).isNotEqualTo(uvFreeVoucherDTO2);
        uvFreeVoucherDTO2.setId(uvFreeVoucherDTO1.getId());
        assertThat(uvFreeVoucherDTO1).isEqualTo(uvFreeVoucherDTO2);
        uvFreeVoucherDTO2.setId(2L);
        assertThat(uvFreeVoucherDTO1).isNotEqualTo(uvFreeVoucherDTO2);
        uvFreeVoucherDTO1.setId(null);
        assertThat(uvFreeVoucherDTO1).isNotEqualTo(uvFreeVoucherDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(uvFreeVoucherMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(uvFreeVoucherMapper.fromId(null)).isNull();
    }
}
