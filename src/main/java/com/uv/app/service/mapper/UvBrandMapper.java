package com.uv.app.service.mapper;

import com.uv.app.domain.*;
import com.uv.app.service.dto.UvBrandDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UvBrand and its DTO UvBrandDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UvBrandMapper extends EntityMapper<UvBrandDTO, UvBrand> {

    

    

    default UvBrand fromId(Long id) {
        if (id == null) {
            return null;
        }
        UvBrand uvBrand = new UvBrand();
        uvBrand.setId(id);
        return uvBrand;
    }
}
