package com.w2m.domain.usecase.impl;

import com.w2m.domain.gateway.IGatewayBase;
import com.w2m.domain.usecase.IMaintenanceUseCaseBase;
import com.w2m.domain.usecase.IQueryUseCaseBase;
import com.w2m.infra.dataprovider.HeroDataProvider;
import com.w2m.infra.model.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class HeroUseCase implements IQueryUseCaseBase<Hero, Long>,
        IMaintenanceUseCaseBase<Hero, Long> {
    @Autowired
    HeroDataProvider dataProvider;

    @Override
    public IGatewayBase<Hero, Long> getGateway() {
        return dataProvider;
    }

    public Page<Hero> getHeroesPaginationByName(String name, Pageable pageable) {
        Hero hero = Hero.builder().name(name).build();
        ExampleMatcher caseInsensitiveMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Hero> example = Example.of(hero, caseInsensitiveMatcher);
        return dataProvider.findAll(example, pageable);
    }

    public boolean existsHeroName(String name) {
        Hero hero = Hero.builder().name(name).build();
        ExampleMatcher caseInsensitiveMatcher = ExampleMatcher.matching()
                .withIgnoreCase();
        Example<Hero> example = Example.of(hero, caseInsensitiveMatcher);
        return dataProvider.exists(example);
    }


}
