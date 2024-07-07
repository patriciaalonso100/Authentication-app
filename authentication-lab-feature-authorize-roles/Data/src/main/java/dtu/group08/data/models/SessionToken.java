package dtu.group08.data.models;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author tegge
 */
@Entity
@Table(name = "tokens")
public class SessionToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private final String token;
    private LocalDateTime validTo;
    private String userId;

    public SessionToken(String userId) {
        this.token = generateToken();
        this.validTo = LocalDateTime.now();
        this.validTo = validTo.plusMinutes(60);
        this.userId = userId;
    }
    
    public String getUserId () {
        return this.userId;
    }

    public String getToken() {
        return this.token;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    private String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[16];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getEncoder().encodeToString(tokenBytes);
    }
}
