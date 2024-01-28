package com.w2m.domain.usecase;

import jakarta.persistence.PersistenceException;

public interface IMaintenanceUseCaseBase<E, I> extends IUseCaseBase<E, I> {
    default void save(E object) throws PersistenceException {
        getGateway().save(object);
    }

    default void deleteById(I id) {
        getGateway().deleteById(id);
    }
}
