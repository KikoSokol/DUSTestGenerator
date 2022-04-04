package com.privateAPI.DUSTestGenerator.workflow.generator;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.EdgeDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PlaceDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraphMakerResult;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraphState;
import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphMaker;
import com.privateAPI.DUSTestGenerator.reachability_graph.util.ReachabilityGraphParallelPaths;
import com.privateAPI.DUSTestGenerator.reachability_graph.util.ReachabilityGraphWorker;


import java.util.*;
import java.util.stream.Collectors;

public class StaticPlacesGenerator
{

    private final ReachabilityGraphMaker reachabilityGraphMaker;
    private final ReachabilityGraphWorker reachabilityGraphWorker;

    public StaticPlacesGenerator() {
        this.reachabilityGraphMaker = new ReachabilityGraphMaker();
        this.reachabilityGraphWorker = new ReachabilityGraphWorker();
    }

    public PetriNetDto addStaticPlacesToWorkflow(PetriNetDto workflow, int count)
    {
        ReachabilityGraphMakerResult reachabilityGraphMakerResult = this.reachabilityGraphMaker
                .makeReachabilityGraph(workflow);

        if(reachabilityGraphMakerResult.getState() == ReachabilityGraphState.UNBOUDED)
            return null;


        for(int i = 0; i < count; i++)
        {
            workflow = addStaticPlaceToWorkflow(workflow, reachabilityGraphMakerResult.getReachabilityGraph(), i+1);
        }

        return workflow;
    }

    private PetriNetDto addStaticPlaceToWorkflow(PetriNetDto workflow, ReachabilityGraph reachabilityGraph, int id)
    {
        Map<Vertex, Map<Vertex, List<String>>> vertexMap = this.reachabilityGraphWorker
                .getNextVerticesMap(reachabilityGraph);

        List<ReachabilityGraphParallelPaths> parallelPaths = this.reachabilityGraphWorker
                .getAllParallelPaths(reachabilityGraph, vertexMap);


//        for(Vertex vertex : vertexMap.keySet())
//        {
//            if(vertex.getId() == 1)
//            {
//                Map<Vertex, List<Vertex>> p = this.reachabilityGraphWorker.getParallelPaths(vertexMap, vertex);
////                p = this.reachabilityGraphWorker.removeVerticesFromPaths(p, parallelPaths.get(0).getEndVertex());
//                for(Vertex v : p.keySet())
//                {
//                    System.out.print(v.getId() + ": ");
//                    for(Vertex l : p.get(v))
//                    {
//                        System.out.print(l.getId() + ", ");
//                    }
//                    System.out.println();
//                }
//            }
//        }


        Vertex fromVertex = getRandomVertexFromStaticPlace(reachabilityGraph.getVertices());
        Map<Vertex, List<String>> nextVertices = vertexMap.get(fromVertex);
        Vertex afterFromVertex = getRandomTransitions(nextVertices);
        List<String> transitionsTakeFromStaticPlace = nextVertices.get(afterFromVertex);
        int positionFromVertex = getPositionVertex(parallelPaths, fromVertex);


        Vertex randomVertexToStaticPlace = getRandomVertexToPlace(vertexMap, afterFromVertex,
                this.reachabilityGraphWorker.getLastIdVertex(reachabilityGraph));
        Map<Vertex, List<String>> nextVerticesToPlace = vertexMap.get(randomVertexToStaticPlace);
        Vertex afterToPlace = getRandomTransitions(nextVerticesToPlace);
        List<String> transitionsToStaticPlace = nextVerticesToPlace.get(afterToPlace);
        int positionRandomVertexToStaticPlace = getPositionVertex(parallelPaths, randomVertexToStaticPlace);



        if(positionFromVertex == -1 && positionRandomVertexToStaticPlace == -1)
        {
            generateAndConnectStaticPlace(workflow, transitionsTakeFromStaticPlace, transitionsToStaticPlace, id);
        }
        else if(positionFromVertex > -1 && positionRandomVertexToStaticPlace > -1)
        {
            if(positionFromVertex < positionRandomVertexToStaticPlace)
            {
                List<String> fromStaticPlaces = getAllMustConnectedWithStaticPlace(vertexMap,
                        parallelPaths.get(positionFromVertex), new ArrayList<>());

                List<String> toStaticPlaces = getAllMustConnectedWithStaticPlace(vertexMap,
                        parallelPaths.get(positionRandomVertexToStaticPlace), new ArrayList<>());

                generateAndConnectStaticPlace(workflow, fromStaticPlaces, toStaticPlaces, id);
            }
        }
        else if(positionFromVertex > -1 && positionRandomVertexToStaticPlace == -1)
        {
            List<String> fromStaticPlaces = getAllMustConnectedWithStaticPlace(vertexMap,
                    parallelPaths.get(positionFromVertex), new ArrayList<>());

            generateAndConnectStaticPlace(workflow, fromStaticPlaces, transitionsToStaticPlace, id);
        }
        else if(positionFromVertex == -1 && positionRandomVertexToStaticPlace > -1)
        {
            List<String> toStaticPlaces = getAllMustConnectedWithStaticPlace(vertexMap,
                    parallelPaths.get(positionRandomVertexToStaticPlace), new ArrayList<>());

            generateAndConnectStaticPlace(workflow, transitionsTakeFromStaticPlace, toStaticPlaces, id);
        }

        System.out.println("CREATE");


        return workflow; // vymaz a vrat workflow so statickym miestom
    }

