package com.edjaz.blog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A KeyWord.
 */
@Entity
@Table(name = "key_word")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "keyword")
public class KeyWord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "word", nullable = false)
    private String word;

    @ManyToOne
    private BlogItem blogItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public KeyWord word(String word) {
        this.word = word;
        return this;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public BlogItem getBlogItem() {
        return blogItem;
    }

    public KeyWord blogItem(BlogItem blogItem) {
        this.blogItem = blogItem;
        return this;
    }

    public void setBlogItem(BlogItem blogItem) {
        this.blogItem = blogItem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeyWord keyWord = (KeyWord) o;
        if (keyWord.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), keyWord.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KeyWord{" +
            "id=" + getId() +
            ", word='" + getWord() + "'" +
            "}";
    }
}
