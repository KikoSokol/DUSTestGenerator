package com.privateAPI.DUSTestGenerator.reachability_graph.util;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Edge;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.EdgeDirection;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;

import java.util.*;
import java.util.stream.Collectors;

public class ReachabilityGraphWorker
{

    public Map<Vertex, Map<Vertex, List<String>>> getNextVerticesMap(ReachabilityGraph reachabilityGraph)
    {
        Map<Vertex, Map<Vertex, List<String>>> possibleTransitionTakeFromStaticPlace = new HashMap<>();

        for(Vertex vertex : reachabilityGraph.getVertices())
        {
            possibleTransitionTakeFromStaticPlace.put(vertex, getNextVertices(reachabilityGraph, vertex));
        }

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
//        return deleteSameVerticesInParallelPath(path);
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

    public List<ReachabilityGraphParallelPaths> getAllParallelPaths(ReachabilityGraph reachabilityGraph,
                                                                    Map<Vertex, Map<Vertex, List<String>>> map)
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

//        return parallels;
        return maximizeParallelPaths(parallels, map);
    }

    public List<ReachabilityGraphParallelPaths> maximizeParallelPaths(List<ReachabilityGraphParallelPaths> parallels,
                                                                      Map<Vertex, Map<Vertex, List<String>>> map)
    {
        if(parallels.size() == 1 || parallels.isEmpty())
            return parallels;

        ReachabilityGraphParallelPaths first = parallels.get(0);
        for(int i = 1; i < parallels.size(); i++)
        {
            ReachabilityGraphParallelPaths second = parallels.get(i);

            if(isFirstVertexConnectedWithSecondInParallelPaths(first, second, map))
            {
                ReachabilityGraphParallelPaths newConnected = new ReachabilityGraphParallelPaths(first.getStartVertex(),
                        second.getEndVertex());
                List<ReachabilityGraphParallelPaths> newParallels = createMaximizeParallelPaths(first, second,
                        newConnected, parallels);
                return maximizeParallelPaths(newParallels, map);

            }
            first = second;
        }

        return parallels;
    }

    private boolean isFirstVertexConnectedWithSecondInParallelPaths(ReachabilityGraphParallelPaths first,
                                                                    ReachabilityGraphParallelPaths second,
                                                                    Map<Vertex, Map<Vertex, List<String>>> map)
    {
        for(int i = first.getStartVertex().getId(); i < first.getEndVertex().getId(); i++)
        {
            Vertex vertexFromFirst = getVertexFromMapById(i, map);

            for(int a = second.getStartVertex().getId(); a < second.getEndVertex().getId(); a++)
            {
                Vertex vertexFromSecond = getVertexFromMapById(a, map);
                if(map.get(vertexFromFirst).containsKey(vertexFromSecond))
                {
                    if(map.get(vertexFromSecond).size() > 1)
                        continue;
                    return true;
                }
            }
        }
        return false;
    }

    private List<ReachabilityGraphParallelPaths> createMaximizeParallelPaths(ReachabilityGraphParallelPaths first,
                                                                             ReachabilityGraphParallelPaths second,
                                                                             ReachabilityGraphParallelPaths newConnected,
                                                                             List<ReachabilityGraphParallelPaths> parallels)
    {
        List<ReachabilityGraphParallelPaths> newParallelsPath = new ArrayList<>();

        for(int i = 0; i < parallels.size(); i++)
        {
            if(parallels.get(i).equals(first))
                newParallelsPath.add(newConnected);

            if(!parallels.get(i).equals(second) && !parallels.get(i).equals(first))
                newParallelsPath.add(parallels.get(i));
        }
        return newParallelsPath;
    }

    private Vertex getVertexFromMapById(int id, Map<Vertex, Map<Vertex, List<String>>> map)
    {
        Vertex vertex = null;

        for(Vertex v : map.keySet())
        {
            if(v.getId() == id)
                vertex = v;
        }
        return vertex;
    }

    public int getLastIdVertex(ReachabilityGraph reachabilityGraph)
    {
        int count = reachabilityGraph.getVertices().size();

        return getSortedVertices(new HashSet<>(reachabilityGraph.getVertices())).get(count - 1);
    }

    public Map<Vertex, List<Vertex>> removeVerticesFromPaths(Map<Vertex, List<Vertex>> paths, Vertex start)
    {
        for(Vertex vertex : paths.keySet())
        {
            List<Vertex> tmpForDelete = new ArrayList<>();
            for(Vertex lVertex : paths.get(vertex))
            {
                if(lVertex.getId() >= start.getId())
                    tmpForDelete.add(lVertex);
            }
            paths.get(vertex).removeAll(tmpForDelete);
        }

        return paths;
    }

