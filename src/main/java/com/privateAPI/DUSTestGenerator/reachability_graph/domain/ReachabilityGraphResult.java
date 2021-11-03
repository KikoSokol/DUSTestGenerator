package com.privateAPI.DUSTestGenerator.reachability_graph.domain;

public class ReachabilityGraphResult
{
    private boolean bounded;
    private ReachabilityGraph reachabilityGraph;

    public ReachabilityGraphResult(boolean bounded) {
        this.bounded = bounded;
        reachabilityGraph = null;
    }

    public ReachabilityGraphResult(boolean bounded, ReachabilityGraph reachabilityGraph) {
        this.bounded = bounded;
        this.reachabilityGraph = reachabilityGraph;
    }
}
