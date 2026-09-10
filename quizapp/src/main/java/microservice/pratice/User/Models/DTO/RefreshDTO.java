package microservice.pratice.User.Models.DTO;


import javax.xml.crypto.Data;

public class RefreshDTO {

    private String refreshToken;

    private Data validTill;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Data getValidTill() {
        return validTill;
    }

    public void setValidTill(Data validTill) {
        this.validTill = validTill;
    }
}
