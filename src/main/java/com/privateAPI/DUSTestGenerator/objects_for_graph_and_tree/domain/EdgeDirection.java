package com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EdgeDirection
{
    private Vertex from;
    private Vertex to;

}
