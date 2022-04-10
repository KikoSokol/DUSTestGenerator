package com.privateAPI.DUSTestGenerator.p_invariant.controller;

import com.privateAPI.DUSTestGenerator.p_invariant.controller.request.InvariantGeneratorRequest;
import com.privateAPI.DUSTestGenerator.p_invariant.domain.Invariant;
import com.privateAPI.DUSTestGenerator.p_invariant.generator.InvariantGenerator;
import com.privateAPI.DUSTestGenerator.p_invariant.service.impl.InvariantServiceTest;
import com.privateAPI.DUSTestGenerator.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("invariant")

public class InvariantController {
    private InvariantServiceTest invariantServiceTest;

    public InvariantController(InvariantServiceTest invariantServiceTest) {
        this.invariantServiceTest = invariantServiceTest;
    }


    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("p-invariant")
    public ResponseEntity PInvariantGeneratorResult(@RequestBody InvariantGeneratorRequest invariantGeneratorRequest){
        try {
            Invariant result = this.invariantServiceTest.PInvariantGeneratorResult(invariantGeneratorRequest);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (ConstraintViolationException e)
        {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }
}
