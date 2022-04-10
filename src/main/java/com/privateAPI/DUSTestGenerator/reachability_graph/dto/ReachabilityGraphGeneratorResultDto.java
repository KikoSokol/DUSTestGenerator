package com.privateAPI.DUSTestGenerator.reachability_graph.dto;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;

import java.util.Objects;

public class ReachabilityGraphGeneratorResultDto
{
    private int countOfDeletedReachabilityGraphs;
    private ReachabilityGraphDto reachabilityGraph;
    private PetriNetDto petriNetDto;

    public ReachabilityGraphGeneratorResultDto(int countOfDeletedReachabilityGraphs, ReachabilityGraphDto reachabilityGraph) {
        this.countOfDeletedReachabilityGraphs = countOfDeletedReachabilityGraphs;
        this.reachabilityGraph = reachabilityGraph;
    }

    public int getCountOfDeletedReachabilityGraphs() {
        return countOfDeletedReachabilityGraphs;
    }

    public void setCountOfDeletedReachabilityGraphs(int countOfDeletedReachabilityGraphs) {
        this.countOfDeletedReachabilityGraphs = countOfDeletedReachabilityGraphs;
    }

    public ReachabilityGraphDto getReachabilityGraph() {
        return reachabilityGraph;
    }

    public void setReachabilityGraph(ReachabilityGraphDto reachabilityGraph) {
        this.reachabilityGraph = reachabilityGraph;
    }

    public PetriNetDto getPetriNetDto() {
        return petriNetDto;
    }

    public void setPetriNetDto(PetriNetDto petriNetDto) {
        this.petriNetDto = petriNetDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReachabilityGraphGeneratorResultDto)) return false;
        ReachabilityGraphGeneratorResultDto that = (ReachabilityGraphGeneratorResultDto) o;
        return getCountOfDeletedReachabilityGraphs() == that.getCountOfDeletedReachabilityGraphs() && Objects.equals(getReachabilityGraph(), that.getReachabilityGraph());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountOfDeletedReachabilityGraphs(), getReachabilityGraph());
    }
}
