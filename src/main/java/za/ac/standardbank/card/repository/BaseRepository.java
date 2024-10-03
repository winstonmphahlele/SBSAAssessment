package za.ac.standardbank.card.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T, ID> implements CRUDRepository<T, ID> {

    @PersistenceContext(unitName = "assessmentChallengePU")
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    @Transactional
    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }
    @Override
    @Transactional
    public T update(T entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        T entity = entityManager.find(getEntityClass(), id);
        return Optional.ofNullable(entity);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        entityManager.remove(entity);
    }


    @Override
    public abstract List<T> findAll();
    // Abstract method to get the entity class type
    protected abstract Class<T> getEntityClass();
}