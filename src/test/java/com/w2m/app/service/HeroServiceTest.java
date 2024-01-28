package com.w2m.app.service;

import com.w2m.app.dto.HeroDto;
import com.w2m.app.exception.ConflictException;
import com.w2m.app.exception.NotFoundException;
import com.w2m.cross.assembler.HeroMapper;
import com.w2m.domain.usecase.impl.HeroUseCase;
import com.w2m.infra.model.Hero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HeroServiceTest {
    @InjectMocks
    private HeroService heroService;
    @Mock
    private HeroUseCase heroesUseCase;
    @Mock
    private HeroMapper mapper;

    private HeroDto heroDto;

    private Hero hero;

    @BeforeEach
    void setup() {
        heroDto = HeroDto.builder().name("test").build();
        hero = Hero.builder().name("test").build();
    }

    @Test
    void saveHeroTest() throws ConflictException {
        when(heroesUseCase.existsHeroName(anyString())).thenReturn(Boolean.FALSE);
        when(mapper.from(any())).thenReturn(hero);
        heroService.saveHero(heroDto);
        verify(heroesUseCase).save(any());
    }

    @Test
    void saveHeroTestWithException() {
        when(heroesUseCase.existsHeroName(anyString())).thenReturn(Boolean.TRUE);
        assertThrows(ConflictException.class, () -> heroService.saveHero(heroDto));
        verify(heroesUseCase, times(0)).save(any());
    }

    @Test
    void updateHeroTest() throws NotFoundException {
        when(heroesUseCase.existsById(anyLong())).thenReturn(Boolean.TRUE);
        when(mapper.from(any())).thenReturn(hero);
        heroService.updateHero(1L, heroDto);
        verify(heroesUseCase).save(any());
    }

    @Test
    void updateHeroTestWithException() {
        when(heroesUseCase.existsById(anyLong())).thenReturn(Boolean.FALSE);
        assertThrows(NotFoundException.class, () -> heroService.updateHero(1L, heroDto));
        verify(heroesUseCase, times(0)).save(any());
    }

    @Test
    void getHeroByIdTest() throws NotFoundException {
        when(heroesUseCase.getById(anyLong())).thenReturn(Optional.of(hero));
        when(mapper.to(any())).thenReturn(heroDto);
        assertNotNull(heroService.getHeroById(1L));
    }

    @Test
    void getHeroByIdTestWithException() {
        when(heroesUseCase.getById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> heroService.getHeroById(1L));
    }

    @Test
    void deleteHeroTest() throws NotFoundException {
        when(heroesUseCase.existsById(anyLong())).thenReturn(Boolean.TRUE);
        heroService.deleteHero(1L);
        verify(heroesUseCase).deleteById(anyLong());
    }

    @Test
    void deleteHeroTestWithException() {
        when(heroesUseCase.existsById(anyLong())).thenReturn(Boolean.FALSE);
        assertThrows(NotFoundException.class, () -> heroService.deleteHero(1L));
        verify(heroesUseCase, times(0)).deleteById(anyLong());
    }

    @Test
    void getHeroesTestWithName() {
        Pageable pageable = mock(Pageable.class);
        Page<Hero> heroPage = new PageImpl<>(List.of(hero), pageable, 0);
        when(heroesUseCase.getHeroesPaginationByName(anyString(),any())).thenReturn(heroPage);
        when(mapper.to(any())).thenReturn(heroDto);
        Page<HeroDto> heroes = heroService.getHeroes("test", PageRequest.of(1,1));
        assertNotNull(heroes);
    }

    @Test
    void getHeroesTestWithoutName() {
        Pageable pageable = mock(Pageable.class);
        Page<Hero> heroPage = new PageImpl<>(List.of(hero), pageable, 0);
        when(heroesUseCase.findAll(any())).thenReturn(heroPage);
        when(mapper.to(any())).thenReturn(heroDto);
        Page<HeroDto> heroes = heroService.getHeroes(null, PageRequest.of(1,1));
        assertNotNull(heroes);
    }
}