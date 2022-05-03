package com.privateAPI.DUSTestGenerator.workflow.generator;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;

import java.util.List;
import java.util.Map;

public class StaticPlaceConnector
{
    private final Vertex vertex;
    private final Map<Vertex, List<String>> nextVertices;
    private final Vertex afterVertex;
    private final List<String> transitionsConnectedWithStaticPlace;

    public StaticPlaceConnector(Vertex vertex, Map<Vertex, List<String>> nextVertices, Vertex afterVertex, List<String> transitionsConnectedWithStaticPlace) {
        this.vertex = vertex;
        this.nextVertices = nextVertices;
        this.afterVertex = afterVertex;
        this.transitionsConnectedWithStaticPlace = transitionsConnectedWithStaticPlace;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public Map<Vertex, List<String>> getNextVertices() {
        return nextVertices;
    }

    public Vertex getAfterVertex() {
        return afterVertex;
    }

    public List<String> getTransitionsConnectedWithStaticPlace() {
        return transitionsConnectedWithStaticPlace;
    }
}
