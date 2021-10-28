package com.privateAPI.DUSTestGenerator.reachability_graph.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EdgeDirection
{
    private Vertex from;
    private Vertex to;

}
