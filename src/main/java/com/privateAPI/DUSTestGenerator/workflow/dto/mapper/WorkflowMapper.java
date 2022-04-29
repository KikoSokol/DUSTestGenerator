package com.privateAPI.DUSTestGenerator.workflow.dto.mapper;

import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.mapper.ReachabilityGraphMapper;
import com.privateAPI.DUSTestGenerator.workflow.domain.WorkflowResult;
import com.privateAPI.DUSTestGenerator.workflow.dto.WorkflowResultDto;

public class WorkflowMapper
{
    private final ReachabilityGraphMapper reachabilityGraphMapper;

    public WorkflowMapper()
    {
        this.reachabilityGraphMapper = new ReachabilityGraphMapper();
    }

    public WorkflowResultDto toWorkflowResultDto(WorkflowResult workflowResult)
    {
        ReachabilityGraphDto reachabilityGraphDto = this.reachabilityGraphMapper
                .toReachabilityGraphDto(workflowResult.getReachabilityGraph());

        return new WorkflowResultDto(workflowResult.getWorkflow(), workflowResult.isCorrect(), reachabilityGraphDto,
                workflowResult.getReachabilityNet());
    }
}
