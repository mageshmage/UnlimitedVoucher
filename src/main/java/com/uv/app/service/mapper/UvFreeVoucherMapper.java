package com.uv.app.service.mapper;

import com.uv.app.domain.*;
import com.uv.app.service.dto.UvFreeVoucherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UvFreeVoucher and its DTO UvFreeVoucherDTO.
 */
@Mapper(componentModel = "spring", uses = {UvBrandMapper.class, UvCategoryMapper.class})
public interface UvFreeVoucherMapper extends EntityMapper<UvFreeVoucherDTO, UvFreeVoucher> {

    @Mapping(source = "brand.id", target = "brandId")
    @Mapping(source = "category.id", target = "categoryId")
    UvFreeVoucherDTO toDto(UvFreeVoucher uvFreeVoucher); 

    @Mapping(source = "brandId", target = "brand")
    @Mapping(source = "categoryId", target = "category")
    UvFreeVoucher toEntity(UvFreeVoucherDTO uvFreeVoucherDTO);

    default UvFreeVoucher fromId(Long id) {
        if (id == null) {
            return null;
        }
        UvFreeVoucher uvFreeVoucher = new UvFreeVoucher();
        uvFreeVoucher.setId(id);
        return uvFreeVoucher;
    }
}
