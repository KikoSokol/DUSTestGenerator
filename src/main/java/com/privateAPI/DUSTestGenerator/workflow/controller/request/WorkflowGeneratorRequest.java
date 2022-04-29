package com.privateAPI.DUSTestGenerator.workflow.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;

@Data
public class WorkflowGeneratorRequest
{

    @Min(value = 1, message = "Počet miest musí byť minimálne 1")
    private int minPlaces;
    private int maxPlaces;

    @Min(value = 1, message = "Počet miest musí byť minimálne 1")
    private int minTransition;
    private int maxTransition;

    @Nullable
    @Min(value = 0, message = "Počet statických miest musí byť minimálne 0")
    private Integer countStaticPlace;


}
