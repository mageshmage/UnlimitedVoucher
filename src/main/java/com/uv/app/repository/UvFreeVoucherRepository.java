package com.uv.app.repository;

import com.uv.app.domain.UvFreeVoucher;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UvFreeVoucher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UvFreeVoucherRepository extends JpaRepository<UvFreeVoucher, Long> {

}
