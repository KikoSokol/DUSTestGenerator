package com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.dto;

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
