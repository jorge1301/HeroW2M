package com.w2m.app.rest;

import com.w2m.app.annotations.RuntimeLog;
import com.w2m.app.dto.HeroDto;
import com.w2m.app.exception.ConflictException;
import com.w2m.app.exception.NotFoundException;
import com.w2m.app.service.HeroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RuntimeLog
public class HeroRest {

    @Autowired
    HeroService heroService;

    @GetMapping("/hero/{id}")
    public ResponseEntity<HeroDto> getHero(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(heroService.getHeroById(id));
    }

    @GetMapping("/heroes")
    public ResponseEntity<Page<HeroDto>> getHeroes(
            @RequestParam(required = false) String name,
            Pageable pageable) {
        return ResponseEntity.ok(heroService.getHeroes(name, pageable));
    }

    @PostMapping("/hero")
    public ResponseEntity<HeroDto> createHero(@Valid @RequestBody HeroDto heroDto) throws ConflictException {
        heroService.saveHero(heroDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/hero/{id}")
    public ResponseEntity<HeroDto> deleteHero(@PathVariable Long id) throws NotFoundException {
        heroService.deleteHero(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/hero/{id}")
    public ResponseEntity<HeroDto> updateHero(@PathVariable Long id,
                                              @Validated @RequestBody HeroDto heroDto) throws ConflictException,
            NotFoundException {
        heroService.updateHero(id, heroDto);
        return ResponseEntity.ok().build();
    }
}
