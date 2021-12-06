package dev.wittek.tc.jakarta.book;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
@ApplicationScoped
public class BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Book b) {
        entityManager.persist(b);
    }

    public List<Book> findAll() {
        return entityManager
                .createQuery("from " + Book.class.getSimpleName() + " b", Book.class)
                .getResultList();
    }

    public List<Book> findByAuthor(String author) {
        return entityManager
                .createQuery("SELECT b FROM Book b WHERE b.author LIKE :author", Book.class)
                .setParameter("author", author)
                .getResultList();
    }
}
