package com.uv.app.service.mapper;

import com.uv.app.domain.*;
import com.uv.app.service.dto.UvSellUnusedVoucherUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UvSellUnusedVoucherUser and its DTO UvSellUnusedVoucherUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UvSellUnusedVoucherUserMapper extends EntityMapper<UvSellUnusedVoucherUserDTO, UvSellUnusedVoucherUser> {

    

    

    default UvSellUnusedVoucherUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        UvSellUnusedVoucherUser uvSellUnusedVoucherUser = new UvSellUnusedVoucherUser();
        uvSellUnusedVoucherUser.setId(id);
        return uvSellUnusedVoucherUser;
    }
}
