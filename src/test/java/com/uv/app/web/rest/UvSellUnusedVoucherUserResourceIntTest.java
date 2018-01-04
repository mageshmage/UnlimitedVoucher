package com.uv.app.web.rest;

import com.uv.app.UnlimitedVoucherApp;

import com.uv.app.domain.UvSellUnusedVoucherUser;
import com.uv.app.repository.UvSellUnusedVoucherUserRepository;
import com.uv.app.service.UvSellUnusedVoucherUserService;
import com.uv.app.service.dto.UvSellUnusedVoucherUserDTO;
import com.uv.app.service.mapper.UvSellUnusedVoucherUserMapper;
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
 * Test class for the UvSellUnusedVoucherUserResource REST controller.
 *
 * @see UvSellUnusedVoucherUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnlimitedVoucherApp.class)
public class UvSellUnusedVoucherUserResourceIntTest {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VERIFIED_USER = false;
    private static final Boolean UPDATED_IS_VERIFIED_USER = true;

    @Autowired
    private UvSellUnusedVoucherUserRepository uvSellUnusedVoucherUserRepository;

    @Autowired
    private UvSellUnusedVoucherUserMapper uvSellUnusedVoucherUserMapper;

    @Autowired
    private UvSellUnusedVoucherUserService uvSellUnusedVoucherUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUvSellUnusedVoucherUserMockMvc;

    private UvSellUnusedVoucherUser uvSellUnusedVoucherUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UvSellUnusedVoucherUserResource uvSellUnusedVoucherUserResource = new UvSellUnusedVoucherUserResource(uvSellUnusedVoucherUserService);
        this.restUvSellUnusedVoucherUserMockMvc = MockMvcBuilders.standaloneSetup(uvSellUnusedVoucherUserResource)
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
    public static UvSellUnusedVoucherUser createEntity(EntityManager em) {
        UvSellUnusedVoucherUser uvSellUnusedVoucherUser = new UvSellUnusedVoucherUser()
            .userName(DEFAULT_USER_NAME)
            .password(DEFAULT_PASSWORD)
            .email(DEFAULT_EMAIL)
            .isVerifiedUser(DEFAULT_IS_VERIFIED_USER);
        return uvSellUnusedVoucherUser;
    }

