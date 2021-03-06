package org.app.repository;

import org.app.model.CarEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CarRepositoryImpl implements CarRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public CarEntity save(CarEntity car) {
        if(car.getId() != null){
            return entityManager.merge(car);
        }else {
            entityManager.persist(car);
            return car;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CarEntity> getAll() {
        CriteriaQuery<CarEntity> criteria = entityManager.getCriteriaBuilder().createQuery(CarEntity.class);
        criteria.select(criteria.from(CarEntity.class));
//        criteria.where( builder.equal( root.get( Person_.name ), "John Doe" ) );

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public CarEntity getById(long id) {
        return entityManager.find(CarEntity.class, id);
    }

    @Override
    @Transactional
    public void remove(long id) {
        CarEntity car = getById(id);
        if(car != null) {
            entityManager.remove(car);
        }
    }
}
