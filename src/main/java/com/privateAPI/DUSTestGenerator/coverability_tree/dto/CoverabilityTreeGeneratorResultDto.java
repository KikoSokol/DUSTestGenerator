package com.privateAPI.DUSTestGenerator.coverability_tree.dto;


import java.util.Objects;

public class CoverabilityTreeGeneratorResultDto
{
    private int countOfDeletedCoverabilityTrees;
    private CoverabilityTreeDto coverabilityTree;

    public CoverabilityTreeGeneratorResultDto(int countOfDeletedCoverabilityTrees, CoverabilityTreeDto coverabilityTree) {
        this.countOfDeletedCoverabilityTrees = countOfDeletedCoverabilityTrees;
        this.coverabilityTree = coverabilityTree;
    }

    public int getCountOfDeletedCoverabilityTrees() {
        return countOfDeletedCoverabilityTrees;
    }

    public void setCountOfDeletedCoverabilityTrees(int countOfDeletedCoverabilityTrees) {
        this.countOfDeletedCoverabilityTrees = countOfDeletedCoverabilityTrees;
    }

    public CoverabilityTreeDto getCoverabilityTree() {
        return coverabilityTree;
    }

    public void setCoverabilityTree(CoverabilityTreeDto coverabilityTree) {
        this.coverabilityTree = coverabilityTree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoverabilityTreeGeneratorResultDto that = (CoverabilityTreeGeneratorResultDto) o;
        return getCountOfDeletedCoverabilityTrees() == that.getCountOfDeletedCoverabilityTrees() && Objects.equals(getCoverabilityTree(), that.getCoverabilityTree());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountOfDeletedCoverabilityTrees(), getCoverabilityTree());
    }
}
