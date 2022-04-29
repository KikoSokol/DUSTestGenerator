package com.privateAPI.DUSTestGenerator.reachability_graph.dto;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraphState;

import java.util.Objects;

public class ReachabilityGraphGeneratorResultDto
{
    private int countOfDeletedReachabilityGraphs;
    private ReachabilityGraphDto reachabilityGraph;
    private PetriNetDto petriNet;
    private ReachabilityGraphState state;

    public ReachabilityGraphGeneratorResultDto(int countOfDeletedReachabilityGraphs,
                                               ReachabilityGraphDto reachabilityGraph,
                                               ReachabilityGraphState reachabilityGraphState) {
        this.countOfDeletedReachabilityGraphs = countOfDeletedReachabilityGraphs;
        this.reachabilityGraph = reachabilityGraph;
        this.state = reachabilityGraphState;
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

    public PetriNetDto getPetriNet() {
        return petriNet;
    }

    public void setPetriNet(PetriNetDto petriNet) {
        this.petriNet = petriNet;
    }

    public ReachabilityGraphState getState() {
        return state;
    }

    public void setState(ReachabilityGraphState state) {
        this.state = state;
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
