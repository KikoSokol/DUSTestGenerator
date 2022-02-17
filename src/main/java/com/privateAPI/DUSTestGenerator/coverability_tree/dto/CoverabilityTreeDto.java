package com.privateAPI.DUSTestGenerator.coverability_tree.dto;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.dto.EdgeDto;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.dto.VertexDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoverabilityTreeDto
{
    private VertexDto[] vertices;
    private EdgeDto[] edges;
}
