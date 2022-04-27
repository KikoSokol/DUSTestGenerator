package com.privateAPI.DUSTestGenerator.coverability_tree.dto;


import com.privateAPI.DUSTestGenerator.coverability_tree.domain.CoverabilityTreeState;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;

import java.util.Objects;

public class CoverabilityTreeGeneratorResultDto
{
    private int countOfDeletedCoverabilityTrees;
    private CoverabilityTreeDto coverabilityTree;
    private PetriNetDto petriNetDto;
    private CoverabilityTreeState state;

    public CoverabilityTreeGeneratorResultDto(int countOfDeletedCoverabilityTrees, CoverabilityTreeDto coverabilityTree,
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

    public CoverabilityTreeDto getCoverabilityTree() {
        return coverabilityTree;
    }

    public void setCoverabilityTree(CoverabilityTreeDto coverabilityTree) {
        this.coverabilityTree = coverabilityTree;
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
        if (o == null || getClass() != o.getClass()) return false;
        CoverabilityTreeGeneratorResultDto that = (CoverabilityTreeGeneratorResultDto) o;
        return getCountOfDeletedCoverabilityTrees() == that.getCountOfDeletedCoverabilityTrees() && Objects.equals(getCoverabilityTree(), that.getCoverabilityTree());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountOfDeletedCoverabilityTrees(), getCoverabilityTree());
    }
}
