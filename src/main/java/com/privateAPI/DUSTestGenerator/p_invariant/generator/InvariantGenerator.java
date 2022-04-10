package com.privateAPI.DUSTestGenerator.p_invariant.generator;


import com.privateAPI.DUSTestGenerator.p_invariant.domain.Invariant;
import com.privateAPI.DUSTestGenerator.p_invariant.domain.InvariantType;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.mapper.IncidentalMatrixToPetriNet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class InvariantGenerator {
    private final InvariantMaker invariantMaker;
    private final IncidentalMatrixToPetriNet incidentalMatrixToPetriNet;

    @Autowired
    public InvariantGenerator(InvariantMaker invariantMaker, IncidentalMatrixToPetriNet incidentalMatrixToPetriNet) {
        this.invariantMaker = invariantMaker;
        this.incidentalMatrixToPetriNet = incidentalMatrixToPetriNet;
    }


    public Invariant PInvariantGeneratorResult() {
        return makeRandomPInvariant(5, 6, false, InvariantType.P);
    }

    public Invariant TInvariantGeneratorResult() {
        return makeRandomPInvariant(5, 6, false, InvariantType.T);
    }

    public Invariant makeRandomPInvariant(int place, int transition, boolean limitedness, InvariantType type) {
        ArrayList<Integer> parametersIndexes = new ArrayList<>();
        double[][] invariant = (limitedness) ?
                invariantMaker.generatePInvariant(place, transition, parametersIndexes) :
                invariantMaker.generateFalsePInvariant(place, transition, parametersIndexes);
        double[][] cMatrix = invariantMaker.calculateMatrixC(transition, invariant, parametersIndexes);
        PetriNetDto petriNet = incidentalMatrixToPetriNet.calculatePetriNet(cMatrix);
        return new Invariant(invariant,petriNet, type);
    }


}
