package ca.sait.lab7v2.dataaccess;

import ca.sait.lab7v2.models.User;
import java.util.List;
import javax.persistence.*;

public class UserDB {

    public List<User> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Query query = em.createNamedQuery("User.finaAll");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public User get(String email) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            User user = em.find(User.class, email);
            return user;
        } finally {
            em.close();
        }
    }

    public boolean insert(User user) throws Exception {
       EntityManager em = DBUtil.getEmFactory().createEntityManager();  //create an entity manager to maintain my entity/record/models/javabean/domai/models...
        EntityTransaction trans = em.getTransaction();  //it must be create a transsaction for doing any DML
        
        try {
            trans.begin();   //begin a transaction in database
            em.persist(user);   //to do persist -- to taking this user object and updating to database
            em.merge(user);     // after an updating, and then do a merge
            // an insert is not gonna change anything in database
            trans.commit();
            
            return true;
        } catch (Exception ex) {
            trans.rollback();  //any exception happens do a rollback
            
            return false;
        } finally { // this finally block call the code in catch and return false
            em.close();
        }
    }

    public boolean update(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
            
            return true;
        } catch (Exception ex) {
            trans.rollback();
            
            return false;
        } finally {    // this finally block call the code in catch and return false
            em.close();
        }
    }

    public boolean delete(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.remove(em.merge(user));  //here the merge will do a select get that user record from database, and then will send to remove method
                                        //the reason to merge is in the UserService, we only set up the email field (user.setEmail(email))
                                        //if we only have the email field,and send that to Entity manager (in the UserDB), it does not know what 
                                        //excatly need to delete
                                        //instead to do a merge(select)
                                        // if do not do merge, it will get error about "entity is not attach"
                                        //because it only has an email ( it is not refer any specific in the database)
                                        //if you have only email field, it will think it jsut have a record with only email column to fill in
            em.merge(user);
            
            /*soft delete
            Query query = em.createNamedQuery("User.softDelete", User.class);
            query.setParameter("email", user.getEmail());
            query.executeUpdate();
            */
            
            trans.commit();
            
            return true;
        } catch (Exception ex) {
            trans.rollback();
            
            return false;
        } finally {
            em.close();
        }
    }

}
