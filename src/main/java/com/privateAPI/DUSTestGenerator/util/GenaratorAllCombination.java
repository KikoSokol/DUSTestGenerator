package com.privateAPI.DUSTestGenerator.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GenaratorAllCombination
{
    private final List<int[]> generatedCombination;
    private final int lengthOfArray;
    private final int countOfAllCombination;
    private final int countOfValue;

    public GenaratorAllCombination(int minNumber, int maxNumber, int lengthOfArray)
    {
        this.generatedCombination = new ArrayList<>();
        this.lengthOfArray = lengthOfArray;
        this.countOfValue = maxNumber - minNumber + 1;
        this.countOfAllCombination = (int) Math.pow(this.countOfValue, this.lengthOfArray);

        generateCombination(minNumber, maxNumber);
    }

    public int getActualCountOfCombination()
    {
        return this.generatedCombination.size();
    }

    public List<int[]> getGeneratedCombination() {
        return generatedCombination;
    }

    public int getLengthOfArray() {
        return lengthOfArray;
    }

    public int getCountOfAllCombination() {
        return countOfAllCombination;
    }

    public int[] getRandomCombinationAndRemove()
    {
        if(this.generatedCombination.size() == 0)
            return null;

        Random random = new Random();
        int randomIndex = random.nextInt(this.generatedCombination.size());
        return this.generatedCombination.remove(randomIndex);
    }

    public int[] getRandomCombination()
    {
        Random random = new Random();
        int randomIndex = random.nextInt(this.generatedCombination.size());

        return this.generatedCombination.get(randomIndex);
    }

    public void removeCombinationsWithAllPositiveNumbers()
    {
        List<int[]> positiveArrays = new ArrayList<>();

        for (int[] ints : this.generatedCombination)
        {
            if(isAllPositiveNumberInArray(ints))
                positiveArrays.add(ints);
        }

        this.generatedCombination.removeAll(positiveArrays);
    }

    private boolean isAllPositiveNumberInArray(int[] array)
    {
        if(isAllNumberInArrayZero(array))
            return false;

        for (int i : array)
        {
            if(i < 0)
                return false;
        }
        return true;
    }

    private boolean isAllNumberInArrayZero(int[] array)
    {
        for (int i : array) {
            if(i != 0)
                return false;
        }
        return true;
    }

    private void generateCombination(int minNumber, int maxNumber)
    {
        int[] array = generateStartArray(minNumber, lengthOfArray);
        this.generatedCombination.add(array);

        for(int i = 0; i < countOfAllCombination - 1; i++)
        {
            array = addNewCombination(this.generatedCombination.
                    get(this.generatedCombination.size() - 1), minNumber, maxNumber);
            this.generatedCombination.add(array);
        }

    }

    private int[] addNewCombination(int[] previousCombination, int minNumber, int maxNumber)
    {
        int[] correctArray = changeArrayToCorrect(previousCombination, minNumber, maxNumber);

        int[] newCombination = new int[correctArray.length];

        if (correctArray.length - 1 >= 0)
            System.arraycopy(correctArray, 0, newCombination, 0, correctArray.length - 1);

        if(isAbleToIncrease())
            newCombination[correctArray.length - 1] = correctArray[correctArray.length - 1] + 1;
        else
            newCombination[correctArray.length - 1] = minNumber;

        return newCombination;
    }

    private int[] changeArrayToCorrect(int[] array, int minNumber, int maxNumber)
    {
        int maxIndex = indexWithMaxNumber(array, maxNumber);
        if(maxIndex < array.length - 1 && array[array.length - 1] != maxNumber)
        {
            return array;
        }

        int[] correctArray = new int[array.length];

        int upIndex = maxIndex - 1;


        for(int i = 0; i < array.length; i++)
        {
            if(i < upIndex)
                correctArray[i] = array[i];
            else if(i == upIndex)
                correctArray[i] = array[i] + 1;
            else
                correctArray[i] = minNumber;
        }

        return correctArray;
    }

    private int indexWithMaxNumber(int[] array, int maxNumber)
    {
        for(int i = array.length - 1; i >= getCountOfFirstMaxValue(array, maxNumber); i--)
        {
            if(array[i] == maxNumber && array[i - 1] < maxNumber)
                return i;
        }

        return -1;
    }


    private int[] generateStartArray(int min, int lengthOfArray)
    {
        int[] array = new int[lengthOfArray];
        Arrays.fill(array, min);
        return array;
    }

    private boolean isAbleToIncrease()
    {
        return this.generatedCombination.size() % this.countOfValue != 0;
    }

    private int getCountOfFirstMaxValue(int[] array, int maxNumber)
    {
        int count = 0;

        for (int j : array) {
            if (j == maxNumber)
                count++;
            else
                break;
        }

        return count;
    }


}
