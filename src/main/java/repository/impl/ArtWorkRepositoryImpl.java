package repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import model.ArtWork;
import repository.ArtWorkRepository;
import util.EntityManagerProvider;

import java.util.List;
import java.util.Optional;

public class ArtWorkRepositoryImpl implements ArtWorkRepository {
    private final EntityManagerProvider emp;
    public ArtWorkRepositoryImpl(EntityManagerProvider emp) {
        this.emp = emp;
    }

    @Override
    public void save(ArtWork artWork) {
        EntityManager entityManager = emp.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(artWork);
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<ArtWork> findById(Long id) {
        try {
            return Optional.ofNullable(emp.getEntityManager().find(ArtWork.class, id));

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ArtWork> findByArtistName(String artistName) {
        return EntityManagerProvider.getEntityManager()
                .createQuery("select a from ArtWork a where a.artist like %artist", ArtWork.class).getResultList();
//        try {
//            return Optional.ofNullable(emp.getEntityManager().find(Orders.class,customerName));
//
//        } catch (Exception e) {
//            return Optional.empty();
//        }
    }

    @Override
    public List<ArtWork> findAll() {
        return EntityManagerProvider.getEntityManager()
                .createQuery("select a from ArtWork a ", ArtWork.class).getResultList();
    }

    @Override
    public void updateArtistById(Long id, String name) {
        EntityManager entityManager = emp.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            ArtWork artWork = entityManager.find(ArtWork.class, id);
            if (artWork != null) {
                transaction.begin();
                artWork.setArtist(name);
                entityManager.merge(artWork);
                transaction.commit();
            } else
                throw new EntityNotFoundException();

        } catch (Exception e) {
            transaction.rollback();

        } finally {
            entityManager.close();
        }
    }

    @Override
    public void updatePriceById(Long id, Double price) {
        EntityManager entityManager = emp.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            ArtWork artWork = entityManager.find(ArtWork.class, id);
            if (artWork != null) {
                transaction.begin();
                artWork.setPrice(price);
                entityManager.merge(artWork);
                transaction.commit();
            } else
                throw new EntityNotFoundException();

        } catch (Exception e) {
            transaction.rollback();

        } finally {
            entityManager.close();
        }

    }

    @Override
    public void removeById(Long id) {
        EntityManager entityManager = emp.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            ArtWork artWork = entityManager.find(ArtWork.class, id);
            if (artWork != null) {
                transaction.begin();
                entityManager.remove(artWork);
                transaction.commit();
            } else
                throw new EntityNotFoundException();

        } catch (Exception e) {
            transaction.rollback();

        } finally {
            entityManager.close();

        }
    }
}

