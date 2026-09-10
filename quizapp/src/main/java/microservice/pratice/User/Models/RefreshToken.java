package microservice.pratice.User.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.xml.crypto.Data;
import java.util.Date;

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
}
