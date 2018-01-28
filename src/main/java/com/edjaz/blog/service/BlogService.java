package com.edjaz.blog.service;

import com.edjaz.blog.service.dto.BlogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Blog.
 */
public interface BlogService {

    /**
     * Save a blog.
     *
     * @param blogDTO the entity to save
     * @return the persisted entity
     */
    BlogDTO save(BlogDTO blogDTO);

    /**
     * Get all the blogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BlogDTO> findAll(Pageable pageable);

    /**
     * Get the "id" blog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BlogDTO findOne(Long id);

    /**
     * Delete the "id" blog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the blog corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BlogDTO> search(String query, Pageable pageable);
}
