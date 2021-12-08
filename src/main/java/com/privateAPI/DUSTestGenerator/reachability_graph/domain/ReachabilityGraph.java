package com.privateAPI.DUSTestGenerator.reachability_graph.domain;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Edge;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import lombok.Data;


import java.util.List;


@Data
public class ReachabilityGraph
{
    private List<Vertex> vertices;
    private List<Edge> edges;


    public ReachabilityGraph(List<Vertex> vertices, List<Edge> edges) {
        setEdges(edges);
        setVertices(vertices);
    }
}
