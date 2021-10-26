package com.privateAPI.DUSTestGenerator.reachability_graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EdgeDirectionDto
{
    private String from;
    private String to;
}
