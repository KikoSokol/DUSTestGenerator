package com.privateAPI.DUSTestGenerator.reachability_graph.util;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReachabilityGraphParallelPaths that = (ReachabilityGraphParallelPaths) o;
        return getStartVertex().equals(that.getStartVertex()) && getEndVertex().equals(that.getEndVertex());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartVertex(), getEndVertex());
    }
}
