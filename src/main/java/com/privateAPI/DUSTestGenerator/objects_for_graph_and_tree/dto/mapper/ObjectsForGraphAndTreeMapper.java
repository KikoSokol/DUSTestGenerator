package com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.dto.mapper;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Edge;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.EdgeDirection;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.dto.EdgeDirectionDto;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.dto.EdgeDto;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.dto.VertexDto;

import java.util.List;

public class ObjectsForGraphAndTreeMapper
{
    private final String EDGE_FIRST_WORD = "t";
    private final String VERTEX_FIRST_WORD = "m";

    public VertexDto toVertexDto(Vertex vertex)
    {
        return new VertexDto(VERTEX_FIRST_WORD + vertex.getId(), vertex.getMarking(),
                listIntegerPredecessorsToStringArrayPredecessors(vertex.getPredecessors()));
    }

    public EdgeDirectionDto toEdgeDirectionDto(EdgeDirection edgeDirection)
    {
        return new EdgeDirectionDto(VERTEX_FIRST_WORD + edgeDirection.getFrom().getId(),
                VERTEX_FIRST_WORD + edgeDirection.getTo().getId());
    }

    public EdgeDto toEdgeDto(Edge edge)
    {
        String id;

        try {
            id = EDGE_FIRST_WORD + Integer.parseInt(edge.getId());
        }
        catch (NumberFormatException e)
        {
            id = edge.getId();
        }

        return new EdgeDto(id,
                listEdgeDirectionToArrayEdgeDirectionsDto(edge.getEdgeDirections()));
    }

    private String[] listIntegerPredecessorsToStringArrayPredecessors(List<Integer> predecessors)
    {
        String[] stringPredecessors = new String[predecessors.size()];

        for(int i = 0; i < predecessors.size();i++)
        {
            stringPredecessors[i] = VERTEX_FIRST_WORD + predecessors.get(i);
        }

        return stringPredecessors;
    }

    private EdgeDirectionDto[] listEdgeDirectionToArrayEdgeDirectionsDto(List<EdgeDirection> edgeDirections)
    {
        EdgeDirectionDto[] edgeDirectionDtos = new EdgeDirectionDto[edgeDirections.size()];

        for(int i = 0; i < edgeDirections.size();i++)
        {
            edgeDirectionDtos[i] = this.toEdgeDirectionDto(edgeDirections.get(i));
        }

        return edgeDirectionDtos;
    }



}
