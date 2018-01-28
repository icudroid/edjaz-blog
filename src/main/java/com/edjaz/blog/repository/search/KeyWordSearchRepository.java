package com.edjaz.blog.repository.search;

import com.edjaz.blog.domain.KeyWord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the KeyWord entity.
 */
public interface KeyWordSearchRepository extends ElasticsearchRepository<KeyWord, Long> {
}