    private Vertex getRandomVertexFromStaticPlace(List<Vertex> vertices)
    {
        Random random = new Random();
        int randomIndex = random.nextInt(vertices.size() - 2);

        return vertices.get(randomIndex);
    }

    private Vertex getRandomVertexToPlace(Map<Vertex, Map<Vertex, List<String>>> vertexMap, Vertex fromVertex,
                                          int idLastVertex)
    {
        Map<Vertex, List<Vertex>> paths = this.reachabilityGraphWorker.getParallelPaths(vertexMap, fromVertex);

        Set<Vertex> possibleVertices = new HashSet<>();


        for(Vertex vertex : paths.keySet())
        {
            for(Vertex listVertex : paths.get(vertex))
            {
                if(listVertex.getId() != idLastVertex)
                    possibleVertices.add(listVertex);
            }
        }

        possibleVertices.add(fromVertex);

        Random random = new Random();
        int randomIndex = random.nextInt(possibleVertices.size());

        return (Vertex) possibleVertices.toArray()[randomIndex];

    }

    private Vertex getRandomTransitions(Map<Vertex, List<String>> vertices)
    {
        Random random = new Random();
        int randomIndex = random.nextInt(vertices.size());

        Vertex nextVertex = null;
        int index = 0;
        for(Vertex vertex : vertices.keySet())
        {
            if(index == randomIndex)
            {
                nextVertex = vertex;
                break;
            }

            index++;
        }
        return nextVertex;
    }


    private int getPositionVertex(List<ReachabilityGraphParallelPaths> parallelPaths, Vertex vertex)
    {
        for(int i = 0; i < parallelPaths.size(); i++)
        {
            Vertex startVertex = parallelPaths.get(i).getStartVertex();
            Vertex endVertex = parallelPaths.get(i).getEndVertex();

            if(startVertex.getId() <= vertex.getId() && vertex.getId() < endVertex.getId())
                return i;
        }

        return -1;
    }

    private List<String> getAllMustConnectedWithStaticPlace(Map<Vertex, Map<Vertex, List<String>>> vertexMap,
                                                            ReachabilityGraphParallelPaths parallel,
                                                            List<Vertex> examinePath)
    {
        Map<Vertex, List<Vertex>> paths = this.reachabilityGraphWorker.getParallelPaths(vertexMap, parallel.getStartVertex());
        paths = this.reachabilityGraphWorker.removeVerticesFromPaths(paths, parallel.getEndVertex());

        deleteVertexInExaminePath(paths, examinePath);

        Map<Vertex, List<List<String>>> option = new HashMap<>();

        for(Vertex vertex : paths.keySet())
        {
            option.put(vertex, new ArrayList<>());
            option.get(vertex).add(vertexMap.get(parallel.getStartVertex()).get(vertex));
            List<Vertex> path = paths.get(vertex);

            while(path.size() > 0)
            {
                Vertex examineVertex = path.get(0);
                if(vertexMap.get(examineVertex).size() == 1)
                {
                    option.get(vertex).add(examine(vertexMap, examineVertex, path));
                }
                else
                {
                    ReachabilityGraphParallelPaths par = computeParallel(vertexMap, examineVertex);
                    option.get(vertex).add(getAllMustConnectedWithStaticPlace(vertexMap, par, path));
                }
            }

        }

        return mergeOption(option);
    }

