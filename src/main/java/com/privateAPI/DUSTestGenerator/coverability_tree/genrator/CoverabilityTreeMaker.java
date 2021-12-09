package com.privateAPI.DUSTestGenerator.coverability_tree.genrator;

import com.privateAPI.DUSTestGenerator.coverability_tree.domain.CoverabilityTree;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Edge;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.EdgeDirection;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CoverabilityTreeMaker
{
    public CoverabilityTree makeCoverabilityTree(Vertex firstVertex, List<Edge> edges)
    {
        Map<Integer, Vertex> vertexMap = new HashMap<>();
        List<Integer> examinedVertices = new ArrayList<>();

        int idNewVertex = 1;
        firstVertex = prepareFirstVertex(firstVertex,idNewVertex);
        vertexMap.put(firstVertex.getId(), firstVertex);

        Vertex examine = getUnexaminedVertex(vertexMap, examinedVertices);
        while(examine != null)
        {
            if(!existsEqualPredecessor(examine, vertexMap))
            {
                for(Edge edge : edges)
                {
                    int[] newMarking = computeNewMarking(examine.getMarking(), edge.getMarkingChange());
                    if(!isCorrectMarking(examine.getMarking(), newMarking))
                    {
                        continue;
                    }

                    idNewVertex++;
                    Vertex vertex = new Vertex(idNewVertex, newMarking, createPredecessors(examine));

                    Vertex predecessorVertexWithLowerMarking = getPredecessorVertexWithLowerMarking(vertex, vertexMap);
                    if(predecessorVertexWithLowerMarking != null)
                    {
                        addOmegaToMarkingInNewVertex(vertex, predecessorVertexWithLowerMarking);
                    }

                    EdgeDirection edgeDirection = new EdgeDirection(examine, vertex);
                    edge.getEdgeDirections().add(edgeDirection);
                    vertexMap.put(vertex.getId(), vertex);

                }
            }
            examinedVertices.add(examine.getId());
            examine = getUnexaminedVertex(vertexMap, examinedVertices);
        }

        return new CoverabilityTree(getVertexListFromMap(vertexMap), edges);

    }

    private boolean existsEqualPredecessor(Vertex vertex,  Map<Integer, Vertex> vertexMap)
    {
        for(Integer id : vertex.getPredecessors())
        {
            Vertex vertexInVertexMap = vertexMap.get(id);
            if(equalMarking(vertex.getMarking(), vertexInVertexMap.getMarking()))
            {
                return true;
            }
        }
        return false;
    }

    private Vertex getPredecessorVertexWithLowerMarking(Vertex vertex, Map<Integer, Vertex> vertexMap)
    {
        for(Integer id : vertex.getPredecessors())
        {
            Vertex predecessorVertex = vertexMap.get(id);
            if(isNewMarkingGreater(predecessorVertex.getMarking(), vertex.getMarking()))
            {
                return predecessorVertex;
            }
        }
        return null;
    }

    private Vertex addOmegaToMarkingInNewVertex(Vertex newVertex, Vertex predecessorVertexWithLowerMarking)
    {
        int[] marking = predecessorVertexWithLowerMarking.getMarking();

        for (int i = 0; i < newVertex.getMarking().length; i++)
        {
            if(newVertex.getMarking()[i] > marking[i])
            {
                newVertex.getMarking()[i] = -1;
            }
        }

        return newVertex;
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

    private boolean isNewMarkingLower(int[] predecessorsMarking, int[] newMarking)
    {
        for (int i = 0; i < predecessorsMarking.length; i++) {
//            if(newMarking[i] == -1) {}
//            else if(newMarking[i] < predecessorsMarking[i])
//                return true;
            if(newMarking[i] != -1 && newMarking[i] < predecessorsMarking[i])
                return true;
        }
        return false;
    }

    private boolean isNewMarkingGreater(int[] predecessorsMarking, int[] newMarking)
    {
        if(equalMarking(predecessorsMarking, newMarking))
            return false;
        else return !isNewMarkingLower(predecessorsMarking, newMarking);
    }

    private boolean isCorrectMarking(int[] previousMarking, int[] newMarking)
    {
        for (int i = 0; i < previousMarking.length; i++)
        {
            if(previousMarking[i] != -1 && newMarking[i] < 0)
                return false;
        }
        return true;
    }

    private int[] computeNewMarking(int[] marking, int[] markingChange)
    {
        int[] newMarking = new int[marking.length];

        for(int i = 0; i < newMarking.length; i++)
        {
            if(marking[i] == -1)
                newMarking[i] = marking[i];
            else
                newMarking[i] = marking[i] + markingChange[i];
        }

        return newMarking;
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

    private ArrayList<Integer> createPredecessors(Vertex examineVertex)
    {
        Set<Integer> predecessors = new HashSet<>(examineVertex.getPredecessors());
        predecessors.add(examineVertex.getId());
        return new ArrayList<>(predecessors);
    }

    private Vertex prepareFirstVertex(Vertex firstVertex, int id)
    {
        firstVertex.setId(id);
        firstVertex.setPredecessors(new ArrayList<>());
        return firstVertex;
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
}
