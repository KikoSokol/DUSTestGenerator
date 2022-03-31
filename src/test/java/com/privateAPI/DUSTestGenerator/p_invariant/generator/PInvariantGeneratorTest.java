package com.privateAPI.DUSTestGenerator.p_invariant.generator;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PInvariantGeneratorTest
{

    @Test
    void test()
    {
        PInvariantMaker maker = new PInvariantMaker();
        PInvariantGenerator generator = new PInvariantGenerator(maker);


        for(int i = 0; i < 100; i++){
            System.out.println(generator.PInvariantGeneratorResult());
        }

    }



}