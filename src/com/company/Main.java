package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    static public int combinations = 0;
    static Thread ansThread;
    static ArrayList<Integer> cachedIndex1 = new ArrayList<>(), cachedIndex2 = new ArrayList<>(),cachedIndex3 = new ArrayList<>();
    static int compareIndex = 0;
    static Boolean running = true;


    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
        File file = new File("числа.txt");
        BufferedReader rdFile = new BufferedReader(new FileReader(file));
        ArrayList<Integer> indexArray = new ArrayList<>();
        ArrayList<Integer> arr = new ArrayList<>();
        ArrayList<Integer> tempArr = new ArrayList<>();
        ArrayList<Integer> count = new ArrayList<>();
        String inSums = "", inSumsTemp = "", tempRead = "";
        int countCombinations = 0;
        int inSum1 = 120440, inSum2 = 120460;
        System.out.println("Введите точную сумму");
        int readSum = Integer.parseInt(rd.readLine());

        if (readSum == 0 || String.valueOf(readSum).isEmpty()) {
            System.out.println("Введите нижнюю и верхнюю границу поиска через - но без пробелов \n (если нет необходимости просто нажмите ENTER)");
            inSums = rd.readLine();

            //parse between sums to integers
            if (inSums.length() > 0) {
                inSum1 = inSums.indexOf("-");
                inSumsTemp = inSums.substring(0, inSum1);
                inSum1 = Integer.parseInt(inSumsTemp);
                inSumsTemp = inSums.substring(inSums.indexOf("-") + 1, inSums.length());
                inSum2 = Integer.parseInt(inSumsTemp);
            }
        }

        if (inSums == null || inSums.equals("")) {
            while ((tempRead = rdFile.readLine()) != null) {
                arr.add(Integer.parseInt(tempRead));
            }
        }
        int a = 0, b = 0;

        combinations = arr.size() * arr.size() * arr.size() * 2;
        while (running) {
            int suma = 0;
            count.clear();
            countCombinations++;
            tempArr.clear();
            tempArr.addAll(arr);
            indexArray.clear();
            for (; ; ) {
                a = (b + (int) (Math.random() * tempArr.size()));

                if (tempArr.size() > 0) {
                    if(tempArr.get(a) != 0) {
                        suma += tempArr.get(a);
                        count.add(tempArr.get(a));
                    }else continue;

                    //tempArr.remove(a);
                    tempArr.set(a, 0);
                    indexArray.add(a);
                }
                if (readSum == suma) {

                    if(compareIndex(indexArray)){
                        printResults(suma, count);
                    }
                    break;
                }
                if (suma > readSum) break;
            }
            if (countCombinations == combinations) {

                if (inSums.length() > 0) {
                    BetweenSum bS = new BetweenSum(inSum1, inSum2, arr);
                    bS.start();

                } else {
                    AutoNearbySearch ans = new AutoNearbySearch(readSum, arr);
                    ansThread = new Thread(ans);
                    ansThread.start();

                }


            }
            if (countCombinations > combinations * combinations) {

                ansThread.join();

                if(compareIndex==0) System.out.println("------------------------------------------------------\nСкорее всего такой суммы " + readSum + " не существует.");
                break;
            }
        }

        System.out.println("");
        System.out.println("combinations = " + countCombinations);
        System.out.println("GAME OVER");
        String ifNeedRestart = "";
        if ((ifNeedRestart = rd.readLine()) != null) {
//                ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/C", "start.bat");
//                Process process = processBuilder.start();
        }
        rd.close();
        rdFile.close();
    }
    private static void printResults(int suma, ArrayList<Integer> count){
        System.out.println("Cумма найдена == " + suma + "\nЧисла:");
        for (int i = 0; i < count.size(); i++) {
            System.out.println(count.get(i));
        }
    }
    private static boolean compareIndex(ArrayList<Integer> indexArray){
        Collections.sort(indexArray);
        if(compareIndex == 0){
            cachedIndex1.addAll(indexArray);
            compareIndex++;
            return true;
        }
        if(compareIndex == 1) {
            if (!(indexArray.equals(cachedIndex1))) {
                cachedIndex2.addAll(indexArray);
                compareIndex++;
                return true;
            }
        }
            if(compareIndex == 2){
                if(!(indexArray.equals(cachedIndex1) || indexArray.equals(cachedIndex2))){
                    cachedIndex3.addAll(indexArray);
                    running = false;
                    return true;

                }
            }

        return false;
        }
    }
