package com.uv.app.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UvSellUnusedVoucherUser entity.
 */
public class UvSellUnusedVoucherUserDTO implements Serializable {

    private Long id;

    private String userName;

    private String password;

    private String email;

    private Boolean isVerifiedUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isIsVerifiedUser() {
        return isVerifiedUser;
    }

    public void setIsVerifiedUser(Boolean isVerifiedUser) {
        this.isVerifiedUser = isVerifiedUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UvSellUnusedVoucherUserDTO uvSellUnusedVoucherUserDTO = (UvSellUnusedVoucherUserDTO) o;
        if(uvSellUnusedVoucherUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uvSellUnusedVoucherUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UvSellUnusedVoucherUserDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            ", isVerifiedUser='" + isIsVerifiedUser() + "'" +
            "}";
    }
}
