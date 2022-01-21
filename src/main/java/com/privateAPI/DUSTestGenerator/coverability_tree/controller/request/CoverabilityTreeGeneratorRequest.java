package com.privateAPI.DUSTestGenerator.coverability_tree.controller.request;

public class CoverabilityTreeGeneratorRequest
{
    private int minVertices;
    private int maxVertices;

    private int minPlaces;
    private int maxPlaces;

    private int minCountEdges;
    private int maxCountEdges;

    public CoverabilityTreeGeneratorRequest(int minVertices, int maxVertices, int minPlaces, int maxPlaces, int minCountEdges, int maxCountEdges) {
        this.minVertices = minVertices;
        this.maxVertices = maxVertices;
        this.minPlaces = minPlaces;
        this.maxPlaces = maxPlaces;
        this.minCountEdges = minCountEdges;
        this.maxCountEdges = maxCountEdges;
    }

    public int getMinVertices() {
        return minVertices;
    }

    public void setMinVertices(int minVertices) {
        this.minVertices = minVertices;
    }

    public int getMaxVertices() {
        return maxVertices;
    }

    public void setMaxVertices(int maxVertices) {
        this.maxVertices = maxVertices;
    }

    public int getMinPlaces() {
        return minPlaces;
    }

    public void setMinPlaces(int minPlaces) {
        this.minPlaces = minPlaces;
    }

    public int getMaxPlaces() {
        return maxPlaces;
    }

    public void setMaxPlaces(int maxPlaces) {
        this.maxPlaces = maxPlaces;
    }

    public int getMinCountEdges() {
        return minCountEdges;
    }

    public void setMinCountEdges(int minCountEdges) {
        this.minCountEdges = minCountEdges;
    }

    public int getMaxCountEdges() {
        return maxCountEdges;
    }

    public void setMaxCountEdges(int maxCountEdges) {
        this.maxCountEdges = maxCountEdges;
    }
}
