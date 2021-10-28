package com.privateAPI.DUSTestGenerator.reachability_graph.dto.mapper;

import com.privateAPI.DUSTestGenerator.reachability_graph.domain.Edge;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.EdgeDirection;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.Vertex;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.EdgeDirectionDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.EdgeDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.VertexDto;

import java.util.List;

public class ReachabilityGraphMapper
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
        return new EdgeDto(EDGE_FIRST_WORD + edge.getId(),
                listEdgeDirectionToArrayEdgeDirectionsDto(edge.getEdgeDirections()));
    }

    public ReachabilityGraphDto toReachabilityGraphDto(ReachabilityGraph reachabilityGraph)
    {
        return new ReachabilityGraphDto(convertVertexArrayToVertexDtoArray(reachabilityGraph.getVertices()),
                convertEdgeArrayToEdgeDtoArray(reachabilityGraph.getEdges()));
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

    private VertexDto[] convertVertexArrayToVertexDtoArray(List<Vertex> vertices)
    {
        VertexDto[] vertexDtos = new VertexDto[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            vertexDtos[i] = toVertexDto(vertices.get(i));
        }

        return vertexDtos;
    }

    private EdgeDto[] convertEdgeArrayToEdgeDtoArray(List<Edge> edges)
    {
        EdgeDto[] edgesDtos = new EdgeDto[edges.size()];
        for (int i = 0; i < edges.size(); i++) {
            edgesDtos[i] = toEdgeDto(edges.get(i));
        }

        return edgesDtos;
    }


}
