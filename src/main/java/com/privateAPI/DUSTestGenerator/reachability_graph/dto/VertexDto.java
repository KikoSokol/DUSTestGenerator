package com.privateAPI.DUSTestGenerator.reachability_graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VertexDto
{
    private String id;
    private int[] marking;
    private String[] predecessors;
}
