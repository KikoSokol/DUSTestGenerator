package com.privateAPI.DUSTestGenerator.petri_nets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransitionDto {
    private String id;

    public TransitionDto () {
        this.id = "";
    }
}
