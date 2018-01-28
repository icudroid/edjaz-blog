package com.edjaz.blog.service;

import com.edjaz.blog.service.dto.KeyWordDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing KeyWord.
 */
public interface KeyWordService {

    /**
     * Save a keyWord.
     *
     * @param keyWordDTO the entity to save
     * @return the persisted entity
     */
    KeyWordDTO save(KeyWordDTO keyWordDTO);

    /**
     * Get all the keyWords.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<KeyWordDTO> findAll(Pageable pageable);

    /**
     * Get the "id" keyWord.
     *
     * @param id the id of the entity
     * @return the entity
     */
    KeyWordDTO findOne(Long id);

    /**
     * Delete the "id" keyWord.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the keyWord corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<KeyWordDTO> search(String query, Pageable pageable);
}
