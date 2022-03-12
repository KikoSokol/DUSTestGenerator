package com.privateAPI.DUSTestGenerator.petri_nets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class PlaceDto {
    private int numberOfTokens;
    private String id;
    private boolean isStatic;

    public PlaceDto () {
        this.numberOfTokens = 0;
        this.id = "";
        this.isStatic = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceDto placeDto = (PlaceDto) o;
        return getNumberOfTokens() == placeDto.getNumberOfTokens() && isStatic() == placeDto.isStatic() && getId().equals(placeDto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumberOfTokens(), getId(), isStatic());
    }
}
