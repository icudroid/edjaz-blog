package com.edjaz.blog.web.rest;

import com.edjaz.blog.BlogApp;

import com.edjaz.blog.domain.KeyWord;
import com.edjaz.blog.repository.KeyWordRepository;
import com.edjaz.blog.service.KeyWordService;
import com.edjaz.blog.repository.search.KeyWordSearchRepository;
import com.edjaz.blog.service.dto.KeyWordDTO;
import com.edjaz.blog.service.mapper.KeyWordMapper;
import com.edjaz.blog.web.rest.errors.ExceptionTranslator;

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

import static com.edjaz.blog.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the KeyWordResource REST controller.
 *
 * @see KeyWordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApp.class)
public class KeyWordResourceIntTest {

    private static final String DEFAULT_WORD = "AAAAAAAAAA";
    private static final String UPDATED_WORD = "BBBBBBBBBB";

    @Autowired
    private KeyWordRepository keyWordRepository;

    @Autowired
    private KeyWordMapper keyWordMapper;

    @Autowired
    private KeyWordService keyWordService;

    @Autowired
    private KeyWordSearchRepository keyWordSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKeyWordMockMvc;

    private KeyWord keyWord;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KeyWordResource keyWordResource = new KeyWordResource(keyWordService);
        this.restKeyWordMockMvc = MockMvcBuilders.standaloneSetup(keyWordResource)
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
    public static KeyWord createEntity(EntityManager em) {
        KeyWord keyWord = new KeyWord()
            .word(DEFAULT_WORD);
        return keyWord;
    }

    @Before
    public void initTest() {
        keyWordSearchRepository.deleteAll();
        keyWord = createEntity(em);
    }

