package cz.fi.muni.pa165.dto;

/**
 * Data Transfer Object for the User authentication
 * @author Matej Sojak
 */
public class UserAuthDTO {

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
