package com.privateAPI.DUSTestGenerator.petri_nets.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrescriptionPnDto
{
    private final PetriNetDto petriNet;

    private final List<EdgeDto> shakeEdgesList;

//    private final String prescriptionPN;
//
//    private final String postsets;
//
//    private final String presets;

    public PrescriptionPnDto(PetriNetDto petriNet)
    {
        this.petriNet = petriNet;
        this.shakeEdgesList = shakeEdgeDtoList(this.petriNet.getEdges());
//        this.prescriptionPN = getInStringFullPrescriptionPN();
//        this.presets = getStringListOfPostsets();
//        this.postsets = getStringListOfPrestes();
    }

    public String stringListOfPlaces()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{");
        for(PlaceDto placeDto : this.petriNet.getPlaces())
        {
            stringBuilder.append(placeDto.getId());
            stringBuilder.append(", ");
        }

        if(stringBuilder.toString().compareTo("{") == 0)
        {
            stringBuilder.append("}");
        }
        else
        {
            stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "}");
        }

        return stringBuilder.toString();
    }

    public String stringListOfTransitions()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{");
        for (TransitionDto transition : this.petriNet.getTransitions())
        {
            stringBuilder.append(transition.getId());
            stringBuilder.append(", ");
        }

        if(stringBuilder.toString().compareTo("{") == 0)
        {
            stringBuilder.append("}");
        }
        else
        {
            stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "}");
        }

        return stringBuilder.toString();
    }

    public String stringListOfEdges(boolean withWeights)
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{");
        for (EdgeDto edge : this.shakeEdgesList)
        {
            stringBuilder.append(edge.getFrom());
            stringBuilder.append(edge.getTo());
            if(withWeights)
            {
                stringBuilder.append(": ");
                stringBuilder.append(edge.getWeight());
            }

            stringBuilder.append(", ");
        }

        if(stringBuilder.toString().compareTo("{") == 0)
        {
            stringBuilder.append("}");
        }
        else
        {
            stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "}");
        }


        return stringBuilder.toString();
    }

    public String stringListOfStartMarking()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("(");
        for (PlaceDto place : this.petriNet.getPlaces())
        {
            stringBuilder.append(place.getNumberOfTokens());
            stringBuilder.append(", ");
        }

        if(stringBuilder.toString().compareTo("(") == 0)
        {
            stringBuilder.append(")");
        }
        else
        {
            stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), ")");
        }

        return stringBuilder.toString();
    }


    public String getPrescriptionPN()
    {

        return stringListOfPlaces() +
                ",\n" +
                stringListOfTransitions() +
                ",\n" +
                stringListOfEdges(false) +
                ",\n" +
                stringListOfEdges(true) +
                ",\n" +
                stringListOfStartMarking();
    }


    private List<EdgeDto> shakeEdgeDtoList(List<EdgeDto> edges)
    {
        List<EdgeDto> tmp = new ArrayList<>(edges);
        List<EdgeDto> shakeEdges = new ArrayList<>();

        Random random = new Random();

        while (tmp.size() > 0)
        {
            int position = random.nextInt(tmp.size());
            shakeEdges.add(tmp.remove(position));
        }

        return shakeEdges;
    }


    public String stringListOfPostsets()
    {
        StringBuilder stringBuilder = new StringBuilder();

        for (TransitionDto transition : this.petriNet.getTransitions()) {
            stringBuilder.append(getPostsetOfTransition(transition));
            stringBuilder.append("\n");
        }

        if(stringBuilder.toString().length() > 0)
        {
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        }

        return stringBuilder.toString();
    }

    public String stringListOfPresets()
    {
        StringBuilder stringBuilder = new StringBuilder();

        for (TransitionDto transition : this.petriNet.getTransitions()) {
            stringBuilder.append(getPresetOfTransition(transition));
            stringBuilder.append("\n");
        }

        if(stringBuilder.toString().length() > 0)
        {
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        }

        return stringBuilder.toString();
    }


    private String getPostsetOfTransition(TransitionDto transition)
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(transition.getId());
        stringBuilder.append("• = {");

        List<String> places = new ArrayList<>();

        for (EdgeDto edge : this.petriNet.getEdges())
        {
            if(transition.getId().compareTo(edge.getFrom()) == 0)
            {
                places.add(edge.getTo());
            }
        }

        stringBuilder.append(places.toString().substring(1, places.toString().length() - 1));

        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    private String getPresetOfTransition(TransitionDto transition)
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("•");
        stringBuilder.append(transition.getId());
        stringBuilder.append(" = {");


        List<String> places = new ArrayList<>();

        for (EdgeDto edge : this.petriNet.getEdges())
        {
            if(transition.getId().compareTo(edge.getTo()) == 0)
            {
                places.add(edge.getFrom());
            }
        }

        stringBuilder.append(places.toString().substring(1, places.toString().length() - 1));

        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    public PetriNetDto getPetriNet() {
        return petriNet;
    }

    public String getPostsets() {
        return stringListOfPostsets();
    }

    public String getPresets() {
        return stringListOfPresets();
    }
}
