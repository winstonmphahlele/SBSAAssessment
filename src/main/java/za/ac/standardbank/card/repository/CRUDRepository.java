package za.ac.standardbank.card.repository;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T, ID> {

    T save(T t);
    Optional<T> findById(ID id);
    void delete(T t);
    T update(T t);
    List<T> findAll();
}
