package com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public Vertex(Vertex vertex)
    {
        this.id = vertex.getId();
        this.marking = vertex.getMarking();
        this.predecessors = vertex.getPredecessors();
    }

    public void addPredecessors(int id)
    {
        this.predecessors.add(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return getId() == vertex.getId() && Arrays.equals(getMarking(), vertex.getMarking()) && getPredecessors().equals(vertex.getPredecessors());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getPredecessors());
        result = 31 * result + Arrays.hashCode(getMarking());
        return result;
    }
}
