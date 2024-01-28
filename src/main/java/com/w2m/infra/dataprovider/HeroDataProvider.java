package com.w2m.infra.dataprovider;

import com.w2m.domain.gateway.IGatewayBase;
import com.w2m.infra.model.Hero;
import com.w2m.infra.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class HeroDataProvider implements IGatewayBase<Hero, Long> {
    @Autowired
    HeroRepository repository;

    @Override
    public JpaRepository<Hero, Long> getRepository() {
        return repository;
    }
}
