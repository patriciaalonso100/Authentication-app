package dtu.group08.data.models;

import dtu.group08.core.tools.PasswordUtils;
import java.io.Serializable;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    private String id;
    private String username;
    private String normalizedUsername;
    private String password;
    private String name;
    private String salt;

    protected User(){}
    
    public User(String name, String username, String password) {
        id = UUID.randomUUID().toString();
        this.setName(name);
        this.setUsername(username);
        this.setPassword(password);
    }

    public void setId(UUID id){
        this.id = id.toString();
    }
    
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNormalizedUsername() {
        return this.normalizedUsername;
    }

    public void setUsername(String username) {
        this.username = username;
        this.normalizedUsername = username.toUpperCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.salt = PasswordUtils.generateSalt();
        this.password = PasswordUtils.hashPassword(password, this.salt);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalt() {
        return this.salt;
    }
}
