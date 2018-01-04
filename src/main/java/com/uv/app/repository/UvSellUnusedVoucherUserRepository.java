package com.uv.app.repository;

import com.uv.app.domain.UvSellUnusedVoucherUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UvSellUnusedVoucherUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UvSellUnusedVoucherUserRepository extends JpaRepository<UvSellUnusedVoucherUser, Long> {

}
