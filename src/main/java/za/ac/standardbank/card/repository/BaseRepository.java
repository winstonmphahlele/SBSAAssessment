package za.ac.standardbank.card.repository;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import za.ac.standardbank.card.exception.RepositoryException;
import za.ac.standardbank.card.exception.ResourceNotFoundException;

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
    public T update(T entity) throws ResourceNotFoundException, RepositoryException{
        try {

            Optional<T> optionalEntity = findById(getEntityId(entity));
            if (optionalEntity.isEmpty()) {
                throw new ResourceNotFoundException(5004,"Entity not found!", null, null);
            }

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
    protected abstract ID getEntityId(T entity);
}