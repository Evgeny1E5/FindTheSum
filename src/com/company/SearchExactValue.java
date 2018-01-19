package com.company;

import java.util.ArrayList;

public class SearchExactValue implements Runnable {

    private ArrayList<Integer> list;
    private int sum;

    public SearchExactValue(int sum, ArrayList<Integer> list) {
        this.list = list;
        this.sum = sum;
    }

    @Override
    public void run() {
        int a = 0, b = 0;
        ArrayList<Integer> count = new ArrayList<>();
        Boolean end = true;
        while (end) {
            int suma = 0;
            count.clear();
            for (; ; ) {
                a = (b + (int) (Math.random() * list.size()));

                if (!count.contains(a)) {
                    count.add(a);
                    suma += list.get(a);
                }
                if (sum == suma) {
                    System.out.println("Найдена ближайшая сумма == " + suma + "\n Числа:");
                    for (int i = 0; i < count.size(); i++) {
                        System.out.println(list.get(count.get(i)));

                    }
                    end = false;
                    break;
                }
                if (suma > sum) break;
            }
        }
    }
}

