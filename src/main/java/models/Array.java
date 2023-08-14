package models;

public class Array {
    public static int[] removeDuplicates(int[] a){
        if(a.length<2) {
            return a;
        }
        int[] tmp = new int[a.length];
        int j=0;
        for(int i=0;i<a.length -1;i++){
            if(a[i]!=a[i+1]){
                tmp[j++]=a[i];
            }
        }
        tmp[j++]=a[a.length-1];
        if (j >= 0) System.arraycopy(tmp, 0, a, 0, j);
        int[] newA = new int[j];
        System.arraycopy(a, 0, newA, 0, j);
        return newA;
    }
    public static int[] convertStringIntoArray(String string){
        String[] splitArray = string.split(" ");
        int[] array = new int[splitArray.length];
        for(int i=0;i< splitArray.length;i++){
            array[i]=Integer.parseInt(splitArray[i]);
        }
        return array;
    }
}

