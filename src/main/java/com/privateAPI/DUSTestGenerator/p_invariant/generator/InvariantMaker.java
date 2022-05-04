package com.privateAPI.DUSTestGenerator.p_invariant.generator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.abs;

@Component
public class InvariantMaker {
    public double[][] generateTInvariant(int place, int transition, ArrayList<Integer> parametersIndexes) {
        double[][] invariant = declareInvariant(place, transition);
        ArrayList<Integer> possibleParametersIndexes = fillPossibleIndexes(invariant.length);

        findParamIndexes(parametersIndexes, possibleParametersIndexes, invariant[0].length);

        boolean biggerParam = false;

        for (int i = 0; i < invariant.length; i++) {
            if (parametersIndexes.contains(i)) {

                invariant[i][parametersIndexes.indexOf(i)] = fillInvariantParam(biggerParam);
                if (invariant[i][parametersIndexes.indexOf(i)] != 1)
                    biggerParam = true;

            } else {
                fillInvariant(invariant, i);
            }
        }
        return invariant;
    }

    private ArrayList<Integer> fillPossibleIndexes(int length) {
        ArrayList<Integer> possibleParametersIndexes = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            possibleParametersIndexes.add(i);
        }
        return possibleParametersIndexes;
    }

    private void findParamIndexes(ArrayList<Integer> parametersIndexes, ArrayList<Integer> possibleParametersIndexes,
                                  int length) {
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int generatedIndex = random.nextInt(possibleParametersIndexes.size());
            parametersIndexes.add(possibleParametersIndexes.get(generatedIndex));
            possibleParametersIndexes.remove(generatedIndex);
        }
    }

    public double fillInvariantParam(boolean biggerParam){
        Random random = new Random();
        int paramVal = 1;
        if (!biggerParam) {
            paramVal = random.nextInt(3) + 1;
        }
        return paramVal;
    }
    private void fillInvariant(double[][] invariant, int i) {
        Random random = new Random();

        for (int j = 0; j < invariant[0].length; j++) {
            invariant[i][j] = random.nextInt(6) - 3;
        }
    }

    public double[][] generatePInvariant(int place, int transition, ArrayList<Integer> parametersIndexes) {
        double[][] invariant = declareInvariant(transition, place);
        ArrayList<Integer> possibleParametersIndexes = fillPossibleIndexes(invariant.length);

        findParamIndexes(parametersIndexes, possibleParametersIndexes, invariant[0].length);

        boolean biggerParam = false;

        for (int i = 0; i < invariant.length; i++) {
            if (parametersIndexes.contains(i)) {

                invariant[i][parametersIndexes.indexOf(i)] = fillInvariantParam(biggerParam);
                if (invariant[i][parametersIndexes.indexOf(i)] != 1)
                    biggerParam = true;

            } else {
                do {
                    fillInvariant(invariant, i);
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

    public double[][] generateFalsePInvariant(int place, int transition, ArrayList<Integer> parametersIndexes) {
        Random random = new Random();

        int numNullRowsInInvariant = random.nextInt(Math.min(place, transition)) + 1;

        int numberOfParameters = randomNumberOfParameters(transition, place, numNullRowsInInvariant);
        if (numberOfParameters < 1)
            return new double[transition][1];

        double[][] invariant = new double[place][numberOfParameters];

        ArrayList<Integer> possibleParametersIndexes = fillPossibleIndexes(invariant.length);
        findParamIndexes(parametersIndexes, possibleParametersIndexes, invariant[0].length);
        removeParamIndexes(possibleParametersIndexes, numNullRowsInInvariant);

        boolean biggerParam = false;

        for (int i = 0; i < invariant.length; i++) {
            if (parametersIndexes.contains(i)) {

                invariant[i][parametersIndexes.indexOf(i)] = fillInvariantParam(biggerParam);
                if (invariant[i][parametersIndexes.indexOf(i)] != 1)
                    biggerParam = true;

            } else {
                if (!possibleParametersIndexes.contains(i))
                    fillInvariant(invariant, i);
            }
        }
        return invariant;
    }

    private void removeParamIndexes(ArrayList<Integer> possibleParametersIndexes, int numNullRowsInInvariant) {
        Random random = new Random();

        while (possibleParametersIndexes.size() > numNullRowsInInvariant) {
            possibleParametersIndexes.remove(random.nextInt(possibleParametersIndexes.size()));
        }
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
                        break;
                    }
                }

                if (notNullInRow(matrixC[editRow]))
                    matrixC[editRow] = rowValues.clone();
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
        for (double[] row : matrixC) {
            if (notNullInRow(row))
                nullRows++;
        }
        return nullRows;
    }

    private boolean notNullInRow(double[] row) {
        for (double val : row) {
            if (val != 0)
                return false;
        }
        return true;
    }

    public String[] invariantToString(double[][] invariant_double) {
        char[] alphabet =  {'a', 'b','c', 'd','e', 'f','g', 'h','i', 'j','k', 'l','m', 'n','o', 'p','q', 'r','s', 't','u', 'v','w', 'x','y', 'z' };
        printMatrix(invariant_double, "Inv");
        String[] invariant = new String[invariant_double.length];
        for (int row = 0; row < invariant_double.length; row++) {
            boolean first = true;
            StringBuilder val = new StringBuilder();
            for (int param = 0; param < invariant_double[0].length; param++) {
                if(invariant_double[row][param] != 0){
                    if(first){
                        first = false;
                    }
                    else {
                        if(invariant_double[row][param] > 0)
                            val.append("+");
                    }
                    val.append((int)invariant_double[row][param]);
                    val.append(alphabet[param]);
                }
            }
            invariant[row] = (Objects.equals(String.valueOf(val), ""))? "0" :  String.valueOf(val);

        }
        return invariant;
    }
}

