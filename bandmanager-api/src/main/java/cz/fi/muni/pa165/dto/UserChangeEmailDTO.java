package cz.fi.muni.pa165.dto;

/**
 * @author Matej Sojak 433294
 */
public class UserChangeEmailDTO {

    private Long userId;
    private String newEmail;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
