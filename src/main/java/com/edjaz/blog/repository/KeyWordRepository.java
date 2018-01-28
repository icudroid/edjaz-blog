package com.edjaz.blog.repository;

import com.edjaz.blog.domain.KeyWord;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the KeyWord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeyWordRepository extends JpaRepository<KeyWord, Long> {

}
