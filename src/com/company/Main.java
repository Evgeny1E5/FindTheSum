package com.company;

import java.io.*;
import java.util.ArrayList;

public class Main {
    static public int combinations=0;
    static Thread ansThread;

    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
        File file = new File("числа.txt");
        BufferedReader rdFile = new BufferedReader(new FileReader(file));

            ArrayList<Integer> arr = new ArrayList<>();
            ArrayList<Integer> tempArr = new ArrayList<>();
            ArrayList<Integer> count = new ArrayList<>();
            String inSums,inSumsTemp="",tempRead="";
            int countCombinations = 0;
            int inSum1=120440,inSum2=120460;
            System.out.println("Введите точную сумму");
            int readSum = Integer.parseInt(rd.readLine());
            System.out.println("Введите нижнюю и верхнюю границу поиска через - но без пробелов \n (если нет необходимости просто нажмите ENTER)");
            inSums = rd.readLine();
            //parse between sums to integers
            if(inSums.length()>0){
                inSum1 = inSums.indexOf("-");
                inSumsTemp = inSums.substring(0,inSum1);
                inSum1 = Integer.parseInt(inSumsTemp);
                inSumsTemp = inSums.substring(inSums.indexOf("-")+1,inSums.length());
                inSum2 =  Integer.parseInt(inSumsTemp);
            }


        if(inSums == null || inSums.equals("")) {
            while ((tempRead = rdFile.readLine()) != null) {
                arr.add(Integer.parseInt(tempRead));
            }
        }

            int a = 0, b = 0;
            Boolean end = true;
            combinations = arr.size() * arr.size() * arr.size();
            while (end) {
                int suma = 0;
                count.clear();
                countCombinations++;
                tempArr.clear();
                tempArr.addAll(arr);
                for (; ; ) {
                    a = (b + (int) (Math.random() * tempArr.size()));

                    if (tempArr.size()>0) {
                        count.add(tempArr.get(a));
                        suma += tempArr.get(a);
                        tempArr.remove(a);
                    }
                    if (readSum == suma) {
                        System.out.println("Cумма найдена == " +suma +"\n Числа:");
                        for (int i = 0; i < count.size(); i++) {
                            System.out.println(count.get(i));

                        }
                        end = false;
                        break;
                    }
                    if (suma > readSum) break;
                }
                if(countCombinations==combinations) {

                    if(inSums.length()>0){
                        BetweenSum bS = new BetweenSum(inSum1, inSum2, arr);
                        bS.start();

                    }else{
                        AutoNearbySearch ans = new AutoNearbySearch(readSum, arr);
                        ansThread = new Thread(ans);
                        ansThread.start();

                    }


                }
                if(countCombinations>combinations*combinations){

                        ansThread.join();

                    System.out.println("------------------------------------------------------\nСкорее всего такой суммы "+readSum+" не существует.");
                    break;
                }
            }

             System.out.println("");
            System.out.println("combinations = "+countCombinations);
            System.out.println("GAME OVER");
            String ifNeedRestart ="";
            if((ifNeedRestart=rd.readLine())!=null){
//                ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/C", "start.bat");
//                Process process = processBuilder.start();
            }
              rd.close();
            rdFile.close();
        }
    }