package com.privateAPI.DUSTestGenerator.p_invariant.validator;

import com.privateAPI.DUSTestGenerator.coverability_tree.controller.request.CoverabilityTreeGeneratorRequest;
import com.privateAPI.DUSTestGenerator.p_invariant.controller.request.InvariantGeneratorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
public class InvariantValidator {
    @Autowired
    private Validator validator;

    public ConstraintViolationException validateInvariantRequest(InvariantGeneratorRequest invariantGeneratorRequest,
                                                                 boolean isTInvariant) {
        Set<ConstraintViolation<InvariantGeneratorRequest>> violations = this.validator.validate(invariantGeneratorRequest);
        StringBuilder sb = new StringBuilder();

        boolean isValid = true;

        if (!violations.isEmpty()) {
            isValid = false;
            for (ConstraintViolation<InvariantGeneratorRequest> violation : violations) {
                sb.append(violation.getMessage());
                sb.append(",\n");
            }
        }

        if(isTInvariant)
        {
            if(!invariantGeneratorRequest.isProperty())
            {
                if(invariantGeneratorRequest.getPlaces() < invariantGeneratorRequest.getTransitions())
                {
                    isValid = false;
                    sb.append("Počet miest nemôže byť menší ako počet prechodov");
                    sb.append(",\n");
                }
            }
        }

        if (isValid)
            return null;
        else {
            if (sb.charAt(sb.length() - 1) == '\n')
                sb.deleteCharAt(sb.length() - 1);

            if (sb.charAt(sb.length() - 1) == ',')
                sb.deleteCharAt(sb.length() - 1);

            return new ConstraintViolationException(sb.toString(), violations);
        }

    }
}
