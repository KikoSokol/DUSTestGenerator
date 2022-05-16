package com.privateAPI.DUSTestGenerator.workflow.dto.mapper;

import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphGeneratorResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.mapper.ReachabilityGraphMapper;
import com.privateAPI.DUSTestGenerator.workflow.domain.ReachabilityNetResult;
import com.privateAPI.DUSTestGenerator.workflow.domain.WorkflowResult;
import com.privateAPI.DUSTestGenerator.workflow.dto.ReachabilityNetResultDto;
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
        ReachabilityGraphGeneratorResultDto reachabilityGraphDto = this.reachabilityGraphMapper
                .toReachabilityGraphGeneratorResultDto(workflowResult.getReachabilityGraph());

        return new WorkflowResultDto(workflowResult.getWorkflow(), workflowResult.isCorrect(), reachabilityGraphDto,
                toReachabilityNetResultDto(workflowResult.getReachabilityNetResult()));
    }

    public ReachabilityNetResultDto toReachabilityNetResultDto(ReachabilityNetResult reachabilityNetResult)
    {
        ReachabilityGraphDto reachabilityGraphDto = this.reachabilityGraphMapper
                .toReachabilityGraphDto(reachabilityNetResult.getReachabilityGraphWithComplementaryPlaces());

        return new ReachabilityNetResultDto(reachabilityNetResult.getWorkflowWithComplementaryPlaces(),
                reachabilityNetResult.getReachabilityNet(),
                reachabilityGraphDto);
    }
}
