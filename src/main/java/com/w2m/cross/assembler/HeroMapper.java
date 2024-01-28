package com.w2m.cross.assembler;

import com.w2m.app.dto.HeroDto;
import com.w2m.infra.model.Hero;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HeroMapper {

    HeroDto to(Hero hero);

    Hero from (HeroDto heroDto);
}
