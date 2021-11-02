package com.privateAPI.DUSTestGenerator.reachability_graph.service.impl;

import com.privateAPI.DUSTestGenerator.reachability_graph.domain.Edge;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.Vertex;

import java.util.ArrayList;
import java.util.List;

public class ReachabilityGraphServiceTest {

    public ReachabilityGraph test1(){
        Vertex vertex1 = new Vertex(1, new int[]{3,2,0,0,0});
        List<Edge> edges1 = new ArrayList<>();
        edges1.add(new Edge(1, new ArrayList<>(), new int[]{-1,0,1,0,0}));
        edges1.add(new Edge(2, new ArrayList<>(), new int[]{0,0,-1,1,0}));
        edges1.add(new Edge(3, new ArrayList<>(), new int[]{0,0,0,-1,2}));
        return null;
    }

    public ReachabilityGraph test2(){
        Vertex vertex2 = new Vertex(1, new int[]{3,2,0,0,0});
        List<Edge> edges2 = new ArrayList<>();
        edges2.add(new Edge(1, new ArrayList<>(), new int[]{-1,-1,1,0,0}));
        edges2.add(new Edge(2, new ArrayList<>(), new int[]{0,0,-1,1,0}));
        edges2.add(new Edge(3, new ArrayList<>(), new int[]{0,0,0,-1,2}));
        return null;
    }

    public ReachabilityGraph test3(){
        Vertex vertex3 = new Vertex(1, new int[]{3,2,0,0,0});
        List<Edge> edges3 = new ArrayList<>();
        edges3.add(new Edge(1, new ArrayList<>(), new int[]{-1,1,1,0,0}));
        edges3.add(new Edge(2, new ArrayList<>(), new int[]{0,0,-1,1,0}));
        edges3.add(new Edge(3, new ArrayList<>(), new int[]{0,0,0,-1,2}));
        return null;
    }

    public ReachabilityGraph test4(){
        Vertex vertex4 = new Vertex(1, new int[]{3,2,0,0,0});
        List<Edge> edges4 = new ArrayList<>();
        edges4.add(new Edge(1, new ArrayList<>(), new int[]{-1,0,1,0,0}));
        edges4.add(new Edge(2, new ArrayList<>(), new int[]{0,0,-1,1,0}));
        edges4.add(new Edge(3, new ArrayList<>(), new int[]{0,0,0,-1,2}));
        return null;
    }
}
