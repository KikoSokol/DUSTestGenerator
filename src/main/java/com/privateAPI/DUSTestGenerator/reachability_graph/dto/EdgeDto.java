package com.privateAPI.DUSTestGenerator.reachability_graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EdgeDto
{
    private String id;
    private EdgeDirectionDto[] edgeDirections;
}
