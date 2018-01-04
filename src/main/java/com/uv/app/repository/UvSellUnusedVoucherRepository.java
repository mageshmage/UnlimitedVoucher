package com.uv.app.repository;

import com.uv.app.domain.UvSellUnusedVoucher;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UvSellUnusedVoucher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UvSellUnusedVoucherRepository extends JpaRepository<UvSellUnusedVoucher, Long> {

}
