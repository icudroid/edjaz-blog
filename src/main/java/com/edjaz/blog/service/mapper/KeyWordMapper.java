package com.edjaz.blog.service.mapper;

import com.edjaz.blog.domain.*;
import com.edjaz.blog.service.dto.KeyWordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity KeyWord and its DTO KeyWordDTO.
 */
@Mapper(componentModel = "spring", uses = {BlogItemMapper.class})
public interface KeyWordMapper extends EntityMapper<KeyWordDTO, KeyWord> {

    @Mapping(source = "blogItem.id", target = "blogItemId")
    KeyWordDTO toDto(KeyWord keyWord);

    @Mapping(source = "blogItemId", target = "blogItem")
    KeyWord toEntity(KeyWordDTO keyWordDTO);

    default KeyWord fromId(Long id) {
        if (id == null) {
            return null;
        }
        KeyWord keyWord = new KeyWord();
        keyWord.setId(id);
        return keyWord;
    }
}
