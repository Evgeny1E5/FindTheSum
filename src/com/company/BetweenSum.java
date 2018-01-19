package com.company;

import java.util.ArrayList;

public class BetweenSum extends  Thread{
    int inSum1, inSum2;
    ArrayList<Integer> list;
    private int lastSum=0;

    public BetweenSum(int inSum1, int inSum2, ArrayList<Integer> arr) {
        this.inSum1 = inSum1;
        this.inSum2 = inSum2;
        this.list = arr;
    }

    public void run() {
        int random = 0, countWrites = 0;
        Boolean end = true;
        ArrayList<Integer> count = new ArrayList<Integer>();
        ArrayList<Integer> allSums = new ArrayList<Integer>();

        while (true) {
            int suma = 0;
            count.clear();
            for (; ; ) {
                random = (0 +  (int) (Math.random() * list.size()));

                if (!count.contains(random)) {
                    count.add(random);
                    suma += list.get(random);
                }
                if (!allSums.contains(suma)) {
                    if (inSum1 < suma && suma < inSum2) {
                        if (!allSums.contains(suma)) {
                            System.out.println(suma +" = между " +inSum1+"-"+inSum2+"\n Числа:");
                            countWrites++;
                            allSums.add(suma);
                            for (int i = 0; i < count.size(); i++) {
                                System.out.println(list.get(count.get(i)));
                            }
                        }

                    }
                }
                    if (suma > inSum2) break;
                }
                if (countWrites == 3) break;

            }


    }

    }


