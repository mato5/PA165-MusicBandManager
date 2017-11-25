package cz.fi.muni.pa165.dto;

/**
 * @author Matej Sojak 433294
 */
public class UserChangePasswordDTO {

    private Long userId;
    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
