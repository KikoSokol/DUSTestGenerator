package com.privateAPI.DUSTestGenerator.workflow.generator;

import com.privateAPI.DUSTestGenerator.workflow.controller.request.WorkflowGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.EdgeDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PlaceDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.TransitionDto;

import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class WorkflowGenerator {
    public PetriNetDto generateRandomWorkflow(WorkflowGeneratorRequest request)
    {
        PlaceDto inputPlace = new PlaceDto(1, "INPUT", false);
        PlaceDto outputPlace = new PlaceDto(0, "OUTPUT", false);

        TransitionDto afterInputPlaceTransition = new TransitionDto("IT");
        TransitionDto beforeOutputPlaceTransition = new TransitionDto("OT");

        List<EdgeDto> edges = new ArrayList<>();
        edges.add(new EdgeDto(inputPlace.getId(), afterInputPlaceTransition.getId(), 1));
        edges.add(new EdgeDto(beforeOutputPlaceTransition.getId(), outputPlace.getId(), 1));

        List<PlaceDto> places = generatePlaces(request.getMinPlaces(), request.getMaxPlaces());
        List<TransitionDto> transitions = generateTransitions(request.getMinTransition(), request.getMaxTransition());


        edges = connectWorkflow(edges, afterInputPlaceTransition, places, transitions, beforeOutputPlaceTransition,
                outputPlace);


        places.add(inputPlace);
        places.add(outputPlace);

        transitions.add(afterInputPlaceTransition);
        transitions.add(beforeOutputPlaceTransition);

        return new PetriNetDto(places, transitions, edges);
    }

    private List<EdgeDto> connectWorkflow(List<EdgeDto> edges, TransitionDto afterInputPlaceTransition,
                                          List<PlaceDto> places, List<TransitionDto> transitions,
                                          TransitionDto beforeOutputPlaceTransition, PlaceDto outputPlace)
    {
        List<PlaceDto> freePlaces = new ArrayList<>(places);
        List<TransitionDto> freeTransitions = new ArrayList<>(transitions);


        List<PlaceDto> connectedPlaces = new ArrayList<>();
        List<TransitionDto> connectedTransitions = new ArrayList<>();


        List<PlaceDto> newConnectedPlaces = new ArrayList<>();
        List<TransitionDto> newConnectedTransitions = new ArrayList<>();
        newConnectedTransitions.add(afterInputPlaceTransition);

        // 1 - last is transitions, 0 - last is places
        int last = 0;
        do {
            if (freePlaces.size() != 0) {
                newConnectedPlaces = connectPlaces(edges, newConnectedTransitions, freePlaces, connectedPlaces);
                freePlaces.removeAll(newConnectedPlaces);
                connectedPlaces.addAll(newConnectedPlaces);
                last = 0;
            }

            if (freeTransitions.size() != 0) {
                newConnectedTransitions = connectTransitions(edges, newConnectedPlaces, freeTransitions,
                        connectedTransitions);
                freeTransitions.removeAll(newConnectedTransitions);
                connectedTransitions.addAll(newConnectedTransitions);
                last = 1;
            }

        } while (freePlaces.size() != 0 || freeTransitions.size() != 0);

        if(last == 0)
            connectLastPlacesToEndWorkflow(edges, newConnectedPlaces, beforeOutputPlaceTransition);
        else
        {
            List<PlaceDto> lastPlaces = new ArrayList<>();
            int addedPlaces = 0;
            for(int i = newConnectedPlaces.size() - 1; i >= 0; i--)
            {
                lastPlaces.add(newConnectedPlaces.get(i));
                addedPlaces++;
                if(addedPlaces == 2)
                    break;
            }
            connectLastTransitionToEndWorkflow(edges, newConnectedTransitions, lastPlaces, beforeOutputPlaceTransition,
                    outputPlace);
        }

        System.out.println(last);
        return edges;

    }

    private void connectLastPlacesToEndWorkflow(List<EdgeDto> edges, List<PlaceDto> lastPlaces,
                                                TransitionDto beforeOutputPlaceTransition)
    {
        for(PlaceDto placeDto : lastPlaces)
        {
            edges.add(new EdgeDto(placeDto.getId(), beforeOutputPlaceTransition.getId(), 1));
        }
    }

    private void connectLastTransitionToEndWorkflow(List<EdgeDto> edges, List<TransitionDto> lastTransitions,
                                                    List<PlaceDto> lastPlaces,
                                                    TransitionDto beforeOutputPlaceTransition,
                                                    PlaceDto outputPlace)
    {
        for(TransitionDto transitionDto : lastTransitions)
        {
            edges.add(new EdgeDto(transitionDto.getId(), outputPlace.getId(), 1));
        }

        Random random = new Random();
        PlaceDto placeToBeforeOutputPlaceTransition = lastPlaces.get(random.nextInt(lastPlaces.size()));

        edges.add(new EdgeDto(placeToBeforeOutputPlaceTransition.getId(), beforeOutputPlaceTransition.getId(), 1));
    }


    private List<PlaceDto> connectPlaces(List<EdgeDto> edges, List<TransitionDto> connectedTransition,
                                         List<PlaceDto> freePlaces, List<PlaceDto> connectedPlaces)
    {

        Set<PlaceDto> places = new HashSet<>();

        for(TransitionDto transitionDto : connectedTransition)
        {
            places.addAll(connectTransitionToPlaces(edges, transitionDto, freePlaces, connectedPlaces));
        }

        return new ArrayList<>(places);
    }

    private List<PlaceDto> connectTransitionToPlaces(List<EdgeDto> edges, TransitionDto transitionDto,
                                                    List<PlaceDto> freePlaces, List<PlaceDto> connectedPlaces)
    {
        Random random = new Random();

        List<PlaceDto> tmpPlaces = new ArrayList<>(freePlaces);

        int maxPossibleFromFreePlaces = Math.max(freePlaces.size() / 2, 1);
        int countFromFreePlaces = random.nextInt((maxPossibleFromFreePlaces - 1) + 1) + 1;

        List<PlaceDto> connectPlaces = new ArrayList<>();

        for(int i = 0; i < countFromFreePlaces; i++)
        {
//            PlaceDto placeDto = tmpPlaces.get(random.nextInt(tmpPlaces.size()));
//            tmpPlaces.remove(placeDto);
//            edges.add(new EdgeDto(transitionDto.getId(), placeDto.getId(), 1));
//            connectPlaces.add(placeDto);
            connectPlaces.add(connectTransitionToRandomPlace(edges, transitionDto, tmpPlaces));
        }

        int countToConnectedPlaces  = random.nextInt(3);

        countToConnectedPlaces = Math.min(connectedPlaces.size(), countToConnectedPlaces);

        if(countToConnectedPlaces > 0)
        {
            List<PlaceDto> tmpConnectedPlaces = new ArrayList<>(connectedPlaces);

            for(int i = 0; i < countToConnectedPlaces; i++)
            {
                connectTransitionToRandomPlace(edges, transitionDto, tmpConnectedPlaces);
            }
        }

        return connectPlaces;
    }

    private PlaceDto connectTransitionToRandomPlace(List<EdgeDto> edges, TransitionDto transitionDto,
                                                    List<PlaceDto> possiblePlaces)
    {
        Random random = new Random();
        PlaceDto placeDto = possiblePlaces.get(random.nextInt(possiblePlaces.size()));
        possiblePlaces.remove(placeDto);

        edges.add(new EdgeDto(transitionDto.getId(), placeDto.getId(), 1));
        return placeDto;
    }

    private List<TransitionDto> connectTransitions(List<EdgeDto> edges, List<PlaceDto> connectedPlaces,
                                         List<TransitionDto> freeTransitions, List<TransitionDto> connectedTransitions)
    {

        Set<TransitionDto> transitions = new HashSet<>();

        for(PlaceDto placeDto : connectedPlaces)
        {
            transitions.addAll(connectPlaceToTransitions(edges, placeDto, freeTransitions, connectedTransitions));
        }

        return new ArrayList<>(transitions);
    }

    private List<TransitionDto> connectPlaceToTransitions(List<EdgeDto> edges, PlaceDto placeDto,
                                                          List<TransitionDto> freeTransitions,
                                                          List<TransitionDto> connectedTransitions)
    {
        Random random = new Random();

        List<TransitionDto> tmpTransitions = new ArrayList<>(freeTransitions);

        int maxPossibleFromFreeTransitions = Math.max(freeTransitions.size() / 2, 1);
        int countFromFreeTransitions = random.nextInt((maxPossibleFromFreeTransitions - 1) + 1) + 1;

        List<TransitionDto> connectTransitions = new ArrayList<>();


        for(int i = 0; i < countFromFreeTransitions; i++)
        {
//            TransitionDto transitionDto = tmpTransitions.get(random.nextInt(tmpTransitions.size()));
//            tmpTransitions.remove(transitionDto);
//            edges.add(new EdgeDto(placeDto.getId(), transitionDto.getId(), 1));
            connectTransitions.add(connectPlaceToRandomTransition(edges, placeDto, tmpTransitions));
        }


//        int countToConnectedTransition = random.nextInt(3);
//        countToConnectedTransition = Math.min(connectedTransitions.size(), countToConnectedTransition);
//
//        if(countToConnectedTransition > 0)
//        {
//
//            List<TransitionDto> tmpConnectedTransitions = new ArrayList<>(connectedTransitions);
//            for(int i = 0; i < countToConnectedTransition; i++)
//            {
//                connectPlaceToRandomTransition(edges, placeDto, tmpConnectedTransitions);
//            }
//        }


        return connectTransitions;

    }

    private TransitionDto connectPlaceToRandomTransition(List<EdgeDto> edges, PlaceDto placeDto,
                                                         List<TransitionDto> possibleTransitions)
    {
        Random random = new Random();
        TransitionDto transitionDto = possibleTransitions.get(random.nextInt(possibleTransitions.size()));
        possibleTransitions.remove(transitionDto);

        edges.add(new EdgeDto(placeDto.getId(), transitionDto.getId(), 1));

        return transitionDto;
    }



    public List<PlaceDto> generatePlaces(int minPlaces, int maxPlaces)
    {
        List<PlaceDto> places = new ArrayList<>();

        Random random = new Random();
        int countOfPlaces = random.nextInt((maxPlaces + 1) - minPlaces) + minPlaces;

        char name = 97;
        for (int i = 0; i < countOfPlaces; i++) {
            PlaceDto place = new PlaceDto(0, "" + name, false);
            places.add(place);
            name++;
        }

        return places;
    }

    private List<TransitionDto> generateTransitions(int minTransition, int maxTransition)
    {
        List<TransitionDto> transitions = new ArrayList<>();
        Random random = new Random();
        int countOfTransitions = random.nextInt((maxTransition + 1) - minTransition) + minTransition;

        char name = 65;
        for (int i = 0; i < countOfTransitions; i++) {
            transitions.add(new TransitionDto("" + name));
            name++;
        }

        return transitions;
    }

}
