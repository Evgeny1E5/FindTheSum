package com.company;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;

public class AutoNearbySearch implements Runnable {
    private ArrayList<Integer> list = new ArrayList<>();
    private int suma;

    public AutoNearbySearch(int suma, ArrayList<Integer> list) {
        this.list = list;
        this.suma = suma;

    }

    @Override
    public void run() {
        int random = 0,
                countWrites = 0,
                bigSuma = 0,
                minimalDel = Integer.MAX_VALUE,
                sumaToSearch = 0,
                countOfAttempts = 0,
                savedCountOfAttempts = 0,
                savedAllSums2Size = 0;
        boolean firstRepeat = true;
        Boolean end = true, endHomieCycle = false;
        ArrayList<Integer> count = new ArrayList<Integer>();
        ArrayList<Integer> tempArr = new ArrayList<Integer>();
        HashSet<Integer> allSums = new HashSet<>();
        HashSet<Integer> allSums2 = new HashSet<>();
        //    ArrayList<Integer> allSums = new ArrayList<Integer>();
       /* for (int a : list) {
            bigSuma += a;
        }
        while (true) {
            int suma = 0;
            count.clear();
            for (; ; ) {
                random = (0 + (int) (Math.random() * list.size()));

                if (!count.contains(random)) {
                    count.add(random);
                    suma += list.get(random);
                    countOfAttempts++;
                }
                if (!allSums.contains(suma)) {
                    allSums.add(suma);
                    if (countOfAttempts  > Main.combinations*Main.combinations) endHomieCycle = true;


                }

                if (bigSuma == suma) break;


            }
            if (endHomieCycle) break;
        }*/

        //новый способ поиска с дублями
        countOfAttempts=0;

        while (true) {
            //if(countOfAttempts % 10 == 0) System.out.println(countOfAttempts);
            boolean endHomieCycle2 = false;
            int a = 0,b=0;
            int sumaCycle = 0;
            count.clear();
            tempArr.clear();
            tempArr.addAll(list);
            countOfAttempts++;
            for (; ; ) {
                a = (b + (int) (Math.random() * tempArr.size()));

                if (tempArr.size() > 0) {
                    count.add(tempArr.get(a));
                    sumaCycle += tempArr.get(a);
                    tempArr.remove(a);

                }
                if (!allSums2.contains(sumaCycle)) {
                    allSums2.add(sumaCycle);
                }
                    if (allSums2.size()==savedAllSums2Size) {
                        if(firstRepeat){
                            savedCountOfAttempts = countOfAttempts;
                            firstRepeat=false;
                        }
                        if(savedCountOfAttempts+Main.combinations==countOfAttempts){
                            endHomieCycle2 = true;
                        }
                    } else  {
                            savedAllSums2Size = allSums2.size();
                            firstRepeat=true;
                            }

                if (sumaCycle > suma) break;
            }
                if(endHomieCycle2) break;
            }

        for(int i: allSums2){
            if (i<suma)  minimalDel = Math.min(suma-i,minimalDel);
            if (i>suma)  minimalDel = Math.min(i-suma,minimalDel);
            }
        if(allSums2.contains((sumaToSearch=suma+minimalDel))){
            SearchExactValue sev = new SearchExactValue(sumaToSearch,list);
            Thread sevThread = new Thread(sev);
            sevThread.start();
        }else{ sumaToSearch = suma-minimalDel;
            SearchExactValue sev = new SearchExactValue(sumaToSearch,list);
           Thread sevThread =  new Thread(sev);
           sevThread.start();
        }

       // System.out.println("количество "+allSums.size() +" "+ allSums2.size());
        }
        private void writeInFile(HashSet<Integer> arr){
            TreeSet<Integer> arrSorted = new TreeSet(arr);
            System.out.println(arrSorted.size()+" после сорта");
            File file = new File("result.txt");
            try {
                FileWriter fw = new FileWriter(file,true);
                for(int i: arrSorted){
                fw.write(String.valueOf(i));
                fw.write("\n");
                }
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
}
