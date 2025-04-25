package valuetodays.demo.commons.base;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-29
 */
public abstract class BaseService<I, E, R extends JpaRepository<E, I>> {
    private R repository;

    private EntityManager entityManager;

    public R getRepository() {
        return repository;
    }

    @Inject
    public void setRepository(R repository) {
        this.repository = repository;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Inject
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<E> findAll() {
        return repository.findAll();
    }

    public E save(E entity) {
        return repository.save(entity);
    }
}
