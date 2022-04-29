package com.privateAPI.DUSTestGenerator.workflow.validator;

import com.privateAPI.DUSTestGenerator.workflow.controller.request.WorkflowGeneratorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
public class WorkflowValidator
{
    @Autowired
    private Validator validator;

    public ConstraintViolationException validate(WorkflowGeneratorRequest workflowGeneratorRequest)
    {
        Set<ConstraintViolation<WorkflowGeneratorRequest>> violations = this.validator.validate(workflowGeneratorRequest);
        StringBuilder sb = new StringBuilder();

        boolean isValid = true;

        if(!violations.isEmpty())
        {
            isValid = false;
            for(ConstraintViolation<WorkflowGeneratorRequest> violation : violations)
            {
                sb.append(violation.getMessage());
                sb.append(",\n");
            }
        }

        if(workflowGeneratorRequest.getMaxPlaces() < workflowGeneratorRequest.getMinPlaces())
        {
            isValid = false;
            sb.append("Maximálny počet miest musí byť väčšie číslo ako minimálny počet miest");
            sb.append("\n");
        }

        if(workflowGeneratorRequest.getMaxTransition() < workflowGeneratorRequest.getMinTransition())
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
