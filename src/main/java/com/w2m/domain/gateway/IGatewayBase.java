package com.w2m.domain.gateway;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IGatewayBase<E, I> {
    JpaRepository<E, I> getRepository();

    default void save(E object) {
        getRepository().saveAndFlush(object);
    }

    default Optional<E> getById(I id) {
        return getRepository().findById(id);
    }

    default boolean existsById(I id) {
        return getRepository().existsById(id);
    }

    default Page<E> findAll(Example<E> example, Pageable pageable) {
        return getRepository().findAll(example, pageable);
    }

    default Page<E> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    default void deleteById(I id) {
        getRepository().deleteById(id);
    }

    default boolean exists(Example<E> example) {
        return getRepository().exists(example);
    }
}
