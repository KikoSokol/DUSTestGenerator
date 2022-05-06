package com.privateAPI.DUSTestGenerator.test_generator.service;

import com.privateAPI.DUSTestGenerator.coverability_tree.controller.request.CoverabilityTreeGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.controller.request.PetriNetGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.DefinitionPTIOM0;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PrescriptionPnDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import com.privateAPI.DUSTestGenerator.test_generator.dto.GraphAndTreeTaskDto;
import com.privateAPI.DUSTestGenerator.workflow.controller.request.WorkflowGeneratorRequest;
import com.privateAPI.DUSTestGenerator.workflow.dto.WorkflowResultDto;

import javax.validation.ConstraintViolationException;

public interface MainTestService
{
    GraphAndTreeTaskDto getReachabilityGraph(ReachabilityGraphGeneratorRequest request) throws ConstraintViolationException;
    GraphAndTreeTaskDto getCoverabilityTree(CoverabilityTreeGeneratorRequest request) throws ConstraintViolationException;
    PrescriptionPnDto getPrescriptionPN(PetriNetGeneratorRequest petriNetGeneratorRequest) throws ConstraintViolationException;
    DefinitionPTIOM0 getDefinitionPTIOM0(PetriNetGeneratorRequest petriNetGeneratorRequest) throws ConstraintViolationException;
    WorkflowResultDto getRandomWorkflow(WorkflowGeneratorRequest workflowGeneratorRequest) throws ConstraintViolationException;
    WorkflowResultDto getRandomCorrectWorkflow(WorkflowGeneratorRequest workflowGeneratorRequest) throws ConstraintViolationException;
}