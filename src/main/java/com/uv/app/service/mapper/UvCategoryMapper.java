package com.uv.app.service.mapper;

import com.uv.app.domain.*;
import com.uv.app.service.dto.UvCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UvCategory and its DTO UvCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UvCategoryMapper extends EntityMapper<UvCategoryDTO, UvCategory> {

    

    

    default UvCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        UvCategory uvCategory = new UvCategory();
        uvCategory.setId(id);
        return uvCategory;
    }
}
