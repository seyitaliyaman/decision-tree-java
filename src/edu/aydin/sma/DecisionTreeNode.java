package edu.aydin.sma;

import java.util.ArrayList;

public class DecisionTreeNode {

    int dataIndex;
    int distinctionPoint;
    ArrayList<Data> dataList;
    DecisionTreeNode minNode;
    DecisionTreeNode maxNode;

    boolean malignant;

    boolean[] availableIndexes;

    public DecisionTreeNode(ArrayList<Data> dataList, boolean[] rootIndexes){
        this.dataList = dataList;

        availableIndexes = new boolean[10];
        for (int i=0; i<10; i++){
            availableIndexes[i] = rootIndexes[i];
        }

        dataIndex = -1;
        distinctionPoint = -1;
        malignant = false;
    }


}
