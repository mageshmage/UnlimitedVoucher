package com.uv.app.web.rest;

import com.uv.app.UnlimitedVoucherApp;

import com.uv.app.domain.UvSellUnusedVoucher;
import com.uv.app.repository.UvSellUnusedVoucherRepository;
import com.uv.app.service.UvSellUnusedVoucherService;
import com.uv.app.service.dto.UvSellUnusedVoucherDTO;
import com.uv.app.service.mapper.UvSellUnusedVoucherMapper;
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
 * Test class for the UvSellUnusedVoucherResource REST controller.
 *
 * @see UvSellUnusedVoucherResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnlimitedVoucherApp.class)
public class UvSellUnusedVoucherResourceIntTest {

    private static final String DEFAULT_VOUCHER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VOUCHER_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VALID = false;
    private static final Boolean UPDATED_IS_VALID = true;

    private static final Boolean DEFAULT_IS_EXPIRED = false;
    private static final Boolean UPDATED_IS_EXPIRED = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private UvSellUnusedVoucherRepository uvSellUnusedVoucherRepository;

    @Autowired
    private UvSellUnusedVoucherMapper uvSellUnusedVoucherMapper;

    @Autowired
    private UvSellUnusedVoucherService uvSellUnusedVoucherService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUvSellUnusedVoucherMockMvc;

    private UvSellUnusedVoucher uvSellUnusedVoucher;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UvSellUnusedVoucherResource uvSellUnusedVoucherResource = new UvSellUnusedVoucherResource(uvSellUnusedVoucherService);
        this.restUvSellUnusedVoucherMockMvc = MockMvcBuilders.standaloneSetup(uvSellUnusedVoucherResource)
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
    public static UvSellUnusedVoucher createEntity(EntityManager em) {
        UvSellUnusedVoucher uvSellUnusedVoucher = new UvSellUnusedVoucher()
            .voucherCode(DEFAULT_VOUCHER_CODE)
            .isValid(DEFAULT_IS_VALID)
            .isExpired(DEFAULT_IS_EXPIRED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON);
        return uvSellUnusedVoucher;
    }

    @Before
    public void initTest() {
        uvSellUnusedVoucher = createEntity(em);
    }

