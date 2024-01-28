package com.w2m.domain.usecase;

import com.w2m.domain.gateway.IGatewayBase;

public interface IUseCaseBase<E,I> {
    IGatewayBase<E,I> getGateway();
}
