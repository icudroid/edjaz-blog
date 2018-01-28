package com.edjaz.blog.repository;

import com.edjaz.blog.domain.BlogItem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the BlogItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlogItemRepository extends JpaRepository<BlogItem, Long> {

    @Query("select blog_item from BlogItem blog_item where blog_item.author.login = ?#{principal.username}")
    List<BlogItem> findByAuthorIsCurrentUser();
    @Query("select distinct blog_item from BlogItem blog_item left join fetch blog_item.tags")
    List<BlogItem> findAllWithEagerRelationships();

    @Query("select blog_item from BlogItem blog_item left join fetch blog_item.tags where blog_item.id =:id")
    BlogItem findOneWithEagerRelationships(@Param("id") Long id);

}
