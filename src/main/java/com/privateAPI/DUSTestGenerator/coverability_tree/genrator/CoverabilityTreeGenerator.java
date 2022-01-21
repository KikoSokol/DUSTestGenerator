package com.privateAPI.DUSTestGenerator.coverability_tree.genrator;

import com.privateAPI.DUSTestGenerator.coverability_tree.controller.request.CoverabilityTreeGeneratorRequest;
import com.privateAPI.DUSTestGenerator.coverability_tree.domain.CoverabilityTreeGeneratorResult;
import com.privateAPI.DUSTestGenerator.coverability_tree.domain.CoverabilityTreeMakerResult;
import com.privateAPI.DUSTestGenerator.coverability_tree.domain.CoverabilityTreeState;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Edge;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import com.privateAPI.DUSTestGenerator.util.GenaratorAllCombination;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class CoverabilityTreeGenerator
{
    private final CoverabilityTreeMaker coverabilityTreeMaker;

    public CoverabilityTreeGenerator(CoverabilityTreeMaker coverabilityTreeMaker) {
        this.coverabilityTreeMaker = coverabilityTreeMaker;
    }


    public CoverabilityTreeGeneratorResult generateRandomCoverabilityTree()
    {
        return makeRandomCoverabilityTree(10, 15);
    }

    public CoverabilityTreeGeneratorResult generateRandomCoverabilityTree(CoverabilityTreeGeneratorRequest generatorRequest)
    {
        return makeRandomCoverabilityTree(generatorRequest);
    }

    private CoverabilityTreeGeneratorResult makeRandomCoverabilityTree(int minVertices, int maxVertices)
    {
        int countOfDeletedCoverabilityTrees = 0;

        while (true)
        {
            Vertex firstVertex = generateFirstVertex(4, 5, 0, 3);
            List<Edge> edges = generateRandomEdges(firstVertex.getMarking().length, 3, 5,
                    -3, 3);

            CoverabilityTreeMakerResult coverabilityTreeMakerResult = this.coverabilityTreeMaker.
                    makeCoverabilityTree(firstVertex, edges, maxVertices);

            if(isCorrectCoverabilityTree(coverabilityTreeMakerResult, minVertices, maxVertices))
            {
                return new CoverabilityTreeGeneratorResult(countOfDeletedCoverabilityTrees,
                        coverabilityTreeMakerResult.getCoverabilityTree());
            }
            else
                countOfDeletedCoverabilityTrees++;

        }
    }

    private CoverabilityTreeGeneratorResult makeRandomCoverabilityTree(CoverabilityTreeGeneratorRequest generatorRequest)
    {
        int countOfDeletedCoverabilityTrees = 0;

        while (true)
        {
            Vertex firstVertex = generateFirstVertex(generatorRequest.getMinPlaces(), generatorRequest.getMaxPlaces(),
                    0, 3);
            List<Edge> edges = generateRandomEdges(firstVertex.getMarking().length, generatorRequest.getMinCountEdges(),
                    generatorRequest.getMaxCountEdges(), -3, 3);

            CoverabilityTreeMakerResult coverabilityTreeMakerResult = this.coverabilityTreeMaker.
                    makeCoverabilityTree(firstVertex, edges, generatorRequest.getMaxVertices());

            if(isCorrectCoverabilityTree(coverabilityTreeMakerResult, generatorRequest.getMinVertices(),
                    generatorRequest.getMaxVertices()))
            {
                return new CoverabilityTreeGeneratorResult(countOfDeletedCoverabilityTrees,
                        coverabilityTreeMakerResult.getCoverabilityTree());
            }
            else
                countOfDeletedCoverabilityTrees++;

        }
    }


    private boolean isCorrectCoverabilityTree(CoverabilityTreeMakerResult coverabilityTreeMakerResult, int minVertices,
                                              int maxVertices)
    {
        if(coverabilityTreeMakerResult.getCoverabilityTreeState() == CoverabilityTreeState.INCOMPLETE)
            return false;

        int countVertices = coverabilityTreeMakerResult.getCoverabilityTree().getVertices().size();

        if(coverabilityTreeMakerResult.getCoverabilityTreeState() == CoverabilityTreeState.COMPLETE)
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

    private List<Edge> generateRandomEdges(int numberOfPlaces, int minCount, int maxCount, int minNumber, int maxNumber)
    {
        Random random = new Random();
        GenaratorAllCombination genaratorAllCombination = new GenaratorAllCombination(minNumber, maxNumber, numberOfPlaces);
        genaratorAllCombination.removeCombinationsWithAllPositiveNumbers();

        int countEdges = random.nextInt((maxCount + 1) - minCount) + minCount;

        if(genaratorAllCombination.getActualCountOfCombination() < countEdges)
            countEdges = genaratorAllCombination.getActualCountOfCombination();


        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < countEdges; i++)
        {
            int[] marking = genaratorAllCombination.getRandomCombinationAndRemove();
            edges.add(new Edge(i+1, new ArrayList<>(), marking));
        }

        return edges;
    }

}
