package com.privateAPI.DUSTestGenerator.p_invariant.generator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.log;

@Component
public class InvariantMaker {

    //PRE T INVARIANT
//    int maxNumOfParameters = place - 1;
//        if (maxNumOfParameters > place / 2) {
//        maxNumOfParameters = place / 2;
//    }
//    int minNumOfParameters = (place > transition) ? place - transition : 1;
//    int bound = maxNumOfParameters - minNumOfParameters;
//        if(bound <1)//mozem toto<<<<????
//    bound = 1;
//    int numberOfParameters = random.nextInt(bound) + minNumOfParameters; //chyba- 3,3 bound je zaporne/ 0
//    double[][] PInvariant = new double[place][numberOfParameters];
//    boolean biggerParam = false;
//    ArrayList<Integer> possibleParametersIndexes = new ArrayList<>();
//        for (int i = 0; i < place; i++) {
//        possibleParametersIndexes.add(i);
//    }

    public double[][] generateTInvariant(int place, int transition, ArrayList<Integer> parametersIndexes) {
        Random random = new Random();
        double[][] invariant = declareInvariant(place, transition);
        boolean biggerParam = false;
        ArrayList<Integer> possibleParametersIndexes = new ArrayList<>();
        for (int i = 0; i < invariant.length; i++) {
            possibleParametersIndexes.add(i);
        }
        for (int i = 0; i < invariant[0].length; i++) {
            int generatedIndex = random.nextInt(possibleParametersIndexes.size());
            parametersIndexes.add(possibleParametersIndexes.get(generatedIndex));
            possibleParametersIndexes.remove(generatedIndex);
        }
        for (int i = 0; i < invariant.length; i++) {
            if (parametersIndexes.contains(i)) {
                int paramVal = 1;
                if (!biggerParam) {
                    paramVal = random.nextInt(3) + 1;
                    if (paramVal != 1)
                        biggerParam = true;
                }
                invariant[i][parametersIndexes.indexOf(i)] = paramVal;

            } else {

                for (int j = 0; j < invariant[0].length; j++) {
                    invariant[i][j] = random.nextInt(6) - 3;
                }
            }
        }
        return invariant;
    }


    public double[][] generatePInvariant(int place, int transition, ArrayList<Integer> parametersIndexes) {
        Random random = new Random();
        double[][] invariant = declareInvariant(transition, place);
        boolean biggerParam = false;
        ArrayList<Integer> possibleParametersIndexes = new ArrayList<>();
        for (int i = 0; i < invariant.length; i++) {
            possibleParametersIndexes.add(i);
        }
        for (int i = 0; i < invariant[0].length; i++) {
            int generatedIndex = random.nextInt(possibleParametersIndexes.size());
            parametersIndexes.add(possibleParametersIndexes.get(generatedIndex));
            possibleParametersIndexes.remove(generatedIndex);
        }
        for (int i = 0; i < invariant.length; i++) {
            if (parametersIndexes.contains(i)) {
                int paramVal = 1;
                if (!biggerParam) {
                    paramVal = random.nextInt(3) + 1;
                    if (paramVal != 1)
                        biggerParam = true;
                }
                invariant[i][parametersIndexes.indexOf(i)] = paramVal;

            } else {
                do {
                    for (int j = 0; j < invariant[0].length; j++) {
                        invariant[i][j] = random.nextInt(6) - 3;
                    }
                } while (notNullInRow(invariant[i]));
            }
        }
        return invariant;
    }

    private double[][] declareInvariant(int cMatrixRow, int cMatrixCol) {
        Random random = new Random();

        int maxNumOfParameters = cMatrixCol - 1;
        if (maxNumOfParameters > cMatrixCol / 2) {
            maxNumOfParameters = cMatrixCol / 2;
        }
        int minNumOfParameters = (cMatrixCol > cMatrixRow) ? cMatrixCol - cMatrixRow : 1;
        int bound = maxNumOfParameters - minNumOfParameters;
        if (bound < 1)
            bound = 1;
        int numberOfParameters = random.nextInt(bound) + minNumOfParameters;
        return new double[cMatrixCol][numberOfParameters];
    }

    private boolean notNullInRow(double[] InvariantRow) {
        for (double val : InvariantRow) {
            if (val != 0)
                return false;
        }
        return true;
    }

