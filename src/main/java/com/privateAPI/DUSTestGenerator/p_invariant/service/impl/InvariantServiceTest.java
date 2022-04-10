package com.privateAPI.DUSTestGenerator.p_invariant.service.impl;

import com.privateAPI.DUSTestGenerator.p_invariant.controller.request.InvariantGeneratorRequest;
import com.privateAPI.DUSTestGenerator.p_invariant.domain.Invariant;
import com.privateAPI.DUSTestGenerator.p_invariant.domain.InvariantType;
import com.privateAPI.DUSTestGenerator.p_invariant.generator.InvariantGenerator;
import com.privateAPI.DUSTestGenerator.p_invariant.validator.InvariantValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;

@Service
public class InvariantServiceTest {
    private final InvariantGenerator invariantGenerator;
    private final InvariantValidator invariantValidator;

    @Autowired
    public InvariantServiceTest(InvariantGenerator invariantGenerator, InvariantValidator invariantValidator) {
        this.invariantGenerator = invariantGenerator;
        this.invariantValidator = invariantValidator;
    }

    public Invariant PInvariantGeneratorResult(InvariantGeneratorRequest invariantGeneratorRequest) {
        ConstraintViolationException exception = this.invariantValidator
                .validateInvariantRequest(invariantGeneratorRequest);

        if(exception != null)
            throw exception;

        return invariantGenerator.makeRandomPInvariant(invariantGeneratorRequest.getPlaces(),
                invariantGeneratorRequest.getTransitions(), invariantGeneratorRequest.isProperty(), InvariantType.P);

    }
}
