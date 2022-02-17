package com.privateAPI.DUSTestGenerator.coverability_tree.domain;

public class CoverabilityTreeMakerResult
{
    private CoverabilityTreeState coverabilityTreeState;
    private CoverabilityTree coverabilityTree;

    public CoverabilityTreeMakerResult(CoverabilityTreeState coverabilityTreeState, CoverabilityTree coverabilityTree) {
        this.coverabilityTreeState = coverabilityTreeState;
        this.coverabilityTree = coverabilityTree;
    }

    public CoverabilityTreeState getCoverabilityTreeState() {
        return coverabilityTreeState;
    }

    public void setCoverabilityTreeState(CoverabilityTreeState coverabilityTreeState) {
        this.coverabilityTreeState = coverabilityTreeState;
    }

    public CoverabilityTree getCoverabilityTree() {
        return coverabilityTree;
    }

    public void setCoverabilityTree(CoverabilityTree coverabilityTree) {
        this.coverabilityTree = coverabilityTree;
    }
}
