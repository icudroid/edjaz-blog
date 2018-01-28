package com.edjaz.blog.service;

import com.edjaz.blog.service.dto.BlogItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BlogItem.
 */
public interface BlogItemService {

    /**
     * Save a blogItem.
     *
     * @param blogItemDTO the entity to save
     * @return the persisted entity
     */
    BlogItemDTO save(BlogItemDTO blogItemDTO);

    /**
     * Get all the blogItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BlogItemDTO> findAll(Pageable pageable);

    /**
     * Get the "id" blogItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BlogItemDTO findOne(Long id);

    /**
     * Delete the "id" blogItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the blogItem corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BlogItemDTO> search(String query, Pageable pageable);
}
