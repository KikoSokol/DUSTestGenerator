package com.privateAPI.DUSTestGenerator.coverability_tree.service.impl;

import com.privateAPI.DUSTestGenerator.coverability_tree.controller.request.CoverabilityTreeGeneratorRequest;
import com.privateAPI.DUSTestGenerator.coverability_tree.domain.CoverabilityTree;
import com.privateAPI.DUSTestGenerator.coverability_tree.domain.CoverabilityTreeGeneratorResult;
import com.privateAPI.DUSTestGenerator.coverability_tree.domain.CoverabilityTreeMakerResult;
import com.privateAPI.DUSTestGenerator.coverability_tree.dto.CoverabilityTreeDto;
import com.privateAPI.DUSTestGenerator.coverability_tree.dto.CoverabilityTreeGeneratorResultDto;
import com.privateAPI.DUSTestGenerator.coverability_tree.dto.mapper.CoverabilityTreeMapper;
import com.privateAPI.DUSTestGenerator.coverability_tree.genrator.CoverabilityTreeGenerator;
import com.privateAPI.DUSTestGenerator.coverability_tree.genrator.CoverabilityTreeMaker;
import com.privateAPI.DUSTestGenerator.coverability_tree.service.CoverabilityTreeService;
import com.privateAPI.DUSTestGenerator.coverability_tree.validator.CoverabilityTreeValidator;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.mapper.CoverabilityTreeToPetriNetMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;

@Service
public class CoverabilityTreeServiceImpl implements CoverabilityTreeService
{

    private final CoverabilityTreeGenerator coverabilityTreeGenerator;
    private final CoverabilityTreeMapper coverabilityTreeMapper;
    private final CoverabilityTreeValidator coverabilityTreeValidator;
    private final CoverabilityTreeToPetriNetMapper coverabilityTreeToPetriNetMapper;
    private final CoverabilityTreeMaker coverabilityTreeMaker;

    public CoverabilityTreeServiceImpl(CoverabilityTreeGenerator coverabilityTreeGenerator,
                                       CoverabilityTreeValidator coverabilityTreeValidator,
                                       CoverabilityTreeMaker coverabilityTreeMaker) {
        this.coverabilityTreeGenerator = coverabilityTreeGenerator;
        this.coverabilityTreeMaker = coverabilityTreeMaker;
        this.coverabilityTreeMapper = new CoverabilityTreeMapper();
        this.coverabilityTreeValidator = coverabilityTreeValidator;
        this.coverabilityTreeToPetriNetMapper = new CoverabilityTreeToPetriNetMapper();
    }

    @Override
    public CoverabilityTreeGeneratorResultDto generateCoverabilityTree(CoverabilityTreeGeneratorRequest coverabilityTreeGeneratorRequest)
    {
        ConstraintViolationException exception = this.coverabilityTreeValidator
                .validateCoverabilityTreeGeneratorRequest(coverabilityTreeGeneratorRequest);

        if(exception != null)
            throw exception;

        CoverabilityTreeGeneratorResult coverabilityTree = this.coverabilityTreeGenerator.generateRandomCoverabilityTree(coverabilityTreeGeneratorRequest);

        PetriNetDto petriNetDto = this.coverabilityTreeToPetriNetMapper.calculatePetriNet(coverabilityTree.getCoverabilityTree());

        CoverabilityTreeGeneratorResultDto coverabilityTreeGeneratorResultDto =
                this.coverabilityTreeMapper.toCoverabilityTreeGeneratorResultDto(coverabilityTree);

        coverabilityTreeGeneratorResultDto.setPetriNetDto(petriNetDto);

        return coverabilityTreeGeneratorResultDto;
    }

    public CoverabilityTreeGeneratorResultDto fromPetriNetToCoverabilityTree(PetriNetDto petriNetDto)
    {
        CoverabilityTreeMakerResult coverabilityTree = this.coverabilityTreeMaker.makeCoverabilityTree(petriNetDto);

        CoverabilityTreeDto coverabilityTreeDto = this.coverabilityTreeMapper
                .toCoverabilityTreeDto(coverabilityTree.getCoverabilityTree());


        return new CoverabilityTreeGeneratorResultDto(0, coverabilityTreeDto,
                coverabilityTree.getCoverabilityTreeState());
    }
}
