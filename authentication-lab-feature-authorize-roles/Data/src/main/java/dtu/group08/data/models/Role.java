
package dtu.group08.data.models;
import java.util.List;
import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.*;

@Entity
@Table(name = "roles")

/**
 *
 * @author patri
 */
public class Role implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String nameNormalized;
    @ElementCollection
    private List<String> inheritRoles;
    @ElementCollection
    private List<String> permissions;
    
    public Role () {
        
    }
    
    public Role(String rolename, String[] inheritRoles, String[] permissions)
    {
        this(rolename, Arrays.asList(inheritRoles), Arrays.asList(permissions));
    }
    
    public Role (String rolename, List<String> roles, List<String> permissions){
        this.name = rolename;
        this.inheritRoles = roles;
        this.permissions = permissions;
        this.nameNormalized = name.toUpperCase();
        
    }
    
    public String getName () {
        return this.name;
    }
    
    public List<String> getPermissions () {
        return this.permissions;
    }
    
    public int getId () {
        return id;
    }
    
    public List<String> getInheritRoles() {
        
        return this.inheritRoles;
        
    }
    
}
