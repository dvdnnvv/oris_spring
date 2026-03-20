package com.oris_spring.persistence.repository;

import com.oris_spring.persistence.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public UserEntity save(UserEntity entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        }
        return entityManager.merge(entity);
    }

    public Optional<UserEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(UserEntity.class, id));
    }

    public List<UserEntity> findAllByBirthDateBetween(LocalDate from, LocalDate to) {
        return entityManager.createQuery(
                        "select u from UserEntity u where u.birthDate between :from and :to",
                        UserEntity.class
                )
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();
    }

    public List<UserEntity> findAllByIdInAndBirthDateBetween(Collection<Long> ids, LocalDate from, LocalDate to) {
        return entityManager.createQuery(
                        "select u from UserEntity u where u.id in :ids and u.birthDate between :from and :to",
                        UserEntity.class
                )
                .setParameter("ids", ids)
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();
    }
}
