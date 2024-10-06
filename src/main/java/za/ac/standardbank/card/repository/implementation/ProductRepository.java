package za.ac.standardbank.card.repository.implementation;

import jakarta.enterprise.context.RequestScoped;
import za.ac.standardbank.card.model.Product;
import za.ac.standardbank.card.repository.BaseRepository;

import java.util.List;

@RequestScoped
public class ProductRepository extends BaseRepository<Product, Long> {

    @Override
    public List<Product> findAll() {
        List<Product> products = getEntityManager().createQuery("SELECT p FROM Product p", Product.class).getResultList();
        return products;
    }

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }

    @Override
    protected Long getEntityId(Product product) {
        return product.getId();
    }
}
