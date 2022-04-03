package com.privateAPI.DUSTestGenerator.reachability_graph.util;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;

public class ReachabilityGraphParallelPaths
{
    private Vertex startVertex;
    private Vertex endVertex;

    public ReachabilityGraphParallelPaths(Vertex startVertex, Vertex endVertex) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    public Vertex getStartVertex() {
        return startVertex;
    }

    public void setStartVertex(Vertex startVertex) {
        this.startVertex = startVertex;
    }

    public Vertex getEndVertex() {
        return endVertex;
    }

    public void setEndVertex(Vertex endVertex) {
        this.endVertex = endVertex;
    }
}
