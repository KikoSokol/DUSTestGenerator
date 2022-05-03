package com.privateAPI.DUSTestGenerator.workflow.generator;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.EdgeDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PlaceDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.TransitionDto;

import java.util.ArrayList;
import java.util.List;

public class ComplementaryPlaceMaker
{

    public PetriNetDto makeComplementaryPlaces(PetriNetDto workflow)
    {

        List<PlaceDto> staticPlaces = getStaticPlaces(workflow);

        if(staticPlaces.size() == 0)
            return workflow;

        PetriNetDto workflowWithComplementaryPlaces = getDeepCopyOf(workflow);

        for(PlaceDto staticPlace : staticPlaces)
        {
            createAndAddComplementaryPlace(staticPlace, workflow, workflowWithComplementaryPlaces);
        }


        return workflowWithComplementaryPlaces;

    }


    private void createAndAddComplementaryPlace(PlaceDto staticPlace, PetriNetDto workflow,
                                                PetriNetDto workflowWithComplementaryPlaces)
    {
        PlaceDto complementaryPlace = new PlaceDto(0, "k_" + staticPlace.getId(), false);

        workflowWithComplementaryPlaces.getPlaces().add(complementaryPlace);

        for(EdgeDto edgeDto : workflow.getEdges())
        {
            if(edgeDto.getFrom().compareTo(staticPlace.getId()) == 0)
            {
                EdgeDto newEdge = new EdgeDto(edgeDto.getTo(), complementaryPlace.getId(), 1);
                workflowWithComplementaryPlaces.getEdges().add(newEdge);
            }
            else if(edgeDto.getTo().compareTo(staticPlace.getId()) == 0)
            {
                EdgeDto newEdge = new EdgeDto(complementaryPlace.getId(), edgeDto.getFrom(), 1);
                workflowWithComplementaryPlaces.getEdges().add(newEdge);
            }

        }
    }

    private List<PlaceDto> getStaticPlaces(PetriNetDto workflow)
    {
        List<PlaceDto> staticPlaces = new ArrayList<>();

        for(PlaceDto placeDto : workflow.getPlaces())
        {
            if(placeDto.isStatic())
                staticPlaces.add(placeDto);
        }

        return staticPlaces;
    }

    private PetriNetDto getDeepCopyOf(PetriNetDto workflow)
    {
        PetriNetDto newWorkflow = new PetriNetDto();

        for(PlaceDto placeDto : workflow.getPlaces())
        {
            PlaceDto newPlace = new PlaceDto(placeDto.getNumberOfTokens(), placeDto.getId(), placeDto.isStatic());
            newWorkflow.getPlaces().add(newPlace);
        }

        for(TransitionDto transitionDto : workflow.getTransitions())
        {
            TransitionDto newTransition = new TransitionDto(transitionDto.getId(), transitionDto.getName());
            newWorkflow.getTransitions().add(newTransition);
        }

        for(EdgeDto edgeDto : workflow.getEdges())
        {
            EdgeDto newEdge = new EdgeDto(edgeDto.getFrom(), edgeDto.getTo(), edgeDto.getWeight());
            newWorkflow.getEdges().add(newEdge);
        }

        return newWorkflow;
    }

}
