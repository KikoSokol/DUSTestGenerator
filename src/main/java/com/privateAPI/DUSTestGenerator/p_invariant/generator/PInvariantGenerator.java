package com.privateAPI.DUSTestGenerator.p_invariant.generator;

import java.util.ArrayList;
import java.util.Random;

public class PInvariantGenerator {
    private final PInvariantMaker pInvariantMaker;

    public PInvariantGenerator(PInvariantMaker pInvariantMaker) {
        this.pInvariantMaker = pInvariantMaker;
    }

    public double[][] PInvariantGeneratorResult() {
        return makeRandomPInvariant(8, 4, false);
    }

    private double[][] makeRandomPInvariant(int place, int transition, boolean limitedness) {
        ArrayList<Integer> parametersIndexes = new ArrayList<>();
        double[][] pInvariant = (limitedness) ?
                generatePInvariant(place, transition, parametersIndexes) :
                generateFalsePInvariant(place, transition, parametersIndexes);

        double[][] matrixC = calculateMatrixC(transition, pInvariant, parametersIndexes);
        return matrixC;
    }


    private double[][] generatePInvariant(int place, int transition, ArrayList<Integer> parametersIndexes) {
        Random random = new Random();
        int maxNumOfParameters = place - 1;
        int minNumOfParameters = (place > transition) ? place - transition : 1;
        int numberOfParameters = random.nextInt(maxNumOfParameters - minNumOfParameters) + minNumOfParameters;
        double[][] PInvariant = new double[place][numberOfParameters];
        ArrayList<Integer> possibleParametersIndexes = new ArrayList<>();
        for (int i = 0; i < place; i++) {
            possibleParametersIndexes.add(i);
        }
        for (int i = 0; i < numberOfParameters; i++) {
            int generatedIndex = random.nextInt(possibleParametersIndexes.size());
            parametersIndexes.add(possibleParametersIndexes.get(generatedIndex));
            possibleParametersIndexes.remove(generatedIndex);
        }
        for (int i = 0; i < PInvariant.length; i++) {
            if (parametersIndexes.contains(i)) {

                PInvariant[i][parametersIndexes.indexOf(i)] = random.nextInt(3) + 1;
            } else {

                for (int j = 0; j < PInvariant[0].length; j++) {
                    PInvariant[i][j] = random.nextInt(6) - 3;
                }
            }
        }
        return PInvariant;
    }

    private double[][] generateFalsePInvariant(int place, int transition, ArrayList<Integer> parametersIndexes) {
        Random random = new Random();

        int maxNullRows = Math.min(place, transition);
        int numNullRowsInInvariant = random.nextInt(maxNullRows) + 1;

        int maxNumOfParameters = place - numNullRowsInInvariant;
        int minNumOfParameters = (place > transition) ? place - transition : 0;
        int numberOfParameters = random.nextInt(maxNumOfParameters - minNumOfParameters + 1) + minNumOfParameters;

        if (numberOfParameters < 1)
            return new double[place][1];

        double[][] PInvariant = new double[place][numberOfParameters];
        ArrayList<Integer> possibleParametersIndexes = new ArrayList<>();

        for (int colId = 0; colId < place; colId++) {
            possibleParametersIndexes.add(colId);
        }

        for (int i = 0; i < numberOfParameters; i++) {
            int generatedIndex = random.nextInt(possibleParametersIndexes.size());
            parametersIndexes.add(possibleParametersIndexes.get(generatedIndex));
            possibleParametersIndexes.remove(generatedIndex);
        }

        while (possibleParametersIndexes.size() > numNullRowsInInvariant) {
            possibleParametersIndexes.remove(random.nextInt(possibleParametersIndexes.size()));
        }

        for (int i = 0; i < PInvariant.length; i++) {
            if (parametersIndexes.contains(i)) {

                PInvariant[i][parametersIndexes.indexOf(i)] = random.nextInt(3) + 1;
            } else {
                if (!possibleParametersIndexes.contains(i))
                    for (int j = 0; j < PInvariant[0].length; j++) {
                        PInvariant[i][j] = random.nextInt(6) - 3;
                    }
            }
        }
        return PInvariant;
    }

    private double[][] calculateMatrixC(int transition, double[][] pInvariant, ArrayList<Integer> parametersIndexes) {
        double[][] matrixC = fillMatrix(transition, pInvariant, parametersIndexes);
        editMatrixDueInvariant(pInvariant, parametersIndexes, matrixC);
        editToIncidentalMatrix(matrixC, matrixC[0].length - parametersIndexes.size());
        return matrixC;
    }


    private double[][] fillMatrix(int transition, double[][] pInvariant, ArrayList<Integer> parametersIndexes) {
        double[][] matrixC = new double[transition][pInvariant.length];
        int pivotNum = 0;
        for (int i = 0; i < pInvariant.length; i++) {
            if (!parametersIndexes.contains(i) ) {
                matrixC[pivotNum][i] = 1;
                if (parametersIndexes.size() > 0) {
                    for (int j = 0; j < pInvariant[0].length; j++) {
                        matrixC[pivotNum][parametersIndexes.get(j)] = -pInvariant[i][j];
                    }
                }
                pivotNum++;
            }
        }
        return matrixC;
    }


    private void editMatrixDueInvariant(double[][] pInvariant, ArrayList<Integer> parametersIndexes, double[][] matrixC) {
        for (int i = 0; i < parametersIndexes.size(); i++) {
            if (pInvariant[parametersIndexes.get(i)][i] != 1) {
                for (int row = 0; row < matrixC[0].length - parametersIndexes.size(); row++) {
                    if(pInvariant[row][i] != 0) {
                        for (int col = 0; col < matrixC[0].length; col++) {
                            if (matrixC[row][col] != 0 && col != parametersIndexes.get(i))
                                matrixC[row][col] *= pInvariant[parametersIndexes.get(i)][i];
                        }
                    }
                }
            }
        }
    }


    private void editToIncidentalMatrix(double[][] matrixC, int notNullRows) {
        int iteration = 0;
        while (iteration < 10  || matrixHasNullRows(matrixC) > 0 || biggestMatrixVal(matrixC) > 10.0){
//            najdi nsn /nsd pre riadok s najvacsim cislom/ vacsim ako 10,
//            najdi najvacsi prvok a odcitaj od neho daco z jeho stlpca
            iteration++;
        }
    }

    private double biggestMatrixVal(double[][] matrixC) {
        double maxVal = matrixC[0][0];
        for (double[] rows : matrixC) {
            for (double val : rows) {
                if (maxVal < Math.abs(val))
                    maxVal = Math.abs(val);

            }
        }
        return maxVal;
    }

    private int matrixHasNullRows(double[][] matrixC) {
        int nullRows = 0;
        for (double[] rows : matrixC) {
            int sum = 0;
            for (double val : rows) {
                sum += val;
                if(sum > 0)
                    break;
            }
            if(sum == 0)
                nullRows++;
        }
        return nullRows;
    }
}
