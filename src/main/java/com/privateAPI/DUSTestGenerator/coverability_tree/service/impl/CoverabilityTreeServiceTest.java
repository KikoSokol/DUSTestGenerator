package com.privateAPI.DUSTestGenerator.coverability_tree.service.impl;

import com.privateAPI.DUSTestGenerator.coverability_tree.domain.CoverabilityTree;
import com.privateAPI.DUSTestGenerator.coverability_tree.dto.CoverabilityTreeDto;
import com.privateAPI.DUSTestGenerator.coverability_tree.dto.mapper.CoverabilityTreeMapper;
import com.privateAPI.DUSTestGenerator.coverability_tree.genrator.CoverabilityTreeMaker;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Edge;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoverabilityTreeServiceTest
{
    private final CoverabilityTreeMaker coverabilityTreeMaker;
    private final CoverabilityTreeMapper coverabilityTreeMapper;

    public CoverabilityTreeServiceTest(CoverabilityTreeMaker coverabilityTreeMaker) {
        this.coverabilityTreeMaker = coverabilityTreeMaker;
        this.coverabilityTreeMapper = new CoverabilityTreeMapper();
    }


    public CoverabilityTreeDto getSample1()
    {
        Vertex firstVertex = new Vertex(1, new int[]{2});

        Edge a = new Edge(1, new ArrayList<>(), new int[]{3});
        Edge b = new Edge(2, new ArrayList<>(), new int[]{1});
        Edge c = new Edge(3, new ArrayList<>(), new int[]{-4});

        List<Edge> edges = new ArrayList<>();
        edges.add(a);
        edges.add(b);
        edges.add(c);

        CoverabilityTree coverabilityTree = this.coverabilityTreeMaker.makeCoverabilityTree(firstVertex, edges);

        return this.coverabilityTreeMapper.toCoverabilityTreeDto(coverabilityTree);

    }

    public CoverabilityTreeDto getSample2()
    {
        Vertex firstVertex = new Vertex(1, new int[]{1,0,0});

        Edge a = new Edge(1, new ArrayList<>(), new int[]{-1, 1, 1});
        Edge b = new Edge(2, new ArrayList<>(), new int[]{1, -1, 0});


        List<Edge> edges = new ArrayList<>();
        edges.add(a);
        edges.add(b);

        CoverabilityTree coverabilityTree = this.coverabilityTreeMaker.makeCoverabilityTree(firstVertex, edges);

        return this.coverabilityTreeMapper.toCoverabilityTreeDto(coverabilityTree);
    }
}
