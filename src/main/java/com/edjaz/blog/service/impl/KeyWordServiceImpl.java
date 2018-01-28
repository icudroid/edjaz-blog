package com.edjaz.blog.service.impl;

import com.edjaz.blog.service.KeyWordService;
import com.edjaz.blog.domain.KeyWord;
import com.edjaz.blog.repository.KeyWordRepository;
import com.edjaz.blog.repository.search.KeyWordSearchRepository;
import com.edjaz.blog.service.dto.KeyWordDTO;
import com.edjaz.blog.service.mapper.KeyWordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing KeyWord.
 */
@Service
@Transactional
public class KeyWordServiceImpl implements KeyWordService {

    private final Logger log = LoggerFactory.getLogger(KeyWordServiceImpl.class);

    private final KeyWordRepository keyWordRepository;

    private final KeyWordMapper keyWordMapper;

    private final KeyWordSearchRepository keyWordSearchRepository;

    public KeyWordServiceImpl(KeyWordRepository keyWordRepository, KeyWordMapper keyWordMapper, KeyWordSearchRepository keyWordSearchRepository) {
        this.keyWordRepository = keyWordRepository;
        this.keyWordMapper = keyWordMapper;
        this.keyWordSearchRepository = keyWordSearchRepository;
    }

    /**
     * Save a keyWord.
     *
     * @param keyWordDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KeyWordDTO save(KeyWordDTO keyWordDTO) {
        log.debug("Request to save KeyWord : {}", keyWordDTO);
        KeyWord keyWord = keyWordMapper.toEntity(keyWordDTO);
        keyWord = keyWordRepository.save(keyWord);
        KeyWordDTO result = keyWordMapper.toDto(keyWord);
        keyWordSearchRepository.save(keyWord);
        return result;
    }

    /**
     * Get all the keyWords.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KeyWordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all KeyWords");
        return keyWordRepository.findAll(pageable)
            .map(keyWordMapper::toDto);
    }

    /**
     * Get one keyWord by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public KeyWordDTO findOne(Long id) {
        log.debug("Request to get KeyWord : {}", id);
        KeyWord keyWord = keyWordRepository.findOne(id);
        return keyWordMapper.toDto(keyWord);
    }

    /**
     * Delete the keyWord by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete KeyWord : {}", id);
        keyWordRepository.delete(id);
        keyWordSearchRepository.delete(id);
    }

    /**
     * Search for the keyWord corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KeyWordDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of KeyWords for query {}", query);
        Page<KeyWord> result = keyWordSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(keyWordMapper::toDto);
    }
}
