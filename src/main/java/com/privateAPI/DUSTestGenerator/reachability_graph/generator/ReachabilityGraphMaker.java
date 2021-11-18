package com.privateAPI.DUSTestGenerator.reachability_graph.generator;

import com.privateAPI.DUSTestGenerator.reachability_graph.domain.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ReachabilityGraphMaker
{
    public ReachabilityGraphMakerResult makeReachabilityGraph(Vertex firstVertex, List<Edge> edges)
    {
        Map<Integer,Vertex> vertexMap = new HashMap<>();
        List<Integer> examinedVertices = new ArrayList<>();
        int idNewVertex = 1;
        firstVertex = prepareFirstVertex(firstVertex,idNewVertex);
        vertexMap.put(firstVertex.getId(),firstVertex);


        Vertex examine = getUnexaminedVertex(vertexMap,examinedVertices);
        while(examine != null)
        {
            for(Edge edge : edges)
            {
                int[] newMarking = computeNewMarking(examine.getMarking(),edge.getMarkingChange());
                if(!isCorrectMarking(newMarking))
                    continue;

                Vertex vertex = vertexWithTheSomeMarking(vertexMap,newMarking);
                if(vertex == null)
                {
                    idNewVertex++;
                    vertex = new Vertex(idNewVertex,newMarking,createPredecessors(examine));
                }
                else
                {
                    vertex.setPredecessors(createPredecessors(examine,vertex));
                }

                if(!isBounded(vertexMap,vertex))
                    return new ReachabilityGraphMakerResult(ReachabilityGraphState.UNBOUDED,null);

                EdgeDirection edgeDirection = new EdgeDirection(examine,vertex);
                edge.getEdgeDirections().add(edgeDirection);
                vertexMap.put(vertex.getId(),vertex);
            }

            examinedVertices.add(examine.getId());
            examine = getUnexaminedVertex(vertexMap,examinedVertices);
        }


        ReachabilityGraph reachabilityGraph = new ReachabilityGraph(getVertexListFromMap(vertexMap),edges);
        return new ReachabilityGraphMakerResult(ReachabilityGraphState.BOUNDED,reachabilityGraph);

    }

    public ReachabilityGraphMakerResult makeReachabilityGraph(Vertex firstVertex, List<Edge> edges, int maxVertices)
    {
        Map<Integer,Vertex> vertexMap = new HashMap<>();
        List<Integer> examinedVertices = new ArrayList<>();
        int idNewVertex = 1;
        firstVertex = prepareFirstVertex(firstVertex,idNewVertex);
        vertexMap.put(firstVertex.getId(),firstVertex);


        Vertex examine = getUnexaminedVertex(vertexMap,examinedVertices);
        while(examine != null)
        {
            for(Edge edge : edges)
            {
                int[] newMarking = computeNewMarking(examine.getMarking(),edge.getMarkingChange());
                if(!isCorrectMarking(newMarking))
                    continue;

                Vertex vertex = vertexWithTheSomeMarking(vertexMap,newMarking);
                if(vertex == null)
                {
                    idNewVertex++;
                    vertex = new Vertex(idNewVertex,newMarking,createPredecessors(examine));
                }
                else
                {
                    vertex.setPredecessors(createPredecessors(examine,vertex));
                }

                if(!isBounded(vertexMap,vertex))
                    return new ReachabilityGraphMakerResult(ReachabilityGraphState.UNBOUDED,null);

                EdgeDirection edgeDirection = new EdgeDirection(examine,vertex);
                edge.getEdgeDirections().add(edgeDirection);
                vertexMap.put(vertex.getId(),vertex);
            }

            if(getCountVertexInVertexMap(vertexMap) > maxVertices)
            {
                ReachabilityGraph reachabilityGraph = new ReachabilityGraph(getVertexListFromMap(vertexMap),edges);
                return new ReachabilityGraphMakerResult(ReachabilityGraphState.INCOMPLETE,reachabilityGraph);
            }
            examinedVertices.add(examine.getId());
            examine = getUnexaminedVertex(vertexMap,examinedVertices);
        }


        ReachabilityGraph reachabilityGraph = new ReachabilityGraph(getVertexListFromMap(vertexMap),edges);
        return new ReachabilityGraphMakerResult(ReachabilityGraphState.BOUNDED,reachabilityGraph);

    }

    private int[] computeNewMarking(int[] marking, int[] markingChange) {
        int[] newMarking = new int[marking.length];

        for(int i = 0; i < newMarking.length; i++)
        {
            newMarking[i] = marking[i] + markingChange[i];
        }

        return newMarking;
    }


    private boolean isCorrectMarking(int[] marking)
    {
        for(int a : marking)
        {
            if(a < 0)
                return false;
        }
        return true;
    }

    private Vertex vertexWithTheSomeMarking(Map<Integer,Vertex> vertexMap,int[] marking)
    {
        for(Integer id : vertexMap.keySet())
        {
            if(equalMarking(vertexMap.get(id).getMarking(),marking))
                return vertexMap.get(id);
        }
        return null;
    }

    private boolean equalMarking(int[] marking1, int[] marking2)
    {
        for (int i = 0; i < marking1.length; i++)
        {
            if(marking1[i] != marking2[i])
                return false;
        }
        return true;
    }

    private ArrayList<Integer> createPredecessors(Vertex examineVertex)
    {
        Set<Integer> predecessors = new HashSet<>(examineVertex.getPredecessors());
        predecessors.add(examineVertex.getId());
        return new ArrayList<>(predecessors);
    }

    private ArrayList<Integer> createPredecessors(Vertex examineVertex, Vertex destinationVertex)
    {
        Set<Integer> predecessorsSet = new HashSet<>(examineVertex.getPredecessors());
        predecessorsSet.addAll(destinationVertex.getPredecessors());
        predecessorsSet.add(examineVertex.getId());

        return new ArrayList<>(predecessorsSet);
    }

    private boolean isBounded(Map<Integer,Vertex> vertexMap,Vertex vertex)
    {
        for(Integer idVertex : vertex.getPredecessors())
        {
            if(isNewMarkingGreater(vertexMap.get(idVertex).getMarking(),vertex.getMarking()))
                return false;
        }
        return true;
    }

    private boolean isNewMarkingLower(int[] predecessorsMarking, int[] newMarking)
    {
        for (int i = 0; i < predecessorsMarking.length; i++) {
            if(newMarking[i] < predecessorsMarking[i])
                return true;
        }
        return false;
    }

    private boolean isNewMarkingGreater(int[] predecessorsMarking, int[] newMarking)
    {
        if(equalMarking(predecessorsMarking,newMarking))
        {
            return false;
        }
        else return !isNewMarkingLower(predecessorsMarking, newMarking);
    }


    private Vertex getUnexaminedVertex(Map<Integer,Vertex> vertexMap, List<Integer> examinedVertices)
    {
        for(Integer id : vertexMap.keySet())
        {
            if(!examinedVertices.contains(id))
            {
                return vertexMap.get(id);
            }
        }
        return null;
    }

    private List<Vertex> getVertexListFromMap(Map<Integer,Vertex> vertexMap)
    {
        List<Vertex> vertices = new ArrayList<>();

        for(Integer id : vertexMap.keySet())
        {
            vertices.add(vertexMap.get(id));
        }

        return vertices;
    }

    private Vertex prepareFirstVertex(Vertex firstVertex, int id)
    {
        firstVertex.setId(id);
        firstVertex.setPredecessors(new ArrayList<>());
        return firstVertex;
    }

    private int getCountVertexInVertexMap(Map<Integer,Vertex> vertexMap)
    {
        return vertexMap.keySet().size();
    }
}
