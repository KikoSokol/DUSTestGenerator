package com.privateAPI.DUSTestGenerator.coverability_tree.domain;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Edge;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import lombok.Data;

import java.util.List;

@Data
public class CoverabilityTree
{
    private List<Vertex> vertices;
    private List<Edge> edges;

    public CoverabilityTree(List<Vertex> vertices, List<Edge> edges)
    {
        this.vertices = vertices;
        this.edges = edges;
    }


}

