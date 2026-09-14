package com.authService.authService.User.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "refreshToken")
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer refresh_id;

    private String refreshToken;

    private Date validTill;

    @OneToOne
    @JoinColumn(name = "userId",nullable = false,unique = true)
    private User user;

    @ElementCollection
    @Column(name = "access_token", length = 2000)
    private List<String> accesstoken;
    public Integer getRefresh_id() {
        return refresh_id;
    }

    public void setRefresh_id(Integer refresh_id) {
        this.refresh_id = refresh_id;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getValidTill() {
        return validTill;
    }

    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(List<String> accesstoken) {
        this.accesstoken = accesstoken;
    }
}
