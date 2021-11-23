package com.privateAPI.DUSTestGenerator.reachability_graph.generator;

import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ReachabilityGraphGenerator {


    private final ReachabilityGraphMaker reachabilityGraphMaker;

    public ReachabilityGraphGenerator(ReachabilityGraphMaker reachabilityGraphMaker) {
        this.reachabilityGraphMaker = reachabilityGraphMaker;
    }

    public ReachabilityGraph hardcodedGenerateReachabilityGraph() {
        List<Vertex> hardcodedVertices = hardcodedGenerateVertices();
        List<Edge> hardcodedEdges = hardcodedGenerateEdges(hardcodedVertices);
        return new ReachabilityGraph(hardcodedVertices, hardcodedEdges);
    }

    public List<Vertex> hardcodedGenerateVertices() {
        List<Vertex> hardcodedVertices = new ArrayList<>();
        hardcodedVertices.add(new Vertex(0, new int[]{1, 1, 0, 1, 0}));
        hardcodedVertices.add(new Vertex(1, new int[]{0, 0, 2, 1, 0}, new ArrayList<>(Collections.singletonList(0))));
        hardcodedVertices.add(new Vertex(2, new int[]{1, 1, 0, 0, 1}, new ArrayList<>(Arrays.asList(0, 2, 3, 4, 5))));
        hardcodedVertices.add(new Vertex(3, new int[]{0, 0, 2, 0, 1}, new ArrayList<>(Arrays.asList(0, 2, 1))));
        hardcodedVertices.add(new Vertex(4, new int[]{1, 0, 1, 0, 1}, new ArrayList<>(Arrays.asList(0, 2, 3, 1, 8))));
        hardcodedVertices.add(new Vertex(5, new int[]{0, 1, 1, 0, 1}, new ArrayList<>(Arrays.asList(0, 2, 3))));
        hardcodedVertices.add(new Vertex(6, new int[]{2, 0, 0, 0, 1}, new ArrayList<>(Arrays.asList(0, 2, 3, 4, 1, 8, 9))));
        hardcodedVertices.add(new Vertex(7, new int[]{0, 2, 0, 0, 1}, new ArrayList<>(Arrays.asList(0, 2, 3, 5))));
        hardcodedVertices.add(new Vertex(8, new int[]{1, 0, 1, 1, 0}, new ArrayList<>(Arrays.asList(0, 1))));
        hardcodedVertices.add(new Vertex(9, new int[]{2, 0, 0, 1, 0}, new ArrayList<>(Arrays.asList(0, 1, 8))));
        return hardcodedVertices;
    }

    public List<Edge> hardcodedGenerateEdges(List<Vertex> hardcodedVertices) {
        List<Edge> hardcodedEdges = new ArrayList<>();
        hardcodedEdges.add(new Edge(1, new ArrayList<>(Arrays.asList(
                new EdgeDirection(hardcodedVertices.get(0), hardcodedVertices.get(1)),
                new EdgeDirection(hardcodedVertices.get(2), hardcodedVertices.get(3))
        )), new int[]{-1, -1, 2, 0, 0}));
        hardcodedEdges.add(new Edge(2, new ArrayList<>(Arrays.asList(
                new EdgeDirection(hardcodedVertices.get(1), hardcodedVertices.get(8)),
                new EdgeDirection(hardcodedVertices.get(5), hardcodedVertices.get(2)),
                new EdgeDirection(hardcodedVertices.get(3), hardcodedVertices.get(4)),
                new EdgeDirection(hardcodedVertices.get(4), hardcodedVertices.get(6)),
                new EdgeDirection(hardcodedVertices.get(8), hardcodedVertices.get(9))
        )), new int[]{1, 0, -1, 0, 0}));
        hardcodedEdges.add(new Edge(3, new ArrayList<>(Arrays.asList(
                new EdgeDirection(hardcodedVertices.get(4), hardcodedVertices.get(2)),
                new EdgeDirection(hardcodedVertices.get(3), hardcodedVertices.get(5)),
                new EdgeDirection(hardcodedVertices.get(5), hardcodedVertices.get(7))
        )), new int[]{0, 1, -1, 0, 0}));
        hardcodedEdges.add(new Edge(4, new ArrayList<>(Arrays.asList(
                new EdgeDirection(hardcodedVertices.get(0), hardcodedVertices.get(2)),
                new EdgeDirection(hardcodedVertices.get(1), hardcodedVertices.get(3)),
                new EdgeDirection(hardcodedVertices.get(8), hardcodedVertices.get(4)),
                new EdgeDirection(hardcodedVertices.get(9), hardcodedVertices.get(6))
        )), new int[]{0, 0, 0, -1, 1}));
        return hardcodedEdges;
    }



    public ReachabilityGraphGeneratorResult generateRandomReachabilityGraph()
    {
        return makeRandomReachabilityGraph(10,15);
    }

    public ReachabilityGraphGeneratorResult generateRandomReachabilityGraph(ReachabilityGraphGeneratorRequest generatorRequest)
    {
        return makeRandomReachabilityGraph(generatorRequest);
    }



    private ReachabilityGraphGeneratorResult makeRandomReachabilityGraph(int minVertices, int maxVertices)
    {
        int countOfDeletedReachabilityGraphs = 0;
        while(true)
        {
            Vertex firstVertex = generateFirstVertex(4,5,0,3);
            List<Edge> edges = generateRandomEdges(firstVertex.getMarking().length,3,5,-3,3);
            ReachabilityGraphMakerResult reachabilityGraphMakerResult = reachabilityGraphMaker.makeReachabilityGraph(firstVertex, edges, maxVertices);

            if(isCorrectReachabilityGraph(reachabilityGraphMakerResult, minVertices, maxVertices))
            {
                return new ReachabilityGraphGeneratorResult(countOfDeletedReachabilityGraphs,
                        reachabilityGraphMakerResult.getReachabilityGraph());
            }
            else
                countOfDeletedReachabilityGraphs++;
        }
    }

    private ReachabilityGraphGeneratorResult makeRandomReachabilityGraph(ReachabilityGraphGeneratorRequest generatorRequest)
    {

        int countOfDeletedReachabilityGraphs = 0;
        while(true)
        {
            Vertex firstVertex = generateFirstVertex(generatorRequest.getMinPlaces(), generatorRequest.getMaxPlaces(),
                    0,3);
            List<Edge> edges = generateRandomEdges(firstVertex.getMarking().length,generatorRequest.getMinCountEdges(),
                    generatorRequest.getMaxCountEdges(),-3,3);
            ReachabilityGraphMakerResult reachabilityGraphMakerResult = reachabilityGraphMaker.makeReachabilityGraph(firstVertex,
                    edges, generatorRequest.getMaxVertices());

            if(isCorrectReachabilityGraph(reachabilityGraphMakerResult, generatorRequest.getMinVertices(), generatorRequest.getMaxVertices()))
            {
                return new ReachabilityGraphGeneratorResult(countOfDeletedReachabilityGraphs,
                        reachabilityGraphMakerResult.getReachabilityGraph());
            }
            else
                countOfDeletedReachabilityGraphs++;
        }
    }

    private boolean isCorrectReachabilityGraph(ReachabilityGraphMakerResult reachabilityGraphMakerResult, int minVertices, int maxVertices)
    {

        if(reachabilityGraphMakerResult.getState() == ReachabilityGraphState.UNBOUDED)
            return false;

        int countVertices = reachabilityGraphMakerResult.getReachabilityGraph().getVertices().size();

        if(reachabilityGraphMakerResult.getState() == ReachabilityGraphState.INCOMPLETE)
            return false;
        else if(reachabilityGraphMakerResult.getState() == ReachabilityGraphState.BOUNDED)
        {
            if(countVertices < minVertices || countVertices > maxVertices)
                return false;
        }

        return true;

    }


    private Vertex generateFirstVertex(int minPlaces, int maxPlaces, int minToken, int maxToken)
    {
        Random random = new Random();
        int numberOfPlaces = random.nextInt((maxPlaces + 1) - minPlaces) + minPlaces;

        return generateRandomVertex(1,numberOfPlaces,minToken,maxToken);
    }

    private List<Edge> generateRandomEdges(int numberOfPlaces, int minCount, int maxCount, int minNumber, int maxNumber)
    {
        Random random = new Random();
        int countEdges = random.nextInt((maxCount + 1) - minCount) + minCount;

        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < countEdges; i++) {
            edges.add(generateRandomEdge(edges, numberOfPlaces,i+1, minNumber, maxNumber));
        }

        return edges;
    }

    private Edge generateRandomEdge(List<Edge> edges, int numberOfPlaces, int id, int minNumber, int maxNumber)
    {
        while (true)
        {
            Edge edge = generateRandomEdge(id, numberOfPlaces, minNumber, maxNumber);
            if(isCorrectEdge(edges,edge))
            {
                return edge;
            }

        }
    }

    private boolean isCorrectEdge(List<Edge> edges, Edge edge)
    {
        for(Edge e : edges)
        {
            if(isEqualMarkingChange(e.getMarkingChange(), edge.getMarkingChange()))
            {
                return false;
            }
        }
        return true;
    }

    private boolean isEqualMarkingChange(int[] markingChange1, int[] markingChange2)
    {
        if(markingChange1.length != markingChange2.length)
            return false;

        for (int i = 0; i < markingChange1.length; i++)
        {
            if(markingChange1[i] != markingChange2[i])
                return false;
        }

        return true;
    }

    private Edge generateRandomEdge(int id, int numberOfPlaces, int minNumber, int maxNumber)
    {
        int[] markingChange = getRandomMarkingChange(numberOfPlaces,minNumber,maxNumber);
        return new Edge(id, new ArrayList<>(), markingChange);
    }

    private Vertex generateRandomVertex(int id, int numberOfPlaces, int minToken, int maxToken)
    {
        return new Vertex(id,getRandomMarking(numberOfPlaces,minToken,maxToken));
    }

    private int[] getRandomMarking(int numberOfPlaces, int minToken, int maxToken)
    {
        int[] marking = new int[numberOfPlaces];
        Random random = new Random();

        for (int i = 0; i < marking.length; i++) {
            marking[i] = random.nextInt((maxToken + 1) - minToken) + minToken;
        }

        return marking;
    }

    private int[] getRandomMarkingChange(int numberOfPlaces, int minToken, int maxToken)
    {
        int[] marking = new int[numberOfPlaces];
        Random random = new Random();
        int negativeIndex = random.nextInt(numberOfPlaces);

        for (int i = 0; i < numberOfPlaces; i++) {
            if(i != negativeIndex)
                marking[i] = random.nextInt((maxToken + 1) - minToken) + minToken;
            else
                marking[i] = random.nextInt(-minToken) + minToken;
        }

        return marking;
    }


}
