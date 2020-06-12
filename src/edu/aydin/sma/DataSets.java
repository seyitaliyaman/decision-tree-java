package edu.aydin.sma;

import java.util.ArrayList;

public class DataSets {

    private ArrayList<Data> trainingList;
    private ArrayList<Data> testList;

    DataSets(ArrayList<Data> trainingList,ArrayList<Data> testList){
        this.trainingList = trainingList;
        this.testList = testList;
    }

    public ArrayList<Data> getTrainingList() {
        return trainingList;
    }

    public ArrayList<Data> getTestList() {
        return testList;
    }
}
