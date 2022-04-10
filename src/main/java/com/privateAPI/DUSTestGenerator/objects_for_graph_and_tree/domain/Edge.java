package com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Edge
{
    private String id;
    private List<EdgeDirection> edgeDirections;
    private int[] markingChange;

    public Edge(int id, List<EdgeDirection> edgeDirections, int[] markingChange) {
        this.id = Integer.toString(id);
        this.edgeDirections = edgeDirections;
        this.markingChange = markingChange;
    }
}
