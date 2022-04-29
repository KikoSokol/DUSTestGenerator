package com.privateAPI.DUSTestGenerator.workflow.service;

import com.privateAPI.DUSTestGenerator.workflow.controller.request.WorkflowGeneratorRequest;
import com.privateAPI.DUSTestGenerator.workflow.dto.WorkflowResultDto;

import javax.validation.ConstraintViolationException;

public interface WorkflowService
{
    WorkflowResultDto getRandomWorkflow(WorkflowGeneratorRequest workflowGeneratorRequest) throws ConstraintViolationException;
}
