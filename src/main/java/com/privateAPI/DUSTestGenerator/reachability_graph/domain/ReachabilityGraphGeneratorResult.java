package com.privateAPI.DUSTestGenerator.reachability_graph.domain;

import java.util.Objects;

public class ReachabilityGraphGeneratorResult
{
    private int countOfDeletedReachabilityGraphs;
    private ReachabilityGraph reachabilityGraph;
    private ReachabilityGraphState state;

    public ReachabilityGraphGeneratorResult(int countOfDeletedReachabilityGraphs, ReachabilityGraph reachabilityGraph,
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

    public ReachabilityGraph getReachabilityGraph() {
        return reachabilityGraph;
    }

    public void setReachabilityGraph(ReachabilityGraph reachabilityGraph) {
        this.reachabilityGraph = reachabilityGraph;
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
        if (!(o instanceof ReachabilityGraphGeneratorResult)) return false;
        ReachabilityGraphGeneratorResult that = (ReachabilityGraphGeneratorResult) o;
        return getCountOfDeletedReachabilityGraphs() == that.getCountOfDeletedReachabilityGraphs() && Objects.equals(getReachabilityGraph(), that.getReachabilityGraph());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountOfDeletedReachabilityGraphs(), getReachabilityGraph());
    }
}
