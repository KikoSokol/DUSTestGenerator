package com.privateAPI.DUSTestGenerator.petri_nets.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;


@Data
@AllArgsConstructor
public class EdgeDto {
    private String from;
    private String to;
    private int weight;

    public EdgeDto () {
        this.from = "";
        this.to = "";
        this.weight = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeDto edgeDto = (EdgeDto) o;
        return getWeight() == edgeDto.getWeight() && getFrom().equals(edgeDto.getFrom()) && getTo().equals(edgeDto.getTo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFrom(), getTo(), getWeight());
    }
}
