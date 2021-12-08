package com.privateAPI.DUSTestGenerator.coverability_tree.dto.mapper;

import com.privateAPI.DUSTestGenerator.coverability_tree.domain.CoverabilityTree;
import com.privateAPI.DUSTestGenerator.coverability_tree.dto.CoverabilityTreeDto;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Edge;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.dto.EdgeDto;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.dto.VertexDto;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.dto.mapper.ObjectsForGraphAndTreeMapper;

import java.util.List;

public class CoverabilityTreeMapper
{
    private final ObjectsForGraphAndTreeMapper graphAndTreeObjectsMapper;

    public CoverabilityTreeMapper()
    {
        this.graphAndTreeObjectsMapper = new ObjectsForGraphAndTreeMapper();
    }

    public CoverabilityTreeDto toCoverabilityTreeDto(CoverabilityTree coverabilityTree)
    {
        return new CoverabilityTreeDto(convertVertexListToVertexDtoArray(coverabilityTree.getVertices()),
                convertEdgeListToEdgeDtoArray(coverabilityTree.getEdges()));
    }


    private VertexDto[] convertVertexListToVertexDtoArray(List<Vertex> vertices)
    {
        VertexDto[] vertexDtos = new VertexDto[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            vertexDtos[i] = graphAndTreeObjectsMapper.toVertexDto(vertices.get(i));
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
