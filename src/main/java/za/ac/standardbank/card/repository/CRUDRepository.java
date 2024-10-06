package za.ac.standardbank.card.repository;

import za.ac.standardbank.card.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T, ID> {

    T save(T t);
    Optional<T> findById(ID id);
    void delete(T t);
    T update(T t) throws ResourceNotFoundException;
    List<T> findAll();
}
