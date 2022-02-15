package com.privateAPI.DUSTestGenerator.p_invariant.generator;

import java.util.ArrayList;
import java.util.Random;

public class PInvariantGenerator {
    private final PInvariantMaker pInvariantMaker;

    public PInvariantGenerator(PInvariantMaker pInvariantMaker) {
        this.pInvariantMaker = pInvariantMaker;
    }

    public double[][] PInvariantGeneratorResult() {
        return makeRandomPInvariant(4, 4, true);
    }

    private double[][] makeRandomPInvariant(int place, int transition, boolean limitedness) {
        ArrayList<Integer> parametersIndexes = new ArrayList<>();
        double[][] pInvariant = generatePInvariant(place, transition, parametersIndexes);

        double[][] matrixC = calculateMatrixC(transition, pInvariant, parametersIndexes);
        return matrixC;
    }


    private double[][] generatePInvariant(int place, int transition, ArrayList<Integer> parametersIndexes) {
        Random random = new Random();
        int maxNumOfParameters = place - 1;
        int minNumOfParameters = (place > transition) ? place - transition : 1;
        int numberOfParameters = random.nextInt(maxNumOfParameters - minNumOfParameters) + minNumOfParameters;
        double[][] PInvariant = new double[place][numberOfParameters];
        ArrayList<Integer> possibleParametersIndeces = new ArrayList<>();
        for (int i = 0; i < place; i++) {
            possibleParametersIndeces.add(i);
        }
        for (int i = 0; i < numberOfParameters; i++) {
            int generatedIndex = random.nextInt(possibleParametersIndeces.size());
            parametersIndexes.add(possibleParametersIndeces.get(generatedIndex));
            possibleParametersIndeces.remove(generatedIndex);
        }
        for (int i = 0; i < PInvariant.length; i++) {
            if (parametersIndexes.contains(i)) {

                PInvariant[i][parametersIndexes.indexOf(i)] = random.nextInt(3)+1;
            } else {

                for (int j = 0; j < PInvariant[0].length; j++) {
                    PInvariant[i][j] = random.nextInt(6) - 3;
                }
            }
        }
        return PInvariant;
    }

    private double[][] calculateMatrixC(int transition, double[][] pInvariant, ArrayList<Integer> parametersIndexes) {
        double[][] matrixC = new double[transition][pInvariant.length];
        int pivotNum = 0;

        for (int i = 0; i < pInvariant.length; i++) {
            if (!parametersIndexes.contains(i)) {
                matrixC[pivotNum][i] = 1;
                for (int j = 0; j < pInvariant[0].length; j++) {
                    matrixC[pivotNum][parametersIndexes.get(j)] = pInvariant[i][j];
                }
                pivotNum++;
            }
        }

        for (int i = 0; i< parametersIndexes.size(); i++){
            if(parametersIndexes.get(i) != 1) {
                for (int j = 0; j < transition - parametersIndexes.size(); j++) {
                    matrixC[j][i] /= pInvariant[parametersIndexes.get(i)][i]; //nemozem delit, treba vynasobit vsetko?
                }
            }
        }
        return matrixC;
    }


}
