package edu.aydin.sma;

import java.util.ArrayList;

public class DecisionTree {

    DecisionTreeNode rootNode;
    public DecisionTree(ArrayList<Data> dataList){
        boolean[] availableIndexes = new boolean[10];
        for(int i=0; i<10; i++){
            availableIndexes[i] = true;
        }
        availableIndexes[0] = false;
        rootNode = new DecisionTreeNode(dataList,availableIndexes);
    }

    public void getLeafResult(DecisionTreeNode decisionTreeNode){
        if(decisionTreeNode.minNode == null && decisionTreeNode.maxNode == null){
            int malignantCount = 0;
            int benignCount = 0;
            for(Data data: decisionTreeNode.dataList){
                if(data.isMalignant){
                    malignantCount++;
                }else{
                    benignCount++;
                }
            }
            if(malignantCount >= benignCount){
                decisionTreeNode.malignant = true;
            }else{
                decisionTreeNode.malignant = false;
            }
        }else{
            getLeafResult(decisionTreeNode.minNode);
            getLeafResult(decisionTreeNode.maxNode);
        }

    }

    public void train(){
        createTree(rootNode);
        getLeafResult(rootNode);
    }

    public boolean guessData(Data data){
        return test(data,rootNode);
    }

    private boolean test(Data data, DecisionTreeNode decisionTreeNode){

        if(decisionTreeNode.minNode==null && decisionTreeNode.maxNode==null){
            return decisionTreeNode.malignant;
        }else{
            if(data.data[decisionTreeNode.dataIndex] <= decisionTreeNode.distinctionPoint){
                return test(data,decisionTreeNode.minNode);
            }else{
                return test(data,decisionTreeNode.maxNode);
            }
        }
    }

    public void createTree(DecisionTreeNode decisionTreeNode){
        int bestDataIndex = -1;
        float bestEntropy = 5;
        int bestDistinctionPoint = -1;

        for(int i=0; i<10; i++){
            if(decisionTreeNode.availableIndexes[i]){
                float[] distinctionPointAndEntropy = findBestDistinctionPoint(decisionTreeNode.dataList,i);
                if(distinctionPointAndEntropy[1] <= bestEntropy){
                    bestDataIndex = i;
                    bestEntropy = distinctionPointAndEntropy[1];
                    bestDistinctionPoint = (int)distinctionPointAndEntropy[0];
                }
            }
        }
        if(bestDataIndex != -1){
            decisionTreeNode.availableIndexes[bestDataIndex] = false;
            decisionTreeNode.distinctionPoint = bestDistinctionPoint;
            decisionTreeNode.dataIndex = bestDataIndex ;
            decisionTreeNode.minNode = new DecisionTreeNode(new ArrayList<Data>(), decisionTreeNode.availableIndexes);
            decisionTreeNode.maxNode = new DecisionTreeNode(new ArrayList<Data>(), decisionTreeNode.availableIndexes);
            splitDataList(decisionTreeNode.dataList,bestDataIndex,bestDistinctionPoint, decisionTreeNode.minNode.dataList,decisionTreeNode.maxNode.dataList);
            createTree(decisionTreeNode.minNode);
            createTree(decisionTreeNode.maxNode);
        }

    }

    public void splitDataList(ArrayList<Data> dataList, int dataIndex, int distinctionPoint, ArrayList<Data> mins,ArrayList<Data> maxs){
        if(dataIndex >= 10 || dataIndex<0){
            return;
        }
        for(Data data: dataList){
            if(data.data[dataIndex] <= distinctionPoint){
                mins.add(data);
            }else{
                maxs.add(data);
            }
        }
    }

    public int[] splitListForBestDistinctionPoint(ArrayList<Data> dataList,int dataIndex, int distinctionPoint){
        if(dataIndex >= 10 || dataIndex<0){
            return null;
        }
        int malignantCountMins = 0;
        int benignCountMins = 0;
        int malignantCountMaxs = 0;
        int benignCountMaxs = 0;
        for(Data data: dataList){
            if(data.data[dataIndex] <= distinctionPoint){
                if(data.isMalignant){
                    malignantCountMins++;
                }else{
                    benignCountMins++;
                }
            }else {
                if(data.isMalignant){
                    malignantCountMaxs++;
                }else{
                    benignCountMaxs++;
                }
            }
        }
        int[] results = new int[4];
        results[0] = malignantCountMins;
        results[1] = benignCountMins;
        results[2] = malignantCountMaxs;
        results[3] = benignCountMaxs;
        return results;
    }

    public float[] findBestDistinctionPoint(ArrayList<Data> dataList, int dataIndex){

        float[] distinctionPointAndEntropy = new float[2];
        int bestDistinctionPoint = 1;
        int[] results = splitListForBestDistinctionPoint(dataList,dataIndex,bestDistinctionPoint);
        float bestEntropy = calculateEntropy(results);
        float tempEntropy;

        for(int i=2; i<=10; i++){
            results = splitListForBestDistinctionPoint(dataList,dataIndex,i);
            tempEntropy = calculateEntropy(results);
            if(tempEntropy < bestEntropy){
                bestEntropy = tempEntropy;
                bestDistinctionPoint = i;
            }
        }

        distinctionPointAndEntropy[0] = bestDistinctionPoint;
        distinctionPointAndEntropy[1] = bestEntropy;

        return distinctionPointAndEntropy;
    }

    public float calculateEntropy(int[] listResults){
        float entropy;

        float malignantCountMins = listResults[0];
        float benignCountMins = listResults[1];
        float malignantCountMaxs = listResults[2];
        float benignCountMaxs = listResults[3];

        float minBenignRate = malignantCountMins / (malignantCountMins + benignCountMins);
        float minMalignantRate = 1f - minBenignRate;

        float maxBenignRate = benignCountMaxs / (malignantCountMaxs + benignCountMaxs);
        float maxMalignantRate = 1f - maxBenignRate;


        if(minBenignRate >= maxBenignRate){
            entropy = -minBenignRate * logaritmicFunction(minBenignRate) - maxMalignantRate * logaritmicFunction(maxMalignantRate);
        }else{
            entropy = -minMalignantRate * logaritmicFunction(minMalignantRate) - maxBenignRate * logaritmicFunction(maxBenignRate);
        }

        return entropy;
    }

    private float logaritmicFunction(float x){
        if(x == 0f) {
            return 0f;
        } else {
            return (float) (Math.log((double)x)/Math.log((double)2));
        }
    }

    public DecisionTreeNode getRootNode() {
        return rootNode;
    }
}
