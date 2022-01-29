package com.privateAPI.DUSTestGenerator.petri_nets.generator;



import com.privateAPI.DUSTestGenerator.petri_nets.controller.request.PetriNetGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.EdgeDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PlaceDto;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PetriNetGenerator
{

    public PetriNetDto generateRandomPetriNet(PetriNetGeneratorRequest request)
    {
        List<PlaceDto> places = generatePlaces(request.getMinPlaces(), request.getMaxPlaces());
        List<String> transitions = generateTransitions(request.getMinTransition(), request.getMaxTransition());
        List<EdgeDto> edges = generateRandomEdges(places, transitions, request.getMinEdges(), request.getMaxEdges());

        generateRandomMarking(places, request.getMinToken(), request.getMaxToken());
        generateRandomWeightsOfEdges(edges, request.getMinEdgeWeight(), request.getMaxEdgeWeight());

        return new PetriNetDto(places, transitions, edges);

    }

    public List<PlaceDto> generatePlaces(int minPlaces, int maxPlaces)
    {
        List<PlaceDto> places = new ArrayList<>();

        Random random = new Random();
        int countOfPlaces = random.nextInt((maxPlaces + 1) - minPlaces) + minPlaces;

        char name = 97;
        for(int i = 0; i < countOfPlaces; i++)
        {
            PlaceDto place = new PlaceDto(0, "" + name);
            places.add(place);
            name++;
        }

        return places;
    }

    private List<String> generateTransitions(int minTransition, int maxTransition)
    {
        List<String> transitions = new ArrayList<>();
        Random random = new Random();
        int countOfTransitions = random.nextInt((maxTransition + 1) - minTransition) + minTransition;

        char name = 65;
        for(int i = 0; i < countOfTransitions; i++)
        {
            transitions.add("" + name);
            name++;
        }

        return transitions;
    }

    private void generateRandomMarking(List<PlaceDto> places, int minToken, int maxToken)
    {
        Random random = new Random();

        for(PlaceDto place : places)
        {
            int numberOfToken = random.nextInt((maxToken + 1) - minToken) + minToken;
            place.setNumberOfTokens(numberOfToken);
        }
    }

    private void generateRandomWeightsOfEdges(List<EdgeDto> edges, int minWeight, int maxWeight)
    {
        Random random = new Random();

        for(EdgeDto edge : edges)
        {
            int weight = random.nextInt((maxWeight + 1) - minWeight) + minWeight;
            edge.setWeight(weight);
        }

    }

    private List<EdgeDto> generateRandomEdges(List<PlaceDto> places, List<String> transitions, int minEdges, int maxEdges)
    {
        int maxPossibleEdges = places.size() * transitions.size() * 2;

        if(maxPossibleEdges < maxEdges)
            maxEdges = maxPossibleEdges;

        if(maxPossibleEdges < minEdges)
            minEdges = maxEdges;

        Random random = new Random();
        int countOfTransition = random.nextInt((maxEdges + 1) - minEdges) + minEdges;


        return generateEdges(places, transitions, countOfTransition);

    }

    private List<EdgeDto> generateEdges(List<PlaceDto> places, List<String> transitions, int count)
    {

        Map<String, List<String>> placeToTransitionMap = createPlaceToTransitionMap(places, transitions);
        Map<String, List<String>> transitionToPlaceMap = createTransitionToPlaceMap(places, transitions);

//        int minPossibleEdges = places.size() + transitions.size() - 1;

        List<EdgeDto> edges = new ArrayList<>(generateMinimumEdges(placeToTransitionMap, transitionToPlaceMap, count));

        edges.addAll(generateFullRandomEdges(placeToTransitionMap, transitionToPlaceMap, count - edges.size()));

        return edges;
    }

    private List<EdgeDto> generateFullRandomEdges(Map<String, List<String>> placeToTransitionMap, Map<String, List<String>> transitionToPlaceMap, int count)
    {
        Random random = new Random();
        List<EdgeDto> edges = new ArrayList<>();

        List<String> placesId = placeToTransitionMap.keySet().stream().map(String::new).collect(Collectors.toList());
        List<String> transitionsId = transitionToPlaceMap.keySet().stream().map(String::new).collect(Collectors.toList());

        int totalCount = 0;

        while(totalCount < count)
        {
            int placeOrTransition = random.nextInt(2); //0 - place, 1 - transition
            if(placeOrTransition == 0)
            {
                String place = placesId.get(random.nextInt(placesId.size()));
                EdgeDto edgeDto = createRandomEdgeConnectedWithPlace(placeToTransitionMap, transitionToPlaceMap, place);
                if(edgeDto != null)
                {
                    edges.add(edgeDto);
                    totalCount++;
                }
            }
            else
            {
                String transition = transitionsId.get(random.nextInt(transitionsId.size()));
                EdgeDto edgeDto = createRandomEdgeConnectedWithTransition(placeToTransitionMap, transitionToPlaceMap, transition);
                if(edgeDto != null)
                {
                    edges.add(edgeDto);
                    totalCount++;
                }
            }
        }

        return edges;

    }


    private List<EdgeDto> generateMinimumEdges(Map<String, List<String>> placeToTransitionMap, Map<String, List<String>> transitionToPlaceMap, int count)
    {
        List<EdgeDto> edges = new ArrayList<>();

        String[] places = getRandomOrderOfId(new ArrayList<>(placeToTransitionMap.keySet()));
        String[] transitions = getRandomOrderOfId(new ArrayList<>(transitionToPlaceMap.keySet()));

        Random random = new Random();
        String[] connectedId = connectIds(places, transitions, random.nextBoolean());

        int totalCountEdges = 0;

        int stop = -1;

        for(int i = 0; i < connectedId.length - 1 && i < count; i++)
        {

            String idFirst = connectedId[i];
            String idSecond = connectedId[i+1];

            if(isPlace(idFirst) && isTransition(idSecond))
            {
                int direction = random.nextInt(2);
                if(direction == 0)
                {
                    edges.add(createEdgeFromPlaceToTransition(placeToTransitionMap, idFirst, idSecond));
                }
                else
                {
                    edges.add(createEdgeFromTransitionToPlace(transitionToPlaceMap, idSecond, idFirst));
                }
            }
            else if(isPlace(idSecond) && isTransition(idFirst))
            {
                int direction = random.nextInt(2);
                if(direction == 0)
                {
                    edges.add(createEdgeFromPlaceToTransition(placeToTransitionMap, idSecond, idFirst));
                }
                else
                {
                    edges.add(createEdgeFromTransitionToPlace(transitionToPlaceMap, idFirst, idSecond));
                }
            }
            else if(isPlace(idFirst) && isPlace(idSecond))
            {
                stop = i+1;
                break;
            }
            else if(isTransition(idFirst) && isPlace(idSecond))
            {
                stop = i+1;
                break;
            }

            totalCountEdges++;
        }

        if(stop != -1 && totalCountEdges < count)
        {
            for(int i = stop; stop < connectedId.length && stop - 1 < count; stop++)
            {
                String id = connectedId[i];
                if(isPlace(id))
                {
                    edges.add(createRandomEdgeConnectedWithPlace(placeToTransitionMap,
                            transitionToPlaceMap, id));
                }
                else if(isTransition(id))
                {
                    edges.add(createRandomEdgeConnectedWithTransition(placeToTransitionMap,
                            transitionToPlaceMap, id));
                }
            }
        }

        return edges;

    }

    private boolean isPlace(String place)
    {
        String tmp = place.toLowerCase();
        return place.compareTo(tmp) == 0;
    }

    private boolean isTransition(String transition)
    {
        String tmp = transition.toUpperCase();
        return transition.compareTo(tmp) == 0;
    }

    private EdgeDto createEdgeFromPlaceToTransition(Map<String, List<String>> placeToTransitionMap, String place, String transition)
    {
        List<String> possibleTransitions = placeToTransitionMap.get(place);
        if(!possibleTransitions.contains(transition))
            return null;

        EdgeDto edge = new EdgeDto(place,transition,0);
        possibleTransitions.remove(transition);

        return edge;
    }

    private EdgeDto createEdgeFromPlaceToRandomTransition(Map<String, List<String>> placeToTransitionMap, String place)
    {
        List<String> possibleTransitions = placeToTransitionMap.get(place);

        if(possibleTransitions.size() == 0)
            return null;

        Random random = new Random();
        String randomTransition = possibleTransitions.get(random.nextInt(possibleTransitions.size()));

        EdgeDto edge = new EdgeDto(place,randomTransition,0);
        possibleTransitions.remove(randomTransition);

        return edge;
    }

    private EdgeDto createEdgeFromTransitionToPlace(Map<String, List<String>> transitionToPlaceMap, String transition, String place)
    {
        List<String> possiblePlaces = transitionToPlaceMap.get(transition);

        if(!possiblePlaces.contains(place))
            return null;

        EdgeDto edge = new EdgeDto(transition,place,0);
        possiblePlaces.remove(place);

        return edge;
    }

    private EdgeDto createEdgeFromTransitionToRandomPlace(Map<String, List<String>> transitionToPlaceMap, String transition)
    {
        List<String> possiblePlaces = transitionToPlaceMap.get(transition);

        if(possiblePlaces.size() == 0)
            return null;

        Random random = new Random();
        String randomPlace = possiblePlaces.get(random.nextInt(possiblePlaces.size()));

        EdgeDto edge = new EdgeDto(transition,randomPlace,0);
        possiblePlaces.remove(randomPlace);

        return edge;
    }

    private EdgeDto createRandomEdgeConnectedWithPlace(Map<String, List<String>> placeToTransitionMap,
                                                       Map<String, List<String>> transitionToPlaceMap,
                                                       String place)
    {
//        Random random = new Random();
//        boolean fromPlace = random.nextBoolean();
//        int randomTransitionPosition = random.nextInt(transitions.length);
//        String randomTransition = transitions[randomTransitionPosition];
//
//        if(fromPlace)
//            return createEdgeFromPlaceToTransition(placeToTransitionMap, place, randomTransition);
//        else
//            return createEdgeFromTransitionToPlace(transitionToPlaceMap, randomTransition, place);

        Random random = new Random();
        boolean fromPlace = random.nextBoolean();

        EdgeDto edge = null;
        if(fromPlace)
        {
            edge = createEdgeFromPlaceToRandomTransition(placeToTransitionMap, place);
        }

        if(edge == null)
        {
            List<String> transitions = transitionToPlaceMap.keySet().stream().map(String::new).collect(Collectors.toList());
            if(transitions.size() == 0)
                return null;
            String randomTransition = transitions.get(random.nextInt(transitions.size()));
            edge = createEdgeFromTransitionToPlace(transitionToPlaceMap, randomTransition, place);
        }
        return edge;
    }

    private EdgeDto createRandomEdgeConnectedWithTransition(Map<String, List<String>> placeToTransitionMap,
                                                            Map<String, List<String>> transitionToPlaceMap,
                                                            String transition)
    {
//        Random random = new Random();
//        boolean fromPlace = random.nextBoolean();
//
//        int randomPlacesPosition = random.nextInt(places.length);
//        String randomPlace = places[randomPlacesPosition];
//
//        if(fromPlace)
//            return createEdgeFromPlaceToTransition(placeToTransitionMap, randomPlace, transition);
//        else
//            return createEdgeFromTransitionToPlace(transitionToPlaceMap, transition, randomPlace);

        Random random = new Random();
        boolean fromPlace = random.nextBoolean();

        EdgeDto edge = null;
        if(fromPlace)
        {
            edge = createEdgeFromTransitionToRandomPlace(transitionToPlaceMap, transition);
        }

        if(edge == null)
        {
            List<String> places = placeToTransitionMap.keySet().stream().map(String::new).collect(Collectors.toList());
            if(places.size() == 0)
                return null;
            String randomPlace = places.get(random.nextInt(places.size()));
            edge = createEdgeFromPlaceToTransition(placeToTransitionMap, randomPlace, transition);
        }

        return edge;
    }

    private String[] getRandomOrderOfId(List<String> ids)
    {
        String[] id = new String[ids.size()];
        Random random = new Random();
        int idPosition = 0;
        while (ids.size() > 0)
        {
            int position = random.nextInt(ids.size());
            id[idPosition] = ids.remove(position);
            idPosition++;
        }
        return id;
    }


    private static String[] connectIds(String[] places, String[] transitions, boolean startPlaces)
    {
        String[] ids = new String[places.length + transitions.length];

        int placePosition = 0;
        int transitionPosition = 0;

        int minCount = Integer.min(places.length, transitions.length);

        for(int i = 0; i < minCount * 2; i = i + 2)
        {
            if(startPlaces)
            {
                ids[i] = places[placePosition];
                placePosition++;
                ids[i+1] = transitions[transitionPosition];
                transitionPosition++;
            }
            else
            {
                ids[i] = transitions[transitionPosition];
                transitionPosition++;
                ids[i+1] = places[placePosition];
                placePosition++;
            }

        }

        int total = minCount * 2;

        for(int i = total; i < ids.length; i++)
        {
            if(placePosition == places.length)
            {
                ids[i] = transitions[transitionPosition];
                transitionPosition++;
            }
            else if(transitionPosition == transitions.length)
            {
                ids[i] = places[placePosition];
                placePosition++;
            }
        }

        return ids;

    }


    private Map<String, List<String>> createPlaceToTransitionMap(List<PlaceDto> places, List<String> transitions)
    {
        Map<String, List<String>> placeToTransitionMap = new HashMap<>();

        for(PlaceDto p : places)
        {
            placeToTransitionMap.put(p.getId(), new ArrayList<>(transitions));
        }

        return placeToTransitionMap;
    }

    private Map<String, List<String>> createTransitionToPlaceMap(List<PlaceDto> places, List<String> transitions)
    {
        Map<String, List<String>> transitionToPlaceMap = new HashMap<>();

        for(String transition : transitions)
        {
            transitionToPlaceMap.put(transition, places.stream().map(PlaceDto::getId).collect(Collectors.toList()));
        }

        return transitionToPlaceMap;
    }



}
