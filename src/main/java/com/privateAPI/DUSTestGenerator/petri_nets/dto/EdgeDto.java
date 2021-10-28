package com.privateAPI.DUSTestGenerator.petri_nets.dto;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class EdgeDto {
    private String from;
    private String to;
    private int weight;

    public EdgeDto() {
        this.from = "";
        this.to = "";
        this.weight = 0;
    }
}
