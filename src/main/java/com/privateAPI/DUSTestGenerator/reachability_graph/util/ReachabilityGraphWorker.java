package com.privateAPI.DUSTestGenerator.reachability_graph.util;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Edge;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.EdgeDirection;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;

import java.util.*;
import java.util.stream.Collectors;

public class ReachabilityGraphWorker
{

//    private Map<Vertex, Map<Vertex, List<String>>> getNextVerticesMap(ReachabilityGraph reachabilityGraph)
//    {
//        Map<Vertex, Map<Vertex, List<String>>> possibleTransitionTakeFromStaticPlace = new HashMap<>();
//
//        for(Vertex vertex : reachabilityGraph.getVertices())
//        {
//            possibleTransitionTakeFromStaticPlace.put(vertex, getNextVertices(reachabilityGraph, vertex));
//        }
//        printMap(possibleTransitionTakeFromStaticPlace);
//        return possibleTransitionTakeFromStaticPlace;
//    }


    public Map<Vertex, Map<Vertex, List<String>>> getNextVerticesMap(ReachabilityGraph reachabilityGraph)
    {
        Map<Vertex, Map<Vertex, List<String>>> possibleTransitionTakeFromStaticPlace = new HashMap<>();

        for(Vertex vertex : reachabilityGraph.getVertices())
        {
            possibleTransitionTakeFromStaticPlace.put(vertex, getNextVertices(reachabilityGraph, vertex));
        }
        printMap(possibleTransitionTakeFromStaticPlace);
        return possibleTransitionTakeFromStaticPlace;
    }

    private Map<Vertex, List<String>> getNextVertices(ReachabilityGraph reachabilityGraph, Vertex vertex)
    {
        Map<Vertex, List<String>> nextVertices = new HashMap<>();

        for(Edge edge : reachabilityGraph.getEdges())
        {
            for(EdgeDirection edgeDirection : edge.getEdgeDirections())
            {
                if(edgeDirection.getFrom().equals(vertex))
                {
                    if(nextVertices.containsKey(edgeDirection.getTo()))
                        nextVertices.get(edgeDirection.getTo()).add(edge.getId());
                    else
                    {
                        List<String> newEdgeList = new ArrayList<>();
                        newEdgeList.add(edge.getId());
                        nextVertices.put(edgeDirection.getTo(), newEdgeList);
                    }
                }
            }
        }
        return nextVertices;
    }

    public Vertex getEndParallelVertex(Map<Vertex, Map<Vertex, List<String>>> map, Vertex startVertex)
    {
        Map<Vertex, List<Vertex>> path = getParallelPaths(map, startVertex);
        return chooseEndParallelVertex(getSortedVertices(map.keySet()), path);
    }

    public Map<Vertex, List<Vertex>> getParallelPaths(Map<Vertex, Map<Vertex, List<String>>> map, Vertex startVertex)
    {
        Map<Vertex, List<Vertex>> path = new HashMap<>();

        for(Vertex vertex : map.get(startVertex).keySet())
        {
            List<Vertex> vertices = new ArrayList<>();
            vertices.add(vertex);

            getAllVerticesFromVertexToEnd(map, vertices, vertex);
            path.put(vertex, vertices);
        }

        return path;
    }

    private Vertex chooseEndParallelVertex(List<Integer> verticesIds, Map<Vertex, List<Vertex>> parallel)
    {
        List<Vertex> equalsVertex = new ArrayList<>();
        for(Integer i : verticesIds)
        {
            equalsVertex = new ArrayList<>();
            for(Vertex vertex : parallel.keySet())
            {
                equalsVertex.add(isVertexInList(i, parallel.get(vertex)));
            }

            if(!equalsVertex.contains(null))
                return equalsVertex.get(0);
        }

        return equalsVertex.get(0);
    }

    private void getAllVerticesFromVertexToEnd(Map<Vertex, Map<Vertex, List<String>>> map, List<Vertex> vertices,
                                               Vertex vertex)
    {
        Map<Vertex, List<String>> vertexListMap = map.get(vertex);

        if(vertexListMap.size() > 0)
        {
            for(Vertex nextVertex : vertexListMap.keySet())
            {
                if(!vertices.contains(nextVertex))
                    vertices.add(nextVertex);
                getAllVerticesFromVertexToEnd(map, vertices, nextVertex);
            }
        }
    }

    private Vertex isVertexInList(int id, List<Vertex> list)
    {
        for(Vertex vertex : list)
        {
            if(vertex.getId() == id)
                return vertex;
        }
        return null;
    }

    private List<Integer> getSortedVertices(Set<Vertex> vertices)
    {
        return vertices.stream().map(Vertex::getId)
                .sorted(Comparator.comparing(Integer::intValue)).collect(Collectors.toList());
    }



    public List<ReachabilityGraphParallelPaths> getAllParallelPaths(ReachabilityGraph reachabilityGraph)
    {
        Map<Vertex, Map<Vertex, List<String>>> vertexMap = getNextVerticesMap(reachabilityGraph);
        return getAllParallelPaths(reachabilityGraph, vertexMap);
    }

    public List<ReachabilityGraphParallelPaths> getAllParallelPaths(ReachabilityGraph reachabilityGraph, Map<Vertex,
            Map<Vertex, List<String>>> map)
    {
        List<ReachabilityGraphParallelPaths> parallels = new ArrayList<>();

        for(int i = 0; i < reachabilityGraph.getVertices().size(); i++)
        {
            Vertex startVertex = reachabilityGraph.getVertices().get(i);
            if(map.get(startVertex).size() > 1)
            {
                Vertex endVertex = getEndParallelVertex(map, startVertex);
                parallels.add(new ReachabilityGraphParallelPaths(startVertex, endVertex));
                i = reachabilityGraph.getVertices().indexOf(endVertex) - 1;
            }
        }

        return parallels;
    }


    public int getLastIdVertex(ReachabilityGraph reachabilityGraph)
    {
        int count = reachabilityGraph.getVertices().size();

        return getSortedVertices(new HashSet<>(reachabilityGraph.getVertices())).get(count - 1);
    }



    private void printMap(Map<Vertex, Map<Vertex, List<String>>> map)
    {
        for(Vertex from : map.keySet())
        {
            System.out.print(from.getId() + ": {");
            for(Vertex to : map.get(from).keySet())
            {
                System.out.print(to.getId() + ": ");
                for(String s : map.get(from).get(to))
                {
                    System.out.print(s + ",");
                }
                System.out.print("     ");
            }
            System.out.println("}");
        }
    }
}
