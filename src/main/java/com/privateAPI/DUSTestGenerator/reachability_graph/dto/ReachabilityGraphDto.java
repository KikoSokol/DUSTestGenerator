package com.privateAPI.DUSTestGenerator.reachability_graph.dto;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.dto.EdgeDto;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.dto.VertexDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReachabilityGraphDto
{
    private VertexDto[] vertices;
    private EdgeDto[] edges;
}
