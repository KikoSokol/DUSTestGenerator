package com.privateAPI.DUSTestGenerator.p_invariant.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
public class InvariantGeneratorRequest {
    @Min(value = 3, message = "Minimálny počet miest musí byť väčší ako 3")
    @Max(value = 25, message = "Maximálny počet miest musí byť menší ako 25")
    private int places;
    @Min(value = 3, message = "Minimálny počet prechodov musí byť väčší ako 3")
    @Max(value = 25, message = "Maximálny počet prechodov musí byť menší ako 25")
    private int transitions;
    private boolean property;


}
