package pt.restapi.business.bondary;


import pt.restapi.business.entity.User;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateful
public class UsersManager {

    @PersistenceContext(unitName = "PostgresXADS")
    private EntityManager em;

    public User save(User user) {
        return em.merge(user);
    }


    public User get(int id) {
        return em.find(User.class, id);
    }

    public List<User> getAll() {
        //TODO
        return new ArrayList<>();
    }

    public void remove(int id) throws UserNotFoundException {
        try {
            em.remove(em.getReference(User.class, id));
        }catch (EntityNotFoundException x){
            throw new UserNotFoundException();
        }
    }

}
