package com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class  Edge
{
    private int id;
    private List<EdgeDirection> edgeDirections;
    private int[] markingChange;

}
