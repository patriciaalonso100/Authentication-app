
package dtu.group08.data.models;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "aclRecords")
public class AclRecord implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String userId;
    private String permission;

    public AclRecord() {
    }
    
    public AclRecord (String username, String permission){
        setUsername(username);
        setPermission(permission);
    }
    
    public void setUsername(String username){
        this.userId = username.toUpperCase();
    }
    
    public String getUsername()
    {
        return this.userId;
    }
    
    public String getPermission () {
        return this.permission;
    }
    
    public void setPermission(String permission){
        this.permission = permission;
    }
    
    public int getId () {
        return id;
    }
    
}
