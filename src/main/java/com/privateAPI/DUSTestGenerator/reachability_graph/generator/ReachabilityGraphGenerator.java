package com.privateAPI.DUSTestGenerator.reachability_graph.generator;

import com.privateAPI.DUSTestGenerator.reachability_graph.domain.Edge;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.EdgeDirection;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.Vertex;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ReachabilityGraphGenerator {
    public ReachabilityGraph hardcodedGenerateReachabilityGraph() {
        List<Vertex> hardcodedVertices = hardcodedGenerateVertices();
        List<Edge> hardcodedEdges = hardcodedGenerateEdges(hardcodedVertices);
        return new ReachabilityGraph(hardcodedVertices, hardcodedEdges);
    }

    public List<Vertex> hardcodedGenerateVertices() {
        List<Vertex> hardcodedVertices = new ArrayList<>();
        hardcodedVertices.add(new Vertex(0, new int[]{1, 1, 0, 1, 0}));
        hardcodedVertices.add(new Vertex(1, new int[]{0, 0, 2, 1, 0}, new ArrayList<>(Collections.singletonList(0))));
        hardcodedVertices.add(new Vertex(2, new int[]{1, 1, 0, 0, 1}, new ArrayList<>(Arrays.asList(0, 2, 3, 4, 5))));
        hardcodedVertices.add(new Vertex(3, new int[]{0, 0, 2, 0, 1}, new ArrayList<>(Arrays.asList(0, 2, 1))));
        hardcodedVertices.add(new Vertex(4, new int[]{1, 0, 1, 0, 1}, new ArrayList<>(Arrays.asList(0, 2, 3, 1, 8))));
        hardcodedVertices.add(new Vertex(5, new int[]{0, 1, 1, 0, 1}, new ArrayList<>(Arrays.asList(0, 2, 3))));
        hardcodedVertices.add(new Vertex(6, new int[]{2, 0, 0, 0, 1}, new ArrayList<>(Arrays.asList(0, 2, 3, 4, 1, 8, 9))));
        hardcodedVertices.add(new Vertex(7, new int[]{0, 2, 0, 0, 1}, new ArrayList<>(Arrays.asList(0, 2, 3, 5))));
        hardcodedVertices.add(new Vertex(8, new int[]{1, 0, 1, 1, 0}, new ArrayList<>(Arrays.asList(0, 1))));
        hardcodedVertices.add(new Vertex(9, new int[]{2, 0, 0, 1, 0}, new ArrayList<>(Arrays.asList(0, 1, 8))));
        return hardcodedVertices;
    }

    public List<Edge> hardcodedGenerateEdges(List<Vertex> hardcodedVertices) {
        List<Edge> hardcodedEdges = new ArrayList<>();
        hardcodedEdges.add(new Edge(1, new ArrayList<>(Arrays.asList(
                new EdgeDirection(hardcodedVertices.get(0), hardcodedVertices.get(1)),
                new EdgeDirection(hardcodedVertices.get(2), hardcodedVertices.get(3))
        )), new int[]{-1, -1, 2, 0, 0}));
        hardcodedEdges.add(new Edge(2, new ArrayList<>(Arrays.asList(
                new EdgeDirection(hardcodedVertices.get(1), hardcodedVertices.get(8)),
                new EdgeDirection(hardcodedVertices.get(5), hardcodedVertices.get(2)),
                new EdgeDirection(hardcodedVertices.get(3), hardcodedVertices.get(4)),
                new EdgeDirection(hardcodedVertices.get(4), hardcodedVertices.get(6)),
                new EdgeDirection(hardcodedVertices.get(8), hardcodedVertices.get(9))
        )), new int[]{1, 0, -1, 0, 0}));
        hardcodedEdges.add(new Edge(3, new ArrayList<>(Arrays.asList(
                new EdgeDirection(hardcodedVertices.get(4), hardcodedVertices.get(2)),
                new EdgeDirection(hardcodedVertices.get(3), hardcodedVertices.get(5)),
                new EdgeDirection(hardcodedVertices.get(5), hardcodedVertices.get(7))
        )), new int[]{0, 1, -1, 0, 0}));
        hardcodedEdges.add(new Edge(4, new ArrayList<>(Arrays.asList(
                new EdgeDirection(hardcodedVertices.get(0), hardcodedVertices.get(2)),
                new EdgeDirection(hardcodedVertices.get(1), hardcodedVertices.get(3)),
                new EdgeDirection(hardcodedVertices.get(8), hardcodedVertices.get(4)),
                new EdgeDirection(hardcodedVertices.get(9), hardcodedVertices.get(6))
        )), new int[]{0, 0, 0, -1, 1}));
        return hardcodedEdges;
    }

}
