package com.privateAPI.DUSTestGenerator.petri_nets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
public class TransitionDto {
    private String id;
    private String name;

    public TransitionDto () {
        this.id = "";
    }

    public TransitionDto(String id)
    {
        this.id = id;
        this.name = id;
    }

    public TransitionDto(String id, String name)
    {
        this.id = id;
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransitionDto that = (TransitionDto) o;
        return getId().equals(that.getId()) && getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
