package com.privateAPI.DUSTestGenerator.coverability_tree.controller;

import com.privateAPI.DUSTestGenerator.coverability_tree.controller.request.CoverabilityTreeGeneratorRequest;
import com.privateAPI.DUSTestGenerator.coverability_tree.dto.CoverabilityTreeGeneratorResultDto;
import com.privateAPI.DUSTestGenerator.coverability_tree.service.CoverabilityTreeService;
import com.privateAPI.DUSTestGenerator.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("coverability-tree")
public class CoverabilityTreeController
{
    @Autowired
    private CoverabilityTreeService coverabilityTreeService;

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("generator")
    public ResponseEntity getRandomCoverabilityTree(@RequestBody CoverabilityTreeGeneratorRequest coverabilityTreeGeneratorRequest)
    {
        try
        {
            CoverabilityTreeGeneratorResultDto coverabilityTree = this.coverabilityTreeService
                    .generateCoverabilityTree(coverabilityTreeGeneratorRequest);
            return new ResponseEntity<>(coverabilityTree, HttpStatus.OK);
        }
        catch (ConstraintViolationException e)
        {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
