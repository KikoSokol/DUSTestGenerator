package com.privateAPI.DUSTestGenerator.reachability_graph.domain;

import lombok.Data;

import java.util.List;

@Data
public class Edge
{
    private int id;
    private List<EdgeDirection> edgeDirections;
    private int[] markingChange;

}
