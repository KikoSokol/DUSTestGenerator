package com.privateAPI.DUSTestGenerator.p_invariant.generator;


import com.privateAPI.DUSTestGenerator.petri_nets.dto.mapper.IncidentalMatrixToPetriNet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PInvariantGeneratorTest
{

    @Test
    void test()
    {
        InvariantMaker maker = new InvariantMaker();
        IncidentalMatrixToPetriNet incidentalMatrixToPetriNet = new IncidentalMatrixToPetriNet();
        InvariantGenerator generator = new InvariantGenerator(maker, incidentalMatrixToPetriNet);


        for(int i = 0; i < 100; i++){
            System.out.println(generator.PInvariantGeneratorResult());
        }

    }



}