    @Test
    @Transactional
    public void createUvSellUnusedVoucher() throws Exception {
        int databaseSizeBeforeCreate = uvSellUnusedVoucherRepository.findAll().size();

        // Create the UvSellUnusedVoucher
        UvSellUnusedVoucherDTO uvSellUnusedVoucherDTO = uvSellUnusedVoucherMapper.toDto(uvSellUnusedVoucher);
        restUvSellUnusedVoucherMockMvc.perform(post("/api/uv-sell-unused-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvSellUnusedVoucherDTO)))
            .andExpect(status().isCreated());

        // Validate the UvSellUnusedVoucher in the database
        List<UvSellUnusedVoucher> uvSellUnusedVoucherList = uvSellUnusedVoucherRepository.findAll();
        assertThat(uvSellUnusedVoucherList).hasSize(databaseSizeBeforeCreate + 1);
        UvSellUnusedVoucher testUvSellUnusedVoucher = uvSellUnusedVoucherList.get(uvSellUnusedVoucherList.size() - 1);
        assertThat(testUvSellUnusedVoucher.getVoucherCode()).isEqualTo(DEFAULT_VOUCHER_CODE);
        assertThat(testUvSellUnusedVoucher.isIsValid()).isEqualTo(DEFAULT_IS_VALID);
        assertThat(testUvSellUnusedVoucher.isIsExpired()).isEqualTo(DEFAULT_IS_EXPIRED);
        assertThat(testUvSellUnusedVoucher.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testUvSellUnusedVoucher.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testUvSellUnusedVoucher.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void createUvSellUnusedVoucherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uvSellUnusedVoucherRepository.findAll().size();

        // Create the UvSellUnusedVoucher with an existing ID
        uvSellUnusedVoucher.setId(1L);
        UvSellUnusedVoucherDTO uvSellUnusedVoucherDTO = uvSellUnusedVoucherMapper.toDto(uvSellUnusedVoucher);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUvSellUnusedVoucherMockMvc.perform(post("/api/uv-sell-unused-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvSellUnusedVoucherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UvSellUnusedVoucher in the database
        List<UvSellUnusedVoucher> uvSellUnusedVoucherList = uvSellUnusedVoucherRepository.findAll();
        assertThat(uvSellUnusedVoucherList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUvSellUnusedVouchers() throws Exception {
        // Initialize the database
        uvSellUnusedVoucherRepository.saveAndFlush(uvSellUnusedVoucher);

        // Get all the uvSellUnusedVoucherList
        restUvSellUnusedVoucherMockMvc.perform(get("/api/uv-sell-unused-vouchers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uvSellUnusedVoucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherCode").value(hasItem(DEFAULT_VOUCHER_CODE.toString())))
            .andExpect(jsonPath("$.[*].isValid").value(hasItem(DEFAULT_IS_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].isExpired").value(hasItem(DEFAULT_IS_EXPIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(DEFAULT_LAST_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getUvSellUnusedVoucher() throws Exception {
        // Initialize the database
        uvSellUnusedVoucherRepository.saveAndFlush(uvSellUnusedVoucher);

        // Get the uvSellUnusedVoucher
        restUvSellUnusedVoucherMockMvc.perform(get("/api/uv-sell-unused-vouchers/{id}", uvSellUnusedVoucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uvSellUnusedVoucher.getId().intValue()))
            .andExpect(jsonPath("$.voucherCode").value(DEFAULT_VOUCHER_CODE.toString()))
            .andExpect(jsonPath("$.isValid").value(DEFAULT_IS_VALID.booleanValue()))
            .andExpect(jsonPath("$.isExpired").value(DEFAULT_IS_EXPIRED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.lastUpdatedOn").value(DEFAULT_LAST_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUvSellUnusedVoucher() throws Exception {
        // Get the uvSellUnusedVoucher
        restUvSellUnusedVoucherMockMvc.perform(get("/api/uv-sell-unused-vouchers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUvSellUnusedVoucher() throws Exception {
        // Initialize the database
        uvSellUnusedVoucherRepository.saveAndFlush(uvSellUnusedVoucher);
        int databaseSizeBeforeUpdate = uvSellUnusedVoucherRepository.findAll().size();

        // Update the uvSellUnusedVoucher
        UvSellUnusedVoucher updatedUvSellUnusedVoucher = uvSellUnusedVoucherRepository.findOne(uvSellUnusedVoucher.getId());
        // Disconnect from session so that the updates on updatedUvSellUnusedVoucher are not directly saved in db
        em.detach(updatedUvSellUnusedVoucher);
        updatedUvSellUnusedVoucher
            .voucherCode(UPDATED_VOUCHER_CODE)
            .isValid(UPDATED_IS_VALID)
            .isExpired(UPDATED_IS_EXPIRED)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);
        UvSellUnusedVoucherDTO uvSellUnusedVoucherDTO = uvSellUnusedVoucherMapper.toDto(updatedUvSellUnusedVoucher);

        restUvSellUnusedVoucherMockMvc.perform(put("/api/uv-sell-unused-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvSellUnusedVoucherDTO)))
            .andExpect(status().isOk());

        // Validate the UvSellUnusedVoucher in the database
        List<UvSellUnusedVoucher> uvSellUnusedVoucherList = uvSellUnusedVoucherRepository.findAll();
        assertThat(uvSellUnusedVoucherList).hasSize(databaseSizeBeforeUpdate);
        UvSellUnusedVoucher testUvSellUnusedVoucher = uvSellUnusedVoucherList.get(uvSellUnusedVoucherList.size() - 1);
        assertThat(testUvSellUnusedVoucher.getVoucherCode()).isEqualTo(UPDATED_VOUCHER_CODE);
        assertThat(testUvSellUnusedVoucher.isIsValid()).isEqualTo(UPDATED_IS_VALID);
        assertThat(testUvSellUnusedVoucher.isIsExpired()).isEqualTo(UPDATED_IS_EXPIRED);
        assertThat(testUvSellUnusedVoucher.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUvSellUnusedVoucher.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUvSellUnusedVoucher.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingUvSellUnusedVoucher() throws Exception {
        int databaseSizeBeforeUpdate = uvSellUnusedVoucherRepository.findAll().size();

        // Create the UvSellUnusedVoucher
        UvSellUnusedVoucherDTO uvSellUnusedVoucherDTO = uvSellUnusedVoucherMapper.toDto(uvSellUnusedVoucher);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUvSellUnusedVoucherMockMvc.perform(put("/api/uv-sell-unused-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvSellUnusedVoucherDTO)))
            .andExpect(status().isCreated());

        // Validate the UvSellUnusedVoucher in the database
        List<UvSellUnusedVoucher> uvSellUnusedVoucherList = uvSellUnusedVoucherRepository.findAll();
        assertThat(uvSellUnusedVoucherList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUvSellUnusedVoucher() throws Exception {
        // Initialize the database
        uvSellUnusedVoucherRepository.saveAndFlush(uvSellUnusedVoucher);
        int databaseSizeBeforeDelete = uvSellUnusedVoucherRepository.findAll().size();

        // Get the uvSellUnusedVoucher
        restUvSellUnusedVoucherMockMvc.perform(delete("/api/uv-sell-unused-vouchers/{id}", uvSellUnusedVoucher.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UvSellUnusedVoucher> uvSellUnusedVoucherList = uvSellUnusedVoucherRepository.findAll();
        assertThat(uvSellUnusedVoucherList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UvSellUnusedVoucher.class);
        UvSellUnusedVoucher uvSellUnusedVoucher1 = new UvSellUnusedVoucher();
        uvSellUnusedVoucher1.setId(1L);
        UvSellUnusedVoucher uvSellUnusedVoucher2 = new UvSellUnusedVoucher();
        uvSellUnusedVoucher2.setId(uvSellUnusedVoucher1.getId());
        assertThat(uvSellUnusedVoucher1).isEqualTo(uvSellUnusedVoucher2);
        uvSellUnusedVoucher2.setId(2L);
        assertThat(uvSellUnusedVoucher1).isNotEqualTo(uvSellUnusedVoucher2);
        uvSellUnusedVoucher1.setId(null);
        assertThat(uvSellUnusedVoucher1).isNotEqualTo(uvSellUnusedVoucher2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UvSellUnusedVoucherDTO.class);
        UvSellUnusedVoucherDTO uvSellUnusedVoucherDTO1 = new UvSellUnusedVoucherDTO();
        uvSellUnusedVoucherDTO1.setId(1L);
        UvSellUnusedVoucherDTO uvSellUnusedVoucherDTO2 = new UvSellUnusedVoucherDTO();
        assertThat(uvSellUnusedVoucherDTO1).isNotEqualTo(uvSellUnusedVoucherDTO2);
        uvSellUnusedVoucherDTO2.setId(uvSellUnusedVoucherDTO1.getId());
        assertThat(uvSellUnusedVoucherDTO1).isEqualTo(uvSellUnusedVoucherDTO2);
        uvSellUnusedVoucherDTO2.setId(2L);
        assertThat(uvSellUnusedVoucherDTO1).isNotEqualTo(uvSellUnusedVoucherDTO2);
        uvSellUnusedVoucherDTO1.setId(null);
        assertThat(uvSellUnusedVoucherDTO1).isNotEqualTo(uvSellUnusedVoucherDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(uvSellUnusedVoucherMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(uvSellUnusedVoucherMapper.fromId(null)).isNull();
    }
}
