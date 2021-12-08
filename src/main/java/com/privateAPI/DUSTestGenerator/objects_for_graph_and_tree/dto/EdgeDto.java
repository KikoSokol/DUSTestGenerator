package com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EdgeDto
{
    private String id;
    private EdgeDirectionDto[] edgeDirections;
}
