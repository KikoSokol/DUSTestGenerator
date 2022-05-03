package com.privateAPI.DUSTestGenerator.coverability_tree.domain;

import java.util.Objects;

public class CoverabilityTreeGeneratorResult
{
    private int countOfDeletedCoverabilityTrees;
    private CoverabilityTree coverabilityTree;
    private CoverabilityTreeState state;

    public CoverabilityTreeGeneratorResult(int countOfDeletedCoverabilityTrees, CoverabilityTree coverabilityTree,
                                           CoverabilityTreeState coverabilityTreeState) {
        this.countOfDeletedCoverabilityTrees = countOfDeletedCoverabilityTrees;
        this.coverabilityTree = coverabilityTree;
        this.state = coverabilityTreeState;
    }

    public int getCountOfDeletedCoverabilityTrees() {
        return countOfDeletedCoverabilityTrees;
    }

    public void setCountOfDeletedCoverabilityTrees(int countOfDeletedCoverabilityTrees) {
        this.countOfDeletedCoverabilityTrees = countOfDeletedCoverabilityTrees;
    }

    public CoverabilityTree getCoverabilityTree() {
        return coverabilityTree;
    }

    public void setCoverabilityTree(CoverabilityTree coverabilityTree) {
        this.coverabilityTree = coverabilityTree;
    }

    public CoverabilityTreeState getState() {
        return state;
    }

    public void setState(CoverabilityTreeState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoverabilityTreeGeneratorResult that = (CoverabilityTreeGeneratorResult) o;
        return getCountOfDeletedCoverabilityTrees() == that.getCountOfDeletedCoverabilityTrees() && Objects.equals(getCoverabilityTree(), that.getCoverabilityTree());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountOfDeletedCoverabilityTrees(), getCoverabilityTree());
    }
}
