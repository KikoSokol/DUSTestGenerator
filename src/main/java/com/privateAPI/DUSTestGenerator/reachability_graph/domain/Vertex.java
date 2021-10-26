package com.privateAPI.DUSTestGenerator.reachability_graph.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Vertex
{
    private int id;
    private int[] marking;
    private List<Integer> predecessors;

    public Vertex(int id, int[] marking)
    {
        this.id = id;
        this.marking = marking;
        this.predecessors = new ArrayList<>();
    }

    public Vertex(int id, int[] marking, ArrayList<Integer> predecessors)
    {
        this.id = id;
        this.marking = marking;
        this.predecessors = predecessors;
    }

    public void addPredecessors(int id)
    {
        this.predecessors.add(id);
    }


}
