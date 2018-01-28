package com.edjaz.blog.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.edjaz.blog.service.KeyWordService;
import com.edjaz.blog.web.rest.errors.BadRequestAlertException;
import com.edjaz.blog.web.rest.util.HeaderUtil;
import com.edjaz.blog.web.rest.util.PaginationUtil;
import com.edjaz.blog.service.dto.KeyWordDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing KeyWord.
 */
@RestController
@RequestMapping("/api")
public class KeyWordResource {

    private final Logger log = LoggerFactory.getLogger(KeyWordResource.class);

    private static final String ENTITY_NAME = "keyWord";

    private final KeyWordService keyWordService;

    public KeyWordResource(KeyWordService keyWordService) {
        this.keyWordService = keyWordService;
    }

    /**
     * POST  /key-words : Create a new keyWord.
     *
     * @param keyWordDTO the keyWordDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new keyWordDTO, or with status 400 (Bad Request) if the keyWord has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/key-words")
    @Timed
    public ResponseEntity<KeyWordDTO> createKeyWord(@Valid @RequestBody KeyWordDTO keyWordDTO) throws URISyntaxException {
        log.debug("REST request to save KeyWord : {}", keyWordDTO);
        if (keyWordDTO.getId() != null) {
            throw new BadRequestAlertException("A new keyWord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KeyWordDTO result = keyWordService.save(keyWordDTO);
        return ResponseEntity.created(new URI("/api/key-words/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /key-words : Updates an existing keyWord.
     *
     * @param keyWordDTO the keyWordDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated keyWordDTO,
     * or with status 400 (Bad Request) if the keyWordDTO is not valid,
     * or with status 500 (Internal Server Error) if the keyWordDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/key-words")
    @Timed
    public ResponseEntity<KeyWordDTO> updateKeyWord(@Valid @RequestBody KeyWordDTO keyWordDTO) throws URISyntaxException {
        log.debug("REST request to update KeyWord : {}", keyWordDTO);
        if (keyWordDTO.getId() == null) {
            return createKeyWord(keyWordDTO);
        }
        KeyWordDTO result = keyWordService.save(keyWordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, keyWordDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /key-words : get all the keyWords.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of keyWords in body
     */
    @GetMapping("/key-words")
    @Timed
    public ResponseEntity<List<KeyWordDTO>> getAllKeyWords(Pageable pageable) {
        log.debug("REST request to get a page of KeyWords");
        Page<KeyWordDTO> page = keyWordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/key-words");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /key-words/:id : get the "id" keyWord.
     *
     * @param id the id of the keyWordDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the keyWordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/key-words/{id}")
    @Timed
    public ResponseEntity<KeyWordDTO> getKeyWord(@PathVariable Long id) {
        log.debug("REST request to get KeyWord : {}", id);
        KeyWordDTO keyWordDTO = keyWordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(keyWordDTO));
    }

    /**
     * DELETE  /key-words/:id : delete the "id" keyWord.
     *
     * @param id the id of the keyWordDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/key-words/{id}")
    @Timed
    public ResponseEntity<Void> deleteKeyWord(@PathVariable Long id) {
        log.debug("REST request to delete KeyWord : {}", id);
        keyWordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/key-words?query=:query : search for the keyWord corresponding
     * to the query.
     *
     * @param query the query of the keyWord search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/key-words")
    @Timed
    public ResponseEntity<List<KeyWordDTO>> searchKeyWords(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of KeyWords for query {}", query);
        Page<KeyWordDTO> page = keyWordService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/key-words");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
