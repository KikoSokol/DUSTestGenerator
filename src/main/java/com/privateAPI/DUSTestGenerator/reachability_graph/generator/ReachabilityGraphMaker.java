package com.privateAPI.DUSTestGenerator.reachability_graph.generator;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Edge;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.EdgeDirection;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.EdgeDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PlaceDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.TransitionDto;
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
        int idNewVertex = 0;
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
        int idNewVertex = 0;
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

    public ReachabilityGraphMakerResult makeReachabilityGraph(PetriNetDto petriNetDto)
    {
        Map<Integer,Vertex> vertexMap = new HashMap<>();
        List<Integer> examinedVertices = new ArrayList<>();
        int idNewVertex = 0;

        String[] placePositions = getPlacePosition(petriNetDto);
        Vertex firstVertex = createFirstVertexFromPetriNet(petriNetDto, placePositions, idNewVertex);
        List<Edge> edges = createEdgesFromPetriNet(petriNetDto, placePositions);

        vertexMap.put(firstVertex.getId(),firstVertex);

        Vertex examine = getUnexaminedVertex(vertexMap,examinedVertices);
        while(examine != null)
        {
            for(Edge edge : edges)
            {
                if(!isTransitionExecutable(edge, examine, petriNetDto, placePositions))
                   continue;

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

    private Vertex createFirstVertexFromPetriNet(PetriNetDto petriNetDto, String[] placesPositions, int id)
    {
        int[] marking = new int[placesPositions.length];

        for(int i = 0; i < placesPositions.length; i++)
        {
            for(PlaceDto placeDto : petriNetDto.getPlaces())
            {
                if(placeDto.getId().compareTo(placesPositions[i]) == 0)
                {
                    marking[i] = placeDto.getNumberOfTokens();
                    break;
                }
            }
        }

        return new Vertex(id, marking);

    }

    private List<Edge> createEdgesFromPetriNet(PetriNetDto petriNetDto, String[] placesPositions)
    {
        List<Edge> edges = new ArrayList<>();

        for(TransitionDto transitionDto : petriNetDto.getTransitions())
        {
            int[] markingChange = new int[placesPositions.length];

            for (int i = 0; i < placesPositions.length; i++)
            {
                markingChange[i] = getMarkingChangeForPlace(placesPositions[i], transitionDto.getId(), petriNetDto.getEdges());
            }
            edges.add(new Edge(transitionDto.getId(), new ArrayList<>(), markingChange));
        }

        return edges;
    }


    private int getMarkingChangeForPlace(String place, String transition, List<EdgeDto> edges)
    {
        int fromTransitionToPlace = 0;
        int fromPlaceToTransition = 0;

        for(EdgeDto edgeDto : edges)
        {
            if(edgeDto.getFrom().compareTo(transition) == 0 && edgeDto.getTo().compareTo(place) == 0)
                fromTransitionToPlace += edgeDto.getWeight();
            else if(edgeDto.getFrom().compareTo(place) == 0 && edgeDto.getTo().compareTo(transition) == 0)
                fromPlaceToTransition += edgeDto.getWeight();
        }

        return fromTransitionToPlace - fromPlaceToTransition;
    }

    private int getSumWeightOfEdgesFromPlaceToTransition(String place, String transition, List<EdgeDto> edges)
    {
        int fromPlaceToTransition = 0;

        for(EdgeDto edgeDto : edges)
        {
            if(edgeDto.getFrom().compareTo(place) == 0 && edgeDto.getTo().compareTo(transition) == 0)
                fromPlaceToTransition += edgeDto.getWeight();
        }

        return fromPlaceToTransition;
    }

    private String[] getPlacePosition(PetriNetDto petriNetDto)
    {
        List<PlaceDto> places = petriNetDto.sortPlaces();
        List<PlaceDto> staticPlaces = new ArrayList<>();
        for(PlaceDto placeDto : places)
        {
            if(placeDto.isStatic())
                staticPlaces.add(placeDto);
        }
        places.removeAll(staticPlaces);

        String[] positions = new String[places.size()];

        for (int i = 0; i < places.size(); i++)
        {
            positions[i] = places.get(i).getId();
        }

        return positions;
    }


    private boolean isTransitionExecutable(Edge edge, Vertex vertex, PetriNetDto petriNetDto, String[] placePositions)
    {
        for (int i = 0; i < placePositions.length; i++)
        {
            int countMarksGetFromPlace = getSumWeightOfEdgesFromPlaceToTransition(placePositions[i], edge.getId(), petriNetDto.getEdges());
            if(countMarksGetFromPlace > vertex.getMarking()[i])
                return false;
        }
        return true;
    }

    private int getCountVertexInVertexMap(Map<Integer,Vertex> vertexMap)
    {
        return vertexMap.keySet().size();
    }
}
