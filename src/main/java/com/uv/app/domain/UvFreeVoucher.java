package com.uv.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A UvFreeVoucher.
 */
@Entity
@Table(name = "uv_free_voucher")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UvFreeVoucher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voucher_code")
    private String voucherCode;

    @Column(name = "is_valid")
    private Boolean isValid;

    @Column(name = "is_expired")
    private Boolean isExpired;

    @Column(name = "created_by")
    private Boolean createdBy;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "last_updated_on")
    private LocalDate lastUpdatedOn;

    @ManyToOne
    private UvBrand brand;

    @ManyToOne
    private UvCategory category;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public UvFreeVoucher voucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
        return this;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Boolean isIsValid() {
        return isValid;
    }

    public UvFreeVoucher isValid(Boolean isValid) {
        this.isValid = isValid;
        return this;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Boolean isIsExpired() {
        return isExpired;
    }

    public UvFreeVoucher isExpired(Boolean isExpired) {
        this.isExpired = isExpired;
        return this;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public Boolean isCreatedBy() {
        return createdBy;
    }

    public UvFreeVoucher createdBy(Boolean createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Boolean createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public UvFreeVoucher createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public UvFreeVoucher lastUpdatedOn(LocalDate lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
        return this;
    }

    public void setLastUpdatedOn(LocalDate lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public UvBrand getBrand() {
        return brand;
    }

    public UvFreeVoucher brand(UvBrand uvBrand) {
        this.brand = uvBrand;
        return this;
    }

    public void setBrand(UvBrand uvBrand) {
        this.brand = uvBrand;
    }

    public UvCategory getCategory() {
        return category;
    }

    public UvFreeVoucher category(UvCategory uvCategory) {
        this.category = uvCategory;
        return this;
    }

    public void setCategory(UvCategory uvCategory) {
        this.category = uvCategory;
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
        UvFreeVoucher uvFreeVoucher = (UvFreeVoucher) o;
        if (uvFreeVoucher.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uvFreeVoucher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UvFreeVoucher{" +
            "id=" + getId() +
            ", voucherCode='" + getVoucherCode() + "'" +
            ", isValid='" + isIsValid() + "'" +
            ", isExpired='" + isIsExpired() + "'" +
            ", createdBy='" + isCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", lastUpdatedOn='" + getLastUpdatedOn() + "'" +
            "}";
    }
}