    public Map<Vertex, Map<Vertex, List<String>>> removeVerticesFromOutsideParallelPath(
            Map<Vertex, Map<Vertex, List<String>>> vertexMap, ReachabilityGraphParallelPaths parallel,
            Vertex nextVertex)
    {
        Map<Vertex, List<Vertex>> parallelPaths = getParallelPaths(vertexMap, parallel.getStartVertex());
        parallelPaths = removeVerticesFromPaths(parallelPaths, parallel.getEndVertex());

        List<Vertex> verticesFromVertexMap = vertexMap.keySet().stream().map(Vertex::new).collect(Collectors.toList());
        verticesFromVertexMap.remove(parallel.getStartVertex());
        verticesFromVertexMap.remove(parallel.getEndVertex());

        List<Vertex> verticesFromCorrectPath = parallelPaths.get(getStartParallelPath(parallelPaths, nextVertex));

        for(Vertex vertex : verticesFromVertexMap)
        {
            if(!verticesFromCorrectPath.contains(vertex))
            {
                vertexMap.remove(vertex);
            }
        }

        vertexMap.get(parallel.getEndVertex()).clear();


        List<Vertex> nextVerticesFromStartParallelVertex = vertexMap.get(parallel.getStartVertex()).
                keySet().stream().map(Vertex::new).collect(Collectors.toList());

        for(Vertex v : nextVerticesFromStartParallelVertex)
        {
            if(!v.equals(nextVertex))
            {
                vertexMap.get(parallel.getStartVertex()).remove(v);
            }
        }

        return vertexMap;
    }

    private Vertex getStartParallelPath(Map<Vertex, List<Vertex>> parallelPaths, Vertex vertex)
    {
        Vertex start = null;

        for(Vertex v : parallelPaths.keySet())
        {
            if(parallelPaths.get(v).contains(vertex))
            {
                start = v;
            }
        }
        return start;
    }


    public List<ReachabilityGraphParallelPaths> getAllSubParallelPaths(Map<Vertex, Map<Vertex, List<String>>> vertexMap)
    {
        List<Vertex> vertices = vertexMap.keySet().stream().map(Vertex::new)
                .sorted(Comparator.comparingInt(Vertex::getId)).collect(Collectors.toList());

        List<ReachabilityGraphParallelPaths> parallels = new ArrayList<>();

        for(int i = 0; i < vertices.size(); i++)
        {
            Vertex startVertex = vertices.get(i);
            if(vertexMap.get(startVertex).size() > 1)
            {
                Vertex endVertex = getEndParallelVertex(vertexMap, startVertex);
                parallels.add(new ReachabilityGraphParallelPaths(startVertex, endVertex));
                i = vertices.indexOf(endVertex) - 1;
            }
        }

        return parallels;
    }



    public Map<Vertex, List<Vertex>> deleteSameVerticesInParallelPath(Map<Vertex, List<Vertex>> parallelPath)
    {
        List<Vertex> vertices = parallelPath.keySet().stream().map(Vertex::new).collect(Collectors.toList());

        for(int i = 0; i < vertices.size(); i++)
        {
            for(int a = i + 1; a < vertices.size(); a++)
            {
                for(Vertex vertex : parallelPath.get(vertices.get(i)))
                {
                    parallelPath.get(vertices.get(a)).remove(vertex);
                }
            }
        }

        return parallelPath;
    }

    public boolean isParallelStartWithPlaces(ReachabilityGraphParallelPaths parallel,
                                              Map<Vertex, Map<Vertex, List<String>>> vertexMap)
    {
        System.out.println("Start parrrrralel: " + parallel.getStartVertex().getId());
        Map<Vertex, List<Vertex>> parallelPaths = getParallelPaths(vertexMap, parallel.getStartVertex());
        parallelPaths = removeVerticesFromPaths(parallelPaths, parallel.getEndVertex());

        List<Vertex> vertices = parallelPaths.keySet().stream().map(Vertex::new).collect(Collectors.toList());

        for(int i = 0; i < parallelPaths.size(); i++)
        {
            List<Vertex> basePathVertices = parallelPaths.get(vertices.get(i));

            List<List<String>> baseTransitions = getTransitionsOnPath(vertexMap, vertices.get(i),
                    basePathVertices);

            for(int a = i + 1; a < parallelPaths.size(); a++)
            {

                List<Vertex> forCompareVertices = parallelPaths.get(vertices.get(a));

                List<List<String>> forCompareTransitions = getTransitionsOnPath(vertexMap, vertices.get(a),
                        forCompareVertices);

                for(List<String> base : baseTransitions)
                {
                    if(forCompareTransitions.contains(base))
                        return true;
                }
            }
        }

        return false;
    }

    private List<List<String>> getTransitionsOnPath(Map<Vertex, Map<Vertex, List<String>>> vertexMap,
                                                    Vertex firstVertexInPath, List<Vertex> verticesOnPath)
    {
        List<List<String>> transitions = new ArrayList<>();

        for(Vertex vertexInVertexMap : vertexMap.keySet())
        {
            if(vertexMap.get(vertexInVertexMap).containsKey(firstVertexInPath))
            {
                transitions.add(vertexMap.get(vertexInVertexMap).get(firstVertexInPath));
            }
        }

        for(Vertex vertexInVerticesOnPath : verticesOnPath)
        {
            for(Vertex vertex : vertexMap.get(vertexInVerticesOnPath).keySet())
            {
                transitions.add(vertexMap.get(vertexInVerticesOnPath).get(vertex));
            }
        }

        return transitions;
    }


    public void printMap(Map<Vertex, Map<Vertex, List<String>>> map)
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
