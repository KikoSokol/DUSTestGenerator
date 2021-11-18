package com.privateAPI.DUSTestGenerator.reachability_graph.domain;

public class ReachabilityGraphMakerResult
{
    private ReachabilityGraphState state;
    private ReachabilityGraph reachabilityGraph;


    public ReachabilityGraphMakerResult(ReachabilityGraphState reachabilityGraphState, ReachabilityGraph reachabilityGraph) {
        this.state = reachabilityGraphState;
        this.reachabilityGraph = reachabilityGraph;
    }

    public ReachabilityGraphState getState() {
        return state;
    }

    public void setState(ReachabilityGraphState state) {
        this.state = state;
    }

    public ReachabilityGraph getReachabilityGraph() {
        return reachabilityGraph;
    }

    public void setReachabilityGraph(ReachabilityGraph reachabilityGraph) {
        this.reachabilityGraph = reachabilityGraph;
    }
}
