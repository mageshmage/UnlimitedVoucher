package com.uv.app.service.mapper;

import com.uv.app.domain.*;
import com.uv.app.service.dto.UvSellUnusedVoucherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UvSellUnusedVoucher and its DTO UvSellUnusedVoucherDTO.
 */
@Mapper(componentModel = "spring", uses = {UvBrandMapper.class, UvCategoryMapper.class, UvSellUnusedVoucherUserMapper.class})
public interface UvSellUnusedVoucherMapper extends EntityMapper<UvSellUnusedVoucherDTO, UvSellUnusedVoucher> {

    @Mapping(source = "brand.id", target = "brandId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "user.id", target = "userId")
    UvSellUnusedVoucherDTO toDto(UvSellUnusedVoucher uvSellUnusedVoucher); 

    @Mapping(source = "brandId", target = "brand")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "userId", target = "user")
    UvSellUnusedVoucher toEntity(UvSellUnusedVoucherDTO uvSellUnusedVoucherDTO);

    default UvSellUnusedVoucher fromId(Long id) {
        if (id == null) {
            return null;
        }
        UvSellUnusedVoucher uvSellUnusedVoucher = new UvSellUnusedVoucher();
        uvSellUnusedVoucher.setId(id);
        return uvSellUnusedVoucher;
    }
}
