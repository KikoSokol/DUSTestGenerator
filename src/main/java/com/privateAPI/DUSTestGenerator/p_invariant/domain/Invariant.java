package com.privateAPI.DUSTestGenerator.p_invariant.domain;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;

@Data
@AllArgsConstructor

public class Invariant {
    private double[][] invariant;
    private PetriNetDto petriNet;
    private InvariantType invariantType;

    @Override
    public String toString() {
        return "Invariant{" +
                "invariant=" + Arrays.toString(invariant) +
                ", petriNet=" + petriNet +
                ", invariantType=" + invariantType +
                '}';
    }
}
