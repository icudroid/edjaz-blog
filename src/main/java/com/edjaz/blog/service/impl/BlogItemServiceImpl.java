package com.edjaz.blog.service.impl;

import com.edjaz.blog.service.BlogItemService;
import com.edjaz.blog.domain.BlogItem;
import com.edjaz.blog.repository.BlogItemRepository;
import com.edjaz.blog.repository.search.BlogItemSearchRepository;
import com.edjaz.blog.service.dto.BlogItemDTO;
import com.edjaz.blog.service.mapper.BlogItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BlogItem.
 */
@Service
@Transactional
public class BlogItemServiceImpl implements BlogItemService {

    private final Logger log = LoggerFactory.getLogger(BlogItemServiceImpl.class);

    private final BlogItemRepository blogItemRepository;

    private final BlogItemMapper blogItemMapper;

    private final BlogItemSearchRepository blogItemSearchRepository;

    public BlogItemServiceImpl(BlogItemRepository blogItemRepository, BlogItemMapper blogItemMapper, BlogItemSearchRepository blogItemSearchRepository) {
        this.blogItemRepository = blogItemRepository;
        this.blogItemMapper = blogItemMapper;
        this.blogItemSearchRepository = blogItemSearchRepository;
    }

    /**
     * Save a blogItem.
     *
     * @param blogItemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BlogItemDTO save(BlogItemDTO blogItemDTO) {
        log.debug("Request to save BlogItem : {}", blogItemDTO);
        BlogItem blogItem = blogItemMapper.toEntity(blogItemDTO);
        blogItem = blogItemRepository.save(blogItem);
        BlogItemDTO result = blogItemMapper.toDto(blogItem);
        blogItemSearchRepository.save(blogItem);
        return result;
    }

    /**
     * Get all the blogItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BlogItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BlogItems");
        return blogItemRepository.findAll(pageable)
            .map(blogItemMapper::toDto);
    }

    /**
     * Get one blogItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BlogItemDTO findOne(Long id) {
        log.debug("Request to get BlogItem : {}", id);
        BlogItem blogItem = blogItemRepository.findOneWithEagerRelationships(id);
        return blogItemMapper.toDto(blogItem);
    }

    /**
     * Delete the blogItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BlogItem : {}", id);
        blogItemRepository.delete(id);
        blogItemSearchRepository.delete(id);
    }

    /**
     * Search for the blogItem corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BlogItemDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BlogItems for query {}", query);
        Page<BlogItem> result = blogItemSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(blogItemMapper::toDto);
    }
}
