package com.vintoniak.Lab1;


public class GeneralCalculate {


    public double getSum() {
        return sum;
    }

    public void clearSum(){
        sum =0;
    }

    private double sum;

    public synchronized void add(double x){
        sum+=x;
    }


}
