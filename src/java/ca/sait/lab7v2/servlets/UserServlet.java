package ca.sait.lab7v2.servlets;

import ca.sait.lab7v2.models.Role;
import ca.sait.lab7v2.models.User;
import ca.sait.lab7v2.services.RoleService;
import ca.sait.lab7v2.services.UserService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sheng Ming Yan
 */
public class UserServlet extends HttpServlet {
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService service = new UserService();
        RoleService serviceRole = new RoleService();
        User user;
        List<User> users = null;
        String action = request.getParameter("action");
        
        if(action != null && action.equals("delete")){       
            try {
                String email = request.getParameter("email").replace(" ", "+");
                
                boolean deleted = service.delete(email);
                
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        action = request.getParameter("action");
        if (action != null && action.equals("update")) {
            try {
                String email = request.getParameter("email").replace(" ", "+");
                user = service.get(email);
                request.setAttribute("userEdit", user);
//                String activeChar = user.getActive()==true ? "Y":"N";

            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            
            users = service.getAll();
            List<Role> roles = serviceRole.getAll();
            
            request.setAttribute("users", users);
            
//            this.getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        UserService service = new UserService();
        RoleService serviceRole = new RoleService();
        
        Role role = new Role();
//        List<Role> roles = serviceRole.getAll();

        String action = request.getParameter("action");

        String password = "password";
        
        try {
            
            if(action != null && action.equals("create")){
                String newEmail = request.getParameter("newEmail");
                boolean newActive = (request.getParameter("newActive") == "Y");
                String newFirstName = request.getParameter("newFirstName");
                String newLastName = request.getParameter("newLastName");
                int newRole = Integer.parseInt(request.getParameter("newRole"));
                boolean inserted = service.insert(newEmail, newActive, newFirstName, newLastName, password, newRole);
            }
            
            if(action != null && action.equals("update")){
                String editEmail = request.getParameter("editEmail");
                boolean editActive = ("true".equals(request.getParameter("editActive")) || "Y".equals(request.getParameter("editActive")));
                String editFirstName = request.getParameter("editFirstName");
                String editLastName = request.getParameter("editLastName");
                int editRoleID = Integer.parseInt(request.getParameter("editRoleID"));
                boolean updated = service.update(editEmail, editActive, editFirstName, editLastName, password, editRoleID);
            }

        } catch (Exception ex) {
             Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
//            request.setAttribute("message", "error");
        }

        try {
            List<User> users = service.getAll();
            
            request.setAttribute("users", users);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
//            request.setAttribute("message", "error");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        
    }
}
