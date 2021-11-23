package com.privateAPI.DUSTestGenerator.reachability_graph.controller.request;

import java.util.Objects;

public class ReachabilityGraphGeneratorRequest
{
    private int minVertices;
    private int maxVertices;

    private int minPlaces;
    private int maxPlaces;

    private int minCountEdges;
    private int maxCountEdges;

    public ReachabilityGraphGeneratorRequest(int minVertices, int maxVertices, int minPlaces, int maxPlaces, int minCountEdges, int maxCountEdges) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReachabilityGraphGeneratorRequest)) return false;
        ReachabilityGraphGeneratorRequest that = (ReachabilityGraphGeneratorRequest) o;
        return getMinVertices() == that.getMinVertices() && getMaxVertices() == that.getMaxVertices() && getMinPlaces() == that.getMinPlaces() && getMaxPlaces() == that.getMaxPlaces() && getMinCountEdges() == that.getMinCountEdges() && getMaxCountEdges() == that.getMaxCountEdges();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMinVertices(), getMaxVertices(), getMinPlaces(), getMaxPlaces(), getMinCountEdges(), getMaxCountEdges());
    }
}
