package edu.aydin.sma;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataReader {

    public ArrayList<Data> readDataFile(String filename) throws IOException {
        ArrayList<Data> dataList = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        boolean isValidData = true;
        String line;
        String token;
        StringTokenizer tokenizer;
        int[] datas = new int[11];
        int index = 0;

        while ((line = bufferedReader.readLine()) != null){
            index = 0;
            isValidData = true;
            tokenizer = new StringTokenizer(line,",");
            while (tokenizer.hasMoreTokens()){
                token = tokenizer.nextToken();
                if(!token.equals("?")){
                    datas[index] = Integer.parseInt(token);
                    index++;
                }else{
                    isValidData = false;
                }
            }

            if(isValidData){
                Data data = new Data(datas);
                dataList.add(data);
            }
        }

        bufferedReader.close();
        Collections.sort(dataList);
        return dataList;
    }

    public DataSets prepareTrainingAndTestData(ArrayList<Data> dataList){

        ArrayList<Data> trainingDataList = new ArrayList<>();
        ArrayList<Data> testDataList = new ArrayList<>();

        int index = 0;
        int malignantCount = 0;
        int benignCount = 0;

        for(Data data : dataList){
            if (data.isMalignant)
                malignantCount++;
            else
                benignCount++;
        }

        for(int i=0 ; i<malignantCount/2; i++){
            trainingDataList.add(dataList.get(index));
            index++;
            testDataList.add(dataList.get(index));
            index++;
        }

        for(int i=0 ; i<benignCount/2; i++){
            trainingDataList.add(dataList.get(index));
            index++;
            testDataList.add(dataList.get(index));
            index++;
        }

        return new DataSets(trainingDataList,testDataList);
    }
}