    //PRE T INVARIANT

//    Random random = new Random();
//
//    int maxNullRows = Math.min(place, transition);
//    int numNullRowsInInvariant = random.nextInt(maxNullRows) + 1;
//
//    int maxNumOfParameters = place - numNullRowsInInvariant;
//        if (maxNumOfParameters > place / 2) {
//        maxNumOfParameters = place / 2;
//    }
//    int minNumOfParameters = (place > transition) ? place - transition : 0;
//    int boundForNumOfParameters = maxNumOfParameters - minNumOfParameters + 1;
//    int numberOfParameters = random.nextInt(boundForNumOfParameters) + minNumOfParameters;
//    boolean biggerParam = false;
//        if (numberOfParameters < 1)
//            return new double[place][1];
//
//    double[][] PInvariant = new double[place][numberOfParameters];
//    ArrayList<Integer> possibleParametersIndexes = new ArrayList<>();
//
//        for (int colId = 0; colId < place; colId++) {
//        possibleParametersIndexes.add(colId);
//    }


    public double[][] generateFalsePInvariant(int place, int transition, ArrayList<Integer> parametersIndexes) {
        Random random = new Random();

        int maxNullRows = Math.min(place, transition);
        int numNullRowsInInvariant = random.nextInt(maxNullRows) + 1;

        int numberOfParameters = randomNumberOfParameters(transition, place, numNullRowsInInvariant);
        if (numberOfParameters < 1)
            return new double[transition][1];
        double[][] invariant = new double[place][numberOfParameters];
        boolean biggerParam = false;
        ArrayList<Integer> possibleParametersIndexes = new ArrayList<>();

        for (int colId = 0; colId < invariant.length; colId++) {
            possibleParametersIndexes.add(colId);
        }

        for (int i = 0; i < invariant[0].length; i++) {
            int generatedIndex = random.nextInt(possibleParametersIndexes.size());
            parametersIndexes.add(possibleParametersIndexes.get(generatedIndex));
            possibleParametersIndexes.remove(generatedIndex);
        }

        while (possibleParametersIndexes.size() > numNullRowsInInvariant) {
            possibleParametersIndexes.remove(random.nextInt(possibleParametersIndexes.size()));
        }

        for (int i = 0; i < invariant.length; i++) {
            if (parametersIndexes.contains(i)) {
                int paramVal = 1;
                if (!biggerParam) {
                    paramVal = random.nextInt(3) + 1;
                    if (paramVal != 1)
                        biggerParam = true;
                }
                invariant[i][parametersIndexes.indexOf(i)] = paramVal;
            } else {
                if (!possibleParametersIndexes.contains(i))
                    for (int j = 0; j < invariant[0].length; j++) {
                        invariant[i][j] = random.nextInt(6) - 3;
                    }
            }
        }
        return invariant;
    }

    private int randomNumberOfParameters(int cMatrixRow, int cMatrixCol, int numNullRowsInInvariant) {
        Random random = new Random();

        int maxNumOfParameters = cMatrixCol - numNullRowsInInvariant;
        if (maxNumOfParameters > cMatrixCol / 2) {
            maxNumOfParameters = cMatrixCol / 2;
        }
        int minNumOfParameters = (cMatrixCol > cMatrixRow) ? cMatrixCol - cMatrixRow : 0;
        int boundForNumOfParameters = maxNumOfParameters - minNumOfParameters + 1;
        return random.nextInt(boundForNumOfParameters) + minNumOfParameters;
    }

    public double[][] calculateAndTransposeMatrixC(int matrixCRow, double[][] invariant, ArrayList<Integer> parametersIndexes) {

        double[][] matrixC = transposeMatrix(calculateMatrixC(matrixCRow, invariant, parametersIndexes));
        printMatrix(matrixC, "transpose: ");

        return matrixC;
    }

    public double[][] calculateMatrixC(int matrixCRow, double[][] invariant, ArrayList<Integer> parametersIndexes) {
        double[][] matrixC = fillMatrix(matrixCRow, invariant, parametersIndexes);
        editMatrixDueInvariant(invariant, parametersIndexes, matrixC);
        editToIncidentalMatrix(matrixC);


        return matrixC;
    }

    private double[][] transposeMatrix(double[][] matrixC) {
        double[][] transposeMatrixC = new double[matrixC[0].length][matrixC.length];
        for (int row = 0; row < matrixC.length; row++) {
            for (int col = 0; col < matrixC[0].length; col++) {
                transposeMatrixC[col][row] = matrixC[row][col];
            }
        }
        return transposeMatrixC;
    }


