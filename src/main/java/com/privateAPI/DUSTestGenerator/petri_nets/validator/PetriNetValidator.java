package com.privateAPI.DUSTestGenerator.petri_nets.validator;

import com.privateAPI.DUSTestGenerator.petri_nets.controller.request.PetriNetGeneratorRequest;
import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
public class PetriNetValidator
{
    @Autowired
    private Validator validator;

    public ConstraintViolationException validatePetriNetGeneratorRequest(PetriNetGeneratorRequest request)
    {
        Set<ConstraintViolation<PetriNetGeneratorRequest>> violations = this.validator.validate(request);
        StringBuilder sb = new StringBuilder();

        boolean isValid = true;

        if(!violations.isEmpty())
        {
            isValid = false;
            for(ConstraintViolation<PetriNetGeneratorRequest> violation : violations)
            {
                sb.append(violation.getMessage());
                sb.append(",\n");
            }
        }


        if(request.getMaxPlaces() < request.getMinPlaces())
        {
            isValid = false;
            sb.append("Maximálny počet miest musí byť väčšie číslo ako minimálny počet miest");
            sb.append("\n");
        }

        if(request.getMaxTransition() < request.getMinTransition())
        {
            isValid = false;
            sb.append("Maximálny počet prechodov musí byť väčšie číslo ako minimálny počet prechodov");
            sb.append("\n");
        }

        if(request.getMaxEdges() < request.getMinEdges())
        {
            isValid = false;
            sb.append("Maximálny počet hrán musí byť väčšie číslo ako minimálny počet hrán");
            sb.append("\n");
        }

        if(request.getMaxToken() < request.getMinToken())
        {
            isValid = false;
            sb.append("Maximálny počet tokenov musí byť väčšie číslo ako minimálny počet tokenov");
            sb.append("\n");
        }

        if(request.getMaxEdgeWeight() < request.getMinEdgeWeight())
        {
            isValid = false;
            sb.append("Maximálny váha hrány musí byť väčšie číslo ako minimálna váha hrany");
            sb.append("\n");
        }


        if(isValid)
            return null;
        else
        {
            if(sb.charAt(sb.length() - 1) == '\n')
                sb.deleteCharAt(sb.length() - 1);

            if(sb.charAt(sb.length() - 1) == ',')
                sb.deleteCharAt(sb.length() - 1);

            return new ConstraintViolationException(sb.toString(), violations);
        }

    }
}
