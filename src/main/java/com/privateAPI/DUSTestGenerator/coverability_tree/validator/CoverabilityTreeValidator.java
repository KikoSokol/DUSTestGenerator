package com.privateAPI.DUSTestGenerator.coverability_tree.validator;

import com.privateAPI.DUSTestGenerator.coverability_tree.controller.request.CoverabilityTreeGeneratorRequest;
import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
public class CoverabilityTreeValidator
{
    @Autowired
    private Validator validator;

    public ConstraintViolationException validateCoverabilityTreeGeneratorRequest(CoverabilityTreeGeneratorRequest coverabilityTreeGeneratorRequest)
    {
        Set<ConstraintViolation<CoverabilityTreeGeneratorRequest>> violations = this.validator.validate(coverabilityTreeGeneratorRequest);
        StringBuilder sb = new StringBuilder();

        boolean isValid = true;

        if(!violations.isEmpty())
        {
            isValid = false;
            for(ConstraintViolation<CoverabilityTreeGeneratorRequest> violation : violations)
            {
                sb.append(violation.getMessage());
                sb.append(",\n");
            }
        }

        if(coverabilityTreeGeneratorRequest.getMaxVertices() < coverabilityTreeGeneratorRequest.getMinVertices())
        {
            isValid = false;
            sb.append("Maximálny počet vrcholov musí byť väčšie číslo ako minimálny počet vrcholov");
            sb.append("\n");
        }

        if(coverabilityTreeGeneratorRequest.getMaxPlaces() < coverabilityTreeGeneratorRequest.getMinPlaces())
        {
            isValid = false;
            sb.append("Maximálny počet miest musí byť väčšie číslo ako minimálny počet miest");
            sb.append("\n");
        }

        if(coverabilityTreeGeneratorRequest.getMaxCountEdges() < coverabilityTreeGeneratorRequest.getMinCountEdges())
        {
            isValid = false;
            sb.append("Maximálny počet prechodov musí byť väčšie číslo ako minimálny počet prechodov");
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
