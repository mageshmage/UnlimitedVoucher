package com.uv.app.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UvSellUnusedVoucher entity.
 */
public class UvSellUnusedVoucherDTO implements Serializable {

    private Long id;

    private String voucherCode;

    private Boolean isValid;

    private Boolean isExpired;

    private String createdBy;

    private LocalDate createdOn;

    private LocalDate lastUpdatedOn;

    private Long brandId;

    private Long categoryId;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Boolean isIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Boolean isIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(LocalDate lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long uvBrandId) {
        this.brandId = uvBrandId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long uvCategoryId) {
        this.categoryId = uvCategoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long uvSellUnusedVoucherUserId) {
        this.userId = uvSellUnusedVoucherUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UvSellUnusedVoucherDTO uvSellUnusedVoucherDTO = (UvSellUnusedVoucherDTO) o;
        if(uvSellUnusedVoucherDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uvSellUnusedVoucherDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UvSellUnusedVoucherDTO{" +
            "id=" + getId() +
            ", voucherCode='" + getVoucherCode() + "'" +
            ", isValid='" + isIsValid() + "'" +
            ", isExpired='" + isIsExpired() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", lastUpdatedOn='" + getLastUpdatedOn() + "'" +
            "}";
    }
}