    private double[][] fillMatrix(int matrixCRow, double[][] pInvariant, ArrayList<Integer> parametersIndexes) {
        double[][] matrixC = new double[matrixCRow][pInvariant.length];
        int pivotNum = 0;
        for (int i = 0; i < pInvariant.length; i++) {
            if (!parametersIndexes.contains(i)) { //parametersIndexes =  null
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
                    if (pInvariant[row][i] != 0) {
                        for (int col = 0; col < matrixC[0].length; col++) {
                            if (matrixC[row][col] != 0 && col != parametersIndexes.get(i))
                                matrixC[row][col] *= pInvariant[parametersIndexes.get(i)][i];
                        }
                    }
                }
            }
        }
    }


    private void editToIncidentalMatrix(double[][] matrixC) {
        printMatrix(matrixC, "start: ");
        makeRowsNotNull(matrixC);
        printMatrix(matrixC, "makeRowsNotNull: ");

        checkValuesOfMatrix(matrixC);
        printMatrix(matrixC, "checkValuesOfMatrix: ");
        mixRows(matrixC);
        printMatrix(matrixC, "mixRows: ");
        checkValuesOfMatrix(matrixC);
        printMatrix(matrixC, "checkValuesOfMatrix: ");


    }

    private void printMatrix(double[][] matrixC, String s) {
        System.out.println(s);
        for (double[] row : matrixC) {
            for (double val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    private void checkValuesOfMatrix(double[][] matrixC) {
        int iteration = 0;

        while (iteration < 10 && (matrixHasNullRows(matrixC) > 0 || biggestMatrixVal(matrixC) > 10.0)) {
            divideBigRows(matrixC);
            makeRowsNotNull(matrixC);

            iteration++;
        }
    }

    private void mixRows(double[][] matrixC) {
        Random random = new Random();

        //POZOR!! nech sa ten isty neodcita od seba
        int editRows = random.nextInt(matrixC.length) + 1;
        for (int editRow = 0; editRow < editRows; editRow++) {
            int rowCombination = random.nextInt(4) + 1;
            while (rowCombination-- > 0) {
                int multiplicity = random.nextInt(7) - 3;
                if (multiplicity == 0) multiplicity += 1;
                int row = random.nextInt(matrixC.length);
                double[] rowValues = matrixC[editRow].clone();
                for (int col = 0; col < matrixC[0].length; col++) {
                    matrixC[editRow][col] += matrixC[row][col] * multiplicity;
                    if (abs(matrixC[editRow][col]) > 15) {
                        matrixC[editRow] = rowValues.clone();
                    }
                }
            }
        }
    }


    private void makeRowsNotNull(double[][] matrixC) {
        Random random = new Random();
        List<Integer> nullRows = new ArrayList<>();
        List<Integer> notNullRows = new ArrayList<>();
        exploreRows(matrixC, nullRows, notNullRows);
        for (int nullRow : nullRows) {
            int rowCombination = random.nextInt(4) + 1;
            while (rowCombination-- > 0) {
                int multiplicity = random.nextInt(7) - 3;
                if (multiplicity == 0) multiplicity += 1;
                int row = notNullRows.get(random.nextInt(notNullRows.size()));
                for (int col = 0; col < matrixC[0].length; col++) {
                    matrixC[nullRow][col] += matrixC[row][col] * multiplicity;
                }
            }
        }
    }

    private void exploreRows(double[][] matrixC, List<Integer> nullRows, List<Integer> notNullRows) {
        for (int row = 0; row < matrixC.length; row++) {
            boolean nullRow = true;
            for (double val : matrixC[row]) {
                if (val != 0) {
                    notNullRows.add(row);
                    nullRow = false;
                    break;
                }
            }
            if (nullRow)
                nullRows.add(row);
        }
    }


    private double gcd(double a, double b) {
        while (b > 0) {
            double temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }

    public double divisor(double[] input) {
        double result = abs(input[0]);
        for (int i = 1; i < input.length; i++) {
            result = gcd(result, abs(input[i]));
        }
        return result;
    }

    private void divideBigRows(double[][] matrixC) {
        for (int row = 0; row < matrixC.length; row++) {
            for (double val : matrixC[row]) {
                if (abs(val) > 10.0) {
                    double divisor = divisor(matrixC[row]);
                    if (divisor != 0) {
                        for (int col = 0; col < matrixC[0].length; col++)
                            matrixC[row][col] /= divisor;
                    }

                }
            }
        }
    }

    private double biggestMatrixVal(double[][] matrixC) {
        double maxVal = abs(matrixC[0][0]);
        for (double[] rows : matrixC) {
            for (double val : rows) {
                if (maxVal < abs(val))
                    maxVal = abs(val);

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
                if (sum > 0)
                    break;
            }
            if (sum == 0)
                nullRows++;
        }
        return nullRows;
    }
}
