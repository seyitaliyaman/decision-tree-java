package edu.aydin.sma;

public class Data implements Comparable<Data>{

    int[] data;
    boolean isMalignant;
    int length = 10;

    public Data(int[] incomingData){

        this.data = new int[length];
        for(int i=0; i<length; i++)
            data[i] = incomingData[i];

        if(incomingData[length] == 2){
            isMalignant = false;
        }else{
            isMalignant = true;
        }
    }


    @Override
    public String toString() {

        String result = "";
        for(int i=0; i<length; i++){
            result += data[i]+" ";
        }

        return (result+= "isMalignant "+isMalignant);

    }

    public boolean result(){
        return isMalignant;
    }

    @Override
    public int compareTo(Data o) {
        if(o.isMalignant){
            return -1;
        }else if(!o.isMalignant){
            return 1;
        }else{
            return 0;
        }
    }
}
