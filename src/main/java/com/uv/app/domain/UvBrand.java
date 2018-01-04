package com.uv.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UvBrand.
 */
@Entity
@Table(name = "uv_brand")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UvBrand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "brand_code")
    private String brandCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public UvBrand brandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public UvBrand brandCode(String brandCode) {
        this.brandCode = brandCode;
        return this;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
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
        UvBrand uvBrand = (UvBrand) o;
        if (uvBrand.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uvBrand.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UvBrand{" +
            "id=" + getId() +
            ", brandName='" + getBrandName() + "'" +
            ", brandCode='" + getBrandCode() + "'" +
            "}";
    }
}
