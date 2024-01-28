package com.w2m.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeroDto {
    private Long id;
    @NotBlank
    private String name;
    private String power;
    private Integer age;
    private Boolean isDead;
}
