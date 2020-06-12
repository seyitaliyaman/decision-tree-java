package edu.aydin.sma;

import java.io.IOException;
import java.util.ArrayList;

public class MainClass {

    public static void main(String[] args) throws IOException {

        DataReader dataReader = new DataReader();
        ArrayList<Data> dataList = dataReader.readDataFile("breast-cancer-wisconsin.data");

        DataSets dataSets = dataReader.prepareTrainingAndTestData(dataList);

        ArrayList<Data> trainSet = dataSets.getTrainingList();
        ArrayList<Data> testSet = dataSets.getTestList();

        System.out.println("Training Set:");
        for(Data data : trainSet){
            System.out.println(data);
        }
        System.out.println("----------------------");
        System.out.println("----------------------");
        System.out.println("Test Set:");
        for(Data data : testSet){
            System.out.println(data);
        }
        DecisionTree decisionTree = new DecisionTree(dataSets.getTrainingList());
        decisionTree.train();

        System.out.println("----------------------");
        System.out.println("----------------------");
        System.out.println("Results of training set:");
        float trueGuess = 0;
        float falseGuess = 0;
        for(Data data : trainSet){
            boolean resultOfGuess = decisionTree.guessData(data);
            if(resultOfGuess){
                if(data.isMalignant== resultOfGuess){
                    trueGuess++;
                }else{
                    falseGuess++;
                }
            }else{
                if(data.isMalignant == resultOfGuess){
                    trueGuess++;
                }else{
                    falseGuess++;
                }
            }
        }

        System.out.println("Doğru sayısı " + trueGuess
                + " ve yanlış sayısı " + falseGuess);

        System.out.println("Doğruluk oranı: "
                + (trueGuess/(trueGuess+falseGuess)));
        System.out.println("----------------------");
        System.out.println("----------------------");
        System.out.println("Results of test set:");

        float trueGuessTest = 0;
        float falseGuessTest = 0;
        for(Data data : testSet){
            boolean resultOfGuess = decisionTree.guessData(data);
            if(resultOfGuess){
                if(data.isMalignant== resultOfGuess){
                    trueGuessTest++;
                }else{
                    falseGuessTest++;
                }
            }else{
                if(data.isMalignant == resultOfGuess){
                    trueGuessTest++;
                }else{
                    falseGuessTest++;
                }
            }
        }
        System.out.println("Doğru sayısı " + trueGuessTest
                + " ve yanlış sayısı " + falseGuessTest);

        System.out.println("Doğruluk oranı: "
                + (trueGuessTest/(trueGuessTest+falseGuessTest)));



    }
}
