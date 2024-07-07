package dtu.group08.data.interfaces;

import java.util.List;

public interface IAccessControlRepository {
    String getRole(String userId);
    void setRole(String userId, String role);
    void addPermission(String userId, String permission);
    void addPermissions(String userId, String[] permissions);
    List<String> getPermissions(String userId);
    Boolean hasPermission(String userId, String permission);
    void deletePermission(String userId, String permission);
    void deleteUserPermissions(String userId);
}