    private List<String> examine(Map<Vertex, Map<Vertex, List<String>>> vertexMap, Vertex vertex, List<Vertex> path)
    {
        path.remove(vertex);

        Map<Vertex, List<String>> tmp = vertexMap.get(vertex);

        return tmp.get((Vertex)(tmp.keySet().toArray())[0]);
    }

    private ReachabilityGraphParallelPaths computeParallel(Map<Vertex, Map<Vertex, List<String>>> vertexMap, Vertex vertex)
    {
        Vertex endVertex = this.reachabilityGraphWorker.getEndParallelVertex(vertexMap, vertex);

        return new ReachabilityGraphParallelPaths(vertex, endVertex);
    }


    private void deleteVertexInExaminePath(Map<Vertex, List<Vertex>> paths, List<Vertex> path)
    {
        for(Vertex vertex : paths.keySet())
        {
            for(Vertex forDelete : paths.get(vertex))
            {
                path.remove(forDelete);
            }
        }
    }

//    private List<String> mergeOption(Map<Vertex, List<List<String>>> options)
//    {
//        List<String> result = new ArrayList<>();
//
//        for(Vertex vertex : options.keySet())
//        {
//            List<List<String>> opt = options.get(vertex);
//            Random random = new Random();
//            int randIndex = random.nextInt(opt.size());
//            result.addAll(opt.get(randIndex));
//        }
//        return result;
//    }



    private List<String> mergeOption(Map<Vertex, List<List<String>>> options)
    {

        removeEquals(options);

        List<String> result = new ArrayList<>();

        for(Vertex vertex : options.keySet())
        {
            List<List<String>> opt = options.get(vertex);
            Random random = new Random();
            int randIndex = random.nextInt(opt.size());
            result.addAll(opt.get(randIndex));
        }
        return result;
    }


    private void removeEquals(Map<Vertex, List<List<String>>> options)
    {
        List<Vertex> vertices = options.keySet().stream().map(Vertex::new).collect(Collectors.toList());

        List<Vertex> toCheck = new ArrayList<>();


        while(true)
        {
            for(Vertex vertex : vertices)
            {
                List<Vertex> equals = getVertexWithEqualsOptions(options, vertex);
                if(equals.size() > 1)
                    toCheck.add(vertex);
            }
            if(toCheck.isEmpty())
                break;
            List<Vertex> removed = new ArrayList<>();
            for(Vertex check : toCheck)
            {
                if(removed.contains(check))
                    continue;
                removed.addAll(deleteRandomVertex(options, getVertexWithEqualsOptions(options, check)));
                vertices.removeAll(removed);
            }
            toCheck.clear();
        }

    }

    private List<Vertex> deleteRandomVertex(Map<Vertex, List<List<String>>> options, List<Vertex> equals)
    {
        List<Vertex> deleted = new ArrayList<>();
        Random random = new Random();
        int notRemove = random.nextInt(equals.size());

        for(int i = 0; i < equals.size(); i++)
        {
            if(i != notRemove)
            {
                options.remove(equals.get(i));
                deleted.add(equals.get(i));
            }
        }
        return deleted;
    }


    private List<Vertex> getVertexWithEqualsOptions(Map<Vertex, List<List<String>>> options, Vertex vertex)
    {
        List<Vertex> withEqualsOptions = new ArrayList<>();

        List<List<String>> e = options.get(vertex);

        for(Vertex v : options.keySet())
        {
            List<List<String>> a = options.get(v);

            if(e == null)
                System.out.println("eeee");
            else if(a == null)
                System.out.println("aaaa");

            if(e.containsAll(a) || a.containsAll(e))
                withEqualsOptions.add(v);
        }

        return withEqualsOptions;
    }


    private PetriNetDto generateAndConnectStaticPlace(PetriNetDto workflow, List<String> fromStaticPlace, List<String> toStaticPlace, int id)
    {
        PlaceDto placeDto = new PlaceDto(1, "s-"  + id, true);

        workflow.getPlaces().add(placeDto);

        for(String from : fromStaticPlace)
        {
            workflow.getEdges().add(new EdgeDto(placeDto.getId(), from, 1));
        }

        for(String to : toStaticPlace)
        {
            workflow.getEdges().add(new EdgeDto(to, placeDto.getId(), 1));
        }

        return workflow;
    }
}
