package com.privateAPI.DUSTestGenerator.coverability_tree.service.impl;

import com.privateAPI.DUSTestGenerator.coverability_tree.controller.request.CoverabilityTreeGeneratorRequest;
import com.privateAPI.DUSTestGenerator.coverability_tree.domain.CoverabilityTreeGeneratorResult;
import com.privateAPI.DUSTestGenerator.coverability_tree.dto.CoverabilityTreeGeneratorResultDto;
import com.privateAPI.DUSTestGenerator.coverability_tree.dto.mapper.CoverabilityTreeMapper;
import com.privateAPI.DUSTestGenerator.coverability_tree.genrator.CoverabilityTreeGenerator;
import com.privateAPI.DUSTestGenerator.coverability_tree.service.CoverabilityTreeService;
import com.privateAPI.DUSTestGenerator.coverability_tree.validator.CoverabilityTreeValidator;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.mapper.ReachabilityGraphToPetriNetMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;

@Service
public class CoverabilityTreeServiceImpl implements CoverabilityTreeService
{

    private final CoverabilityTreeGenerator coverabilityTreeGenerator;
    private final CoverabilityTreeMapper coverabilityTreeMapper;
    private final CoverabilityTreeValidator coverabilityTreeValidator;

    public CoverabilityTreeServiceImpl(CoverabilityTreeGenerator coverabilityTreeGenerator, CoverabilityTreeValidator coverabilityTreeValidator) {
        this.coverabilityTreeGenerator = coverabilityTreeGenerator;
        this.coverabilityTreeMapper = new CoverabilityTreeMapper();
        this.coverabilityTreeValidator = coverabilityTreeValidator;
    }

    @Override
    public CoverabilityTreeGeneratorResultDto generateCoverabilityTree(CoverabilityTreeGeneratorRequest coverabilityTreeGeneratorRequest)
    {
        ConstraintViolationException exception = this.coverabilityTreeValidator
                .validateCoverabilityTreeGeneratorRequest(coverabilityTreeGeneratorRequest);

        if(exception != null)
            throw exception;

        CoverabilityTreeGeneratorResult coverabilityTree = this.coverabilityTreeGenerator.generateRandomCoverabilityTree(coverabilityTreeGeneratorRequest);
        return this.coverabilityTreeMapper.toCoverabilityTreeGeneratorResultDto(coverabilityTree);
    }
}
