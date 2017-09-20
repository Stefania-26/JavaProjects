package com.vintoniak.Lab1;

public class Integrator implements Runnable
{
    private double start;
    private double finish;
    private double dx;
    private double sum;
    private GeneralCalculate genCalc;

    public Integrator(double start,double finish,double dx, GeneralCalculate genCalc){
        this.start = start;
        this.finish = finish;
        this.dx = dx;
        this.genCalc = genCalc;
    }

    @Override
    public void run() {
        for(double i = start ; i<finish; i+=dx){

            sum += Math.abs((Func.getFuncValue(i*dx)+Func.getFuncValue(i*dx+dx))/2*dx);
        }
        System.out.println(Thread.currentThread().getName() + " - " + sum);
        genCalc.add(sum);
    }
}
