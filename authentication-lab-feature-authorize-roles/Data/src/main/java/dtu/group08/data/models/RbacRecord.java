package dtu.group08.data.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rbacRecords")
public class RbacRecord implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String userId;
    private String roleName;

    public RbacRecord() {
    }
    
    public RbacRecord(String userId, String role){
        setUserId(userId);
        setRole(role);
    }

    public void setUserId(String userId) {
        this.userId = userId.toUpperCase();
    }

    public void setRole(String role) {
        this.roleName = role;
    }
    
    public String getUserId(){
        return this.userId;
    }
    
    public String getRole(){
        return this.roleName;
    }
    
    public int getId(){
        return this.id;
    }
    
}
