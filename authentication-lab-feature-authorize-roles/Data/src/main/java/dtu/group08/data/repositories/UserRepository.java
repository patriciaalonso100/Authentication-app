package dtu.group08.data.repositories;

import dtu.group08.data.models.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.persistence.NonUniqueResultException;

public class UserRepository {

    private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User getUser(String id) {
        return entityManager.find(User.class, id);
    }
    
    public List<String> getAll(){
        TypedQuery<String> query = entityManager.createQuery("SELECT u.username FROM User u", String.class);
        return query.getResultList();
    }
    
    
    public User findUserByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.normalizedUsername = :username", User.class);
        query.setParameter("username", username.toUpperCase());

        List<User> users = query.getResultList();
        if (users.isEmpty()) {
            return null;
        } else if (users.size() > 1) {
            throw new NonUniqueResultException();
        } else {
            return users.get(0);
        }
    }

    public void addUser(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        System.out.println("user added " + user.getUsername()+ " id"+ user.getId());
    }

    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Transactional
    public void addToken(SessionToken token) {
        entityManager.getTransaction().begin();
        entityManager.persist(token);
        entityManager.getTransaction().commit();
        System.out.println("token added " + token.getToken());
    }

    public SessionToken findToken(String tokenValue) {
        TypedQuery<SessionToken> query = entityManager.createQuery("SELECT u FROM SessionToken u WHERE u.token = :tokenValue", SessionToken.class);
        query.setParameter("tokenValue", tokenValue);

        List<SessionToken> tokens = query.getResultList();
        if (tokens.isEmpty()) {
            return null;
        } else if (tokens.size() > 1) {
            throw new NonUniqueResultException();
        } else {
            return tokens.get(0);
        }
    }

    @Transactional
    public void deleteUser(String username) {
        User user = findUserByUsername(username);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    public void deleteToken(String tokenValue) {
        SessionToken token = findToken(tokenValue);
        if (token != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(token);
            entityManager.getTransaction().commit();
        }
        System.out.println("token deleted");
    }
    
    public String findUserNameFromToken (String tokenValue) {
        SessionToken token = findToken(tokenValue);
        String userId = token.getUserId();
        return getUser(userId).getUsername();
    }
    
}
