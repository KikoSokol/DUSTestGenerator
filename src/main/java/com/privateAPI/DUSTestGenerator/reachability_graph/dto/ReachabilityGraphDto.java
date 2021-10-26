package com.privateAPI.DUSTestGenerator.reachability_graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReachabilityGraphDto
{
    private VertexDto[] vertices;
    private EdgeDto[] edges;
}
