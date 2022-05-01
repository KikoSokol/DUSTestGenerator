package com.privateAPI.DUSTestGenerator.reachability_graph.dto.mapper;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Edge;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.dto.EdgeDto;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.dto.VertexDto;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.dto.mapper.ObjectsForGraphAndTreeMapper;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.*;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.*;

import java.util.List;

public class ReachabilityGraphMapper
{

    private final ObjectsForGraphAndTreeMapper graphAndTreeObjectsMapper;

    public ReachabilityGraphMapper() {
        this.graphAndTreeObjectsMapper = new ObjectsForGraphAndTreeMapper();
    }

    public ReachabilityGraphDto toReachabilityGraphDto(ReachabilityGraph reachabilityGraph)
    {
        return new ReachabilityGraphDto(convertVertexListToVertexDtoArray(reachabilityGraph.getVertices()),
                convertEdgeListToEdgeDtoArray(reachabilityGraph.getEdges()));
    }

    public ReachabilityGraphGeneratorResultDto toReachabilityGraphGeneratorResultDto(ReachabilityGraphGeneratorResult reachabilityGraphGeneratorResult)
    {
        return new ReachabilityGraphGeneratorResultDto(reachabilityGraphGeneratorResult.getCountOfDeletedReachabilityGraphs(),
                toReachabilityGraphDto(reachabilityGraphGeneratorResult.getReachabilityGraph()),
                reachabilityGraphGeneratorResult.getState());
    }


    private VertexDto[] convertVertexListToVertexDtoArray(List<Vertex> vertices)
    {
        VertexDto[] vertexDtos = new VertexDto[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            vertexDtos[i] = graphAndTreeObjectsMapper.toVertexDto(vertices.get(i), true);
        }

        return vertexDtos;
    }

    private EdgeDto[] convertEdgeListToEdgeDtoArray(List<Edge> edges)
    {
        EdgeDto[] edgesDtos = new EdgeDto[edges.size()];
        for (int i = 0; i < edges.size(); i++) {
            edgesDtos[i] = graphAndTreeObjectsMapper.toEdgeDto(edges.get(i));
        }

        return edgesDtos;
    }


}
