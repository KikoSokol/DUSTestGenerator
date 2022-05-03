package com.privateAPI.DUSTestGenerator.petri_nets.dto;

public class DefinitionPTIOM0
{
    private final PetriNetDto petriNet;


    public DefinitionPTIOM0(PetriNetDto petriNetDto) {
        this.petriNet = petriNetDto;
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

    public String getPlaces()
    {
        return stringListOfPlaces();
    }

    public String stringListOfTransitions()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{");
        for (TransitionDto transition : this.petriNet.getTransitions())
        {
            stringBuilder.append(transition.getName());
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

    public String getTransition()
    {
        return stringListOfTransitions();
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

    public String getStartMarking()
    {
        return stringListOfStartMarking();
    }

    public int[][] getInputMatrix()
    {
        int[][] inputMatrix = new int[this.petriNet.getPlaces().size()][this.petriNet.getTransitions().size()];

        for(int row = 0; row < inputMatrix.length; row++)
        {
            String place = this.petriNet.getPlaces().get(row).getId();

            for(int column = 0; column < inputMatrix[row].length; column++)
            {
                String transition = this.petriNet.getTransitions().get(column).getId();
                inputMatrix[row][column] = weightOfEdgeFromPlaceToTransition(place, transition);
            }
        }

        return inputMatrix;
    }

    public int[][] getOutputMatrix()
    {
        int[][] outputMatrix = new int[this.petriNet.getPlaces().size()][this.petriNet.getTransitions().size()];

        for(int row = 0; row < outputMatrix.length; row++)
        {
            String place = this.petriNet.getPlaces().get(row).getId();

            for(int column = 0; column < outputMatrix[row].length; column++)
            {
                String transition = this.petriNet.getTransitions().get(column).getId();
                outputMatrix[row][column] = weightOfEdgeFromTransitionToPlace(transition, place);
            }
        }

        return outputMatrix;
    }

    public int[][] getIncidentalMatrix()
    {
        int[][] incidentalMatrix = new int[this.petriNet.getPlaces().size()][this.petriNet.getTransitions().size()];

        int[][] inputMatrix = getInputMatrix();
        int[][] outputMatrix = getOutputMatrix();

        for(int row = 0; row < incidentalMatrix.length; row++)
        {
            for(int column = 0; column < incidentalMatrix[row].length; column++)
            {
                incidentalMatrix[row][column] = outputMatrix[row][column] - inputMatrix[row][column];
            }
        }

        return incidentalMatrix;
    }

    public int weightOfEdgeFromPlaceToTransition(String place, String transition)
    {
        for (EdgeDto edge : this.petriNet.getEdges())
        {
            if(edge.getFrom().compareTo(place) == 0 && edge.getTo().compareTo(transition) == 0)
                return edge.getWeight();
        }
        return 0;
    }

    public int weightOfEdgeFromTransitionToPlace(String transition, String place)
    {
        for (EdgeDto edge : this.petriNet.getEdges())
        {
            if(edge.getFrom().compareTo(transition) == 0 && edge.getTo().compareTo(place) == 0)
            {
                return edge.getWeight();
            }
        }
        return 0;
    }

    public PetriNetDto getPetriNet() {
        return petriNet;
    }
}
