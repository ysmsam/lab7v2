package ca.sait.lab7v2.services;

import ca.sait.lab7v2.dataaccess.RoleDB;
import ca.sait.lab7v2.dataaccess.UserDB;
import ca.sait.lab7v2.models.Role;
import ca.sait.lab7v2.models.User;
import java.util.List;

public class RoleService {
    private RoleDB roleDB = new RoleDB();
    
    public List<Role> getAll() throws Exception {
        List<Role> roles = this.roleDB.getAll();
        return roles;
    }
}
