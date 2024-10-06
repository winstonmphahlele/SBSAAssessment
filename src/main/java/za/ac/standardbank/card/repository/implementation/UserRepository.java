package za.ac.standardbank.card.repository.implementation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import za.ac.standardbank.card.model.User;
import za.ac.standardbank.card.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class UserRepository extends BaseRepository<User, Long> {

    public List<User> findAll() {
        List<User> users = getEntityManager().createQuery("SELECT u FROM User u", User.class).getResultList();
        return users;
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    protected Long getEntityId(User user) {
        return user.getId();
    }
}
