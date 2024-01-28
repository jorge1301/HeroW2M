package com.w2m.domain.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IQueryUseCaseBase<E, I> extends IUseCaseBase<E, I> {
    default Optional<E> getById(I id) {
        return getGateway().getById(id);
    }

    default Page<E> findAll(Pageable pageable) {
        return getGateway().findAll(pageable);
    }

    default boolean existsById(I id) { return getGateway().existsById(id); }
}