    @Before
    public void initTest() {
        uvSellUnusedVoucherUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createUvSellUnusedVoucherUser() throws Exception {
        int databaseSizeBeforeCreate = uvSellUnusedVoucherUserRepository.findAll().size();

        // Create the UvSellUnusedVoucherUser
        UvSellUnusedVoucherUserDTO uvSellUnusedVoucherUserDTO = uvSellUnusedVoucherUserMapper.toDto(uvSellUnusedVoucherUser);
        restUvSellUnusedVoucherUserMockMvc.perform(post("/api/uv-sell-unused-voucher-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvSellUnusedVoucherUserDTO)))
            .andExpect(status().isCreated());

        // Validate the UvSellUnusedVoucherUser in the database
        List<UvSellUnusedVoucherUser> uvSellUnusedVoucherUserList = uvSellUnusedVoucherUserRepository.findAll();
        assertThat(uvSellUnusedVoucherUserList).hasSize(databaseSizeBeforeCreate + 1);
        UvSellUnusedVoucherUser testUvSellUnusedVoucherUser = uvSellUnusedVoucherUserList.get(uvSellUnusedVoucherUserList.size() - 1);
        assertThat(testUvSellUnusedVoucherUser.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testUvSellUnusedVoucherUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUvSellUnusedVoucherUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUvSellUnusedVoucherUser.isIsVerifiedUser()).isEqualTo(DEFAULT_IS_VERIFIED_USER);
    }

    @Test
    @Transactional
    public void createUvSellUnusedVoucherUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uvSellUnusedVoucherUserRepository.findAll().size();

        // Create the UvSellUnusedVoucherUser with an existing ID
        uvSellUnusedVoucherUser.setId(1L);
        UvSellUnusedVoucherUserDTO uvSellUnusedVoucherUserDTO = uvSellUnusedVoucherUserMapper.toDto(uvSellUnusedVoucherUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUvSellUnusedVoucherUserMockMvc.perform(post("/api/uv-sell-unused-voucher-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvSellUnusedVoucherUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UvSellUnusedVoucherUser in the database
        List<UvSellUnusedVoucherUser> uvSellUnusedVoucherUserList = uvSellUnusedVoucherUserRepository.findAll();
        assertThat(uvSellUnusedVoucherUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUvSellUnusedVoucherUsers() throws Exception {
        // Initialize the database
        uvSellUnusedVoucherUserRepository.saveAndFlush(uvSellUnusedVoucherUser);

        // Get all the uvSellUnusedVoucherUserList
        restUvSellUnusedVoucherUserMockMvc.perform(get("/api/uv-sell-unused-voucher-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uvSellUnusedVoucherUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].isVerifiedUser").value(hasItem(DEFAULT_IS_VERIFIED_USER.booleanValue())));
    }

    @Test
    @Transactional
    public void getUvSellUnusedVoucherUser() throws Exception {
        // Initialize the database
        uvSellUnusedVoucherUserRepository.saveAndFlush(uvSellUnusedVoucherUser);

        // Get the uvSellUnusedVoucherUser
        restUvSellUnusedVoucherUserMockMvc.perform(get("/api/uv-sell-unused-voucher-users/{id}", uvSellUnusedVoucherUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uvSellUnusedVoucherUser.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.isVerifiedUser").value(DEFAULT_IS_VERIFIED_USER.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUvSellUnusedVoucherUser() throws Exception {
        // Get the uvSellUnusedVoucherUser
        restUvSellUnusedVoucherUserMockMvc.perform(get("/api/uv-sell-unused-voucher-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUvSellUnusedVoucherUser() throws Exception {
        // Initialize the database
        uvSellUnusedVoucherUserRepository.saveAndFlush(uvSellUnusedVoucherUser);
        int databaseSizeBeforeUpdate = uvSellUnusedVoucherUserRepository.findAll().size();

        // Update the uvSellUnusedVoucherUser
        UvSellUnusedVoucherUser updatedUvSellUnusedVoucherUser = uvSellUnusedVoucherUserRepository.findOne(uvSellUnusedVoucherUser.getId());
        // Disconnect from session so that the updates on updatedUvSellUnusedVoucherUser are not directly saved in db
        em.detach(updatedUvSellUnusedVoucherUser);
        updatedUvSellUnusedVoucherUser
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .email(UPDATED_EMAIL)
            .isVerifiedUser(UPDATED_IS_VERIFIED_USER);
        UvSellUnusedVoucherUserDTO uvSellUnusedVoucherUserDTO = uvSellUnusedVoucherUserMapper.toDto(updatedUvSellUnusedVoucherUser);

        restUvSellUnusedVoucherUserMockMvc.perform(put("/api/uv-sell-unused-voucher-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvSellUnusedVoucherUserDTO)))
            .andExpect(status().isOk());

        // Validate the UvSellUnusedVoucherUser in the database
        List<UvSellUnusedVoucherUser> uvSellUnusedVoucherUserList = uvSellUnusedVoucherUserRepository.findAll();
        assertThat(uvSellUnusedVoucherUserList).hasSize(databaseSizeBeforeUpdate);
        UvSellUnusedVoucherUser testUvSellUnusedVoucherUser = uvSellUnusedVoucherUserList.get(uvSellUnusedVoucherUserList.size() - 1);
        assertThat(testUvSellUnusedVoucherUser.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUvSellUnusedVoucherUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUvSellUnusedVoucherUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUvSellUnusedVoucherUser.isIsVerifiedUser()).isEqualTo(UPDATED_IS_VERIFIED_USER);
    }

    @Test
    @Transactional
    public void updateNonExistingUvSellUnusedVoucherUser() throws Exception {
        int databaseSizeBeforeUpdate = uvSellUnusedVoucherUserRepository.findAll().size();

        // Create the UvSellUnusedVoucherUser
        UvSellUnusedVoucherUserDTO uvSellUnusedVoucherUserDTO = uvSellUnusedVoucherUserMapper.toDto(uvSellUnusedVoucherUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUvSellUnusedVoucherUserMockMvc.perform(put("/api/uv-sell-unused-voucher-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uvSellUnusedVoucherUserDTO)))
            .andExpect(status().isCreated());

        // Validate the UvSellUnusedVoucherUser in the database
        List<UvSellUnusedVoucherUser> uvSellUnusedVoucherUserList = uvSellUnusedVoucherUserRepository.findAll();
        assertThat(uvSellUnusedVoucherUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUvSellUnusedVoucherUser() throws Exception {
        // Initialize the database
        uvSellUnusedVoucherUserRepository.saveAndFlush(uvSellUnusedVoucherUser);
        int databaseSizeBeforeDelete = uvSellUnusedVoucherUserRepository.findAll().size();

        // Get the uvSellUnusedVoucherUser
        restUvSellUnusedVoucherUserMockMvc.perform(delete("/api/uv-sell-unused-voucher-users/{id}", uvSellUnusedVoucherUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UvSellUnusedVoucherUser> uvSellUnusedVoucherUserList = uvSellUnusedVoucherUserRepository.findAll();
        assertThat(uvSellUnusedVoucherUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UvSellUnusedVoucherUser.class);
        UvSellUnusedVoucherUser uvSellUnusedVoucherUser1 = new UvSellUnusedVoucherUser();
        uvSellUnusedVoucherUser1.setId(1L);
        UvSellUnusedVoucherUser uvSellUnusedVoucherUser2 = new UvSellUnusedVoucherUser();
        uvSellUnusedVoucherUser2.setId(uvSellUnusedVoucherUser1.getId());
        assertThat(uvSellUnusedVoucherUser1).isEqualTo(uvSellUnusedVoucherUser2);
        uvSellUnusedVoucherUser2.setId(2L);
        assertThat(uvSellUnusedVoucherUser1).isNotEqualTo(uvSellUnusedVoucherUser2);
        uvSellUnusedVoucherUser1.setId(null);
        assertThat(uvSellUnusedVoucherUser1).isNotEqualTo(uvSellUnusedVoucherUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UvSellUnusedVoucherUserDTO.class);
        UvSellUnusedVoucherUserDTO uvSellUnusedVoucherUserDTO1 = new UvSellUnusedVoucherUserDTO();
        uvSellUnusedVoucherUserDTO1.setId(1L);
        UvSellUnusedVoucherUserDTO uvSellUnusedVoucherUserDTO2 = new UvSellUnusedVoucherUserDTO();
        assertThat(uvSellUnusedVoucherUserDTO1).isNotEqualTo(uvSellUnusedVoucherUserDTO2);
        uvSellUnusedVoucherUserDTO2.setId(uvSellUnusedVoucherUserDTO1.getId());
        assertThat(uvSellUnusedVoucherUserDTO1).isEqualTo(uvSellUnusedVoucherUserDTO2);
        uvSellUnusedVoucherUserDTO2.setId(2L);
        assertThat(uvSellUnusedVoucherUserDTO1).isNotEqualTo(uvSellUnusedVoucherUserDTO2);
        uvSellUnusedVoucherUserDTO1.setId(null);
        assertThat(uvSellUnusedVoucherUserDTO1).isNotEqualTo(uvSellUnusedVoucherUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(uvSellUnusedVoucherUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(uvSellUnusedVoucherUserMapper.fromId(null)).isNull();
    }
}
