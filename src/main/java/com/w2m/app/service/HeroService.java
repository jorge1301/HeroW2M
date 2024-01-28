package com.w2m.app.service;

import com.w2m.app.dto.HeroDto;
import com.w2m.app.exception.ConflictException;
import com.w2m.app.exception.NotFoundException;
import com.w2m.cross.assembler.HeroMapper;
import com.w2m.cross.constants.HeroConstants;
import com.w2m.domain.usecase.impl.HeroUseCase;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@CacheConfig(cacheNames = "heroService")
public class HeroService {

    @Autowired
    HeroUseCase heroesUseCase;

    @Autowired
    HeroMapper mapper;

    @CacheEvict(allEntries = true)
    public void saveHero(HeroDto heroDto) throws ConflictException {
        checkHeroName(heroDto);
        heroesUseCase.save(mapper.from(heroDto));
    }

    @CacheEvict(allEntries = true)
    public void updateHero(Long id, HeroDto heroDto) throws NotFoundException {
        checkHeroId(id);
        heroesUseCase.save(mapper.from(heroDto));
    }

    private void checkHeroName(@NonNull HeroDto heroDto) throws ConflictException {
        boolean existHero = heroesUseCase.existsHeroName(heroDto.getName());
        if (existHero) {
            throw new ConflictException(HeroConstants.THE_HERO_ALREADY_EXISTS);
        }
    }

    private void checkHeroId(Long id) throws NotFoundException {
        boolean existHero = heroesUseCase.existsById(id);
        if (!existHero) {
            throw new NotFoundException(HeroConstants.THERE_IS_NO_HERO);
        }
    }

    @Cacheable()
    public HeroDto getHeroById(Long id) throws NotFoundException {
        return mapper.to(heroesUseCase.getById(id).orElseThrow(() ->
                new NotFoundException(HeroConstants.THERE_IS_NO_HERO)));
    }

    @CacheEvict(allEntries = true)
    public void deleteHero(Long id) throws NotFoundException {
        checkHeroId(id);
        heroesUseCase.deleteById(id);
    }

    @Cacheable()
    public Page<HeroDto> getHeroes(String name, Pageable pageable) {
        if (StringUtils.hasText(name)) {
            return heroesUseCase.getHeroesPaginationByName(name, pageable)
                    .map(entity -> mapper.to(entity));
        } else {
            return heroesUseCase.findAll(pageable)
                    .map(entity -> mapper.to(entity));
        }
    }
}
