package za.ac.standardbank.card.repository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import za.ac.standardbank.card.exception.RepositoryException;

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
    public T save(T entity) throws EntityExistsException, RepositoryException{
        try {
            entityManager.persist(entity);
            return entity;
        } catch (PersistenceException e) {
            throw new RepositoryException(e.getMessage(), null);
        }

    }
    @Override
    @Transactional
    public T update(T entity) throws RepositoryException{
        try {
            T updatedEntity = entityManager.merge(entity);
            return updatedEntity;
        } catch (PersistenceException e) {
            throw new RepositoryException(e.getMessage(), null);
        }
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