    @Test
    @Transactional
    public void createKeyWord() throws Exception {
        int databaseSizeBeforeCreate = keyWordRepository.findAll().size();

        // Create the KeyWord
        KeyWordDTO keyWordDTO = keyWordMapper.toDto(keyWord);
        restKeyWordMockMvc.perform(post("/api/key-words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(keyWordDTO)))
            .andExpect(status().isCreated());

        // Validate the KeyWord in the database
        List<KeyWord> keyWordList = keyWordRepository.findAll();
        assertThat(keyWordList).hasSize(databaseSizeBeforeCreate + 1);
        KeyWord testKeyWord = keyWordList.get(keyWordList.size() - 1);
        assertThat(testKeyWord.getWord()).isEqualTo(DEFAULT_WORD);

        // Validate the KeyWord in Elasticsearch
        KeyWord keyWordEs = keyWordSearchRepository.findOne(testKeyWord.getId());
        assertThat(keyWordEs).isEqualToIgnoringGivenFields(testKeyWord);
    }

    @Test
    @Transactional
    public void createKeyWordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = keyWordRepository.findAll().size();

        // Create the KeyWord with an existing ID
        keyWord.setId(1L);
        KeyWordDTO keyWordDTO = keyWordMapper.toDto(keyWord);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKeyWordMockMvc.perform(post("/api/key-words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(keyWordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KeyWord in the database
        List<KeyWord> keyWordList = keyWordRepository.findAll();
        assertThat(keyWordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkWordIsRequired() throws Exception {
        int databaseSizeBeforeTest = keyWordRepository.findAll().size();
        // set the field null
        keyWord.setWord(null);

        // Create the KeyWord, which fails.
        KeyWordDTO keyWordDTO = keyWordMapper.toDto(keyWord);

        restKeyWordMockMvc.perform(post("/api/key-words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(keyWordDTO)))
            .andExpect(status().isBadRequest());

        List<KeyWord> keyWordList = keyWordRepository.findAll();
        assertThat(keyWordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKeyWords() throws Exception {
        // Initialize the database
        keyWordRepository.saveAndFlush(keyWord);

        // Get all the keyWordList
        restKeyWordMockMvc.perform(get("/api/key-words?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keyWord.getId().intValue())))
            .andExpect(jsonPath("$.[*].word").value(hasItem(DEFAULT_WORD.toString())));
    }

    @Test
    @Transactional
    public void getKeyWord() throws Exception {
        // Initialize the database
        keyWordRepository.saveAndFlush(keyWord);

        // Get the keyWord
        restKeyWordMockMvc.perform(get("/api/key-words/{id}", keyWord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(keyWord.getId().intValue()))
            .andExpect(jsonPath("$.word").value(DEFAULT_WORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKeyWord() throws Exception {
        // Get the keyWord
        restKeyWordMockMvc.perform(get("/api/key-words/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKeyWord() throws Exception {
        // Initialize the database
        keyWordRepository.saveAndFlush(keyWord);
        keyWordSearchRepository.save(keyWord);
        int databaseSizeBeforeUpdate = keyWordRepository.findAll().size();

        // Update the keyWord
        KeyWord updatedKeyWord = keyWordRepository.findOne(keyWord.getId());
        // Disconnect from session so that the updates on updatedKeyWord are not directly saved in db
        em.detach(updatedKeyWord);
        updatedKeyWord
            .word(UPDATED_WORD);
        KeyWordDTO keyWordDTO = keyWordMapper.toDto(updatedKeyWord);

        restKeyWordMockMvc.perform(put("/api/key-words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(keyWordDTO)))
            .andExpect(status().isOk());

        // Validate the KeyWord in the database
        List<KeyWord> keyWordList = keyWordRepository.findAll();
        assertThat(keyWordList).hasSize(databaseSizeBeforeUpdate);
        KeyWord testKeyWord = keyWordList.get(keyWordList.size() - 1);
        assertThat(testKeyWord.getWord()).isEqualTo(UPDATED_WORD);

        // Validate the KeyWord in Elasticsearch
        KeyWord keyWordEs = keyWordSearchRepository.findOne(testKeyWord.getId());
        assertThat(keyWordEs).isEqualToIgnoringGivenFields(testKeyWord);
    }

    @Test
    @Transactional
    public void updateNonExistingKeyWord() throws Exception {
        int databaseSizeBeforeUpdate = keyWordRepository.findAll().size();

        // Create the KeyWord
        KeyWordDTO keyWordDTO = keyWordMapper.toDto(keyWord);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKeyWordMockMvc.perform(put("/api/key-words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(keyWordDTO)))
            .andExpect(status().isCreated());

        // Validate the KeyWord in the database
        List<KeyWord> keyWordList = keyWordRepository.findAll();
        assertThat(keyWordList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKeyWord() throws Exception {
        // Initialize the database
        keyWordRepository.saveAndFlush(keyWord);
        keyWordSearchRepository.save(keyWord);
        int databaseSizeBeforeDelete = keyWordRepository.findAll().size();

        // Get the keyWord
        restKeyWordMockMvc.perform(delete("/api/key-words/{id}", keyWord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean keyWordExistsInEs = keyWordSearchRepository.exists(keyWord.getId());
        assertThat(keyWordExistsInEs).isFalse();

        // Validate the database is empty
        List<KeyWord> keyWordList = keyWordRepository.findAll();
        assertThat(keyWordList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchKeyWord() throws Exception {
        // Initialize the database
        keyWordRepository.saveAndFlush(keyWord);
        keyWordSearchRepository.save(keyWord);

        // Search the keyWord
        restKeyWordMockMvc.perform(get("/api/_search/key-words?query=id:" + keyWord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keyWord.getId().intValue())))
            .andExpect(jsonPath("$.[*].word").value(hasItem(DEFAULT_WORD.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeyWord.class);
        KeyWord keyWord1 = new KeyWord();
        keyWord1.setId(1L);
        KeyWord keyWord2 = new KeyWord();
        keyWord2.setId(keyWord1.getId());
        assertThat(keyWord1).isEqualTo(keyWord2);
        keyWord2.setId(2L);
        assertThat(keyWord1).isNotEqualTo(keyWord2);
        keyWord1.setId(null);
        assertThat(keyWord1).isNotEqualTo(keyWord2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeyWordDTO.class);
        KeyWordDTO keyWordDTO1 = new KeyWordDTO();
        keyWordDTO1.setId(1L);
        KeyWordDTO keyWordDTO2 = new KeyWordDTO();
        assertThat(keyWordDTO1).isNotEqualTo(keyWordDTO2);
        keyWordDTO2.setId(keyWordDTO1.getId());
        assertThat(keyWordDTO1).isEqualTo(keyWordDTO2);
        keyWordDTO2.setId(2L);
        assertThat(keyWordDTO1).isNotEqualTo(keyWordDTO2);
        keyWordDTO1.setId(null);
        assertThat(keyWordDTO1).isNotEqualTo(keyWordDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(keyWordMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(keyWordMapper.fromId(null)).isNull();
    }
}
