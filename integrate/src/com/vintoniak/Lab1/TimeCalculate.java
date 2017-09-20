package com.vintoniak.Lab1;

public class TimeCalculate {

    private GeneralCalculate genCalc;
    private double step;

    public TimeCalculate(GeneralCalculate genCalc,double step){
        this.genCalc = genCalc;
        this.step = step;
    }
    public long getTimeToCalculateWithSingleThread(){

        long start = System.currentTimeMillis();
        Integrator integrator = new Integrator(0,1000,step,genCalc);
        Thread thread = new Thread(integrator);
        thread.start();

        try {
            thread.join();
        }catch (Exception e){

        }
        long timeSpent = System.currentTimeMillis() - start;
        return timeSpent;
    }


    public long getTimeToCalculateWithTwoThreads(){

        long start = System.currentTimeMillis();
        Integrator integrator = new Integrator(0,500,step,genCalc);
        Integrator integrator2 = new Integrator(500,1000,step,genCalc);
        Thread thread = new Thread(integrator);
        Thread thread2 = new Thread(integrator2);
        thread.start();
        thread2.start();

        try {
            thread.join();
        }catch (Exception e){

        }
        long timeSpent = System.currentTimeMillis() - start;
        return timeSpent;
    }

    public long getTimeToCalculateWithThreeThreads(){

        long start = System.currentTimeMillis();
        Integrator integrator = new Integrator(0,333,step,genCalc);
        Integrator integrator2 = new Integrator(333,666,step,genCalc);
        Integrator integrator3 = new Integrator(666,1000,step,genCalc);
        Thread thread = new Thread(integrator);
        Thread thread2 = new Thread(integrator2);
        Thread thread3 = new Thread(integrator3);
        thread.start();
        thread2.start();
        thread3.start();

        try {
            thread.join();
        }catch (Exception e){

        }
        long timeSpent = System.currentTimeMillis() - start;
        return timeSpent;
    }

    public long getTimeToCalculateWithFourThreads(){

        long start = System.currentTimeMillis();
        Integrator integrator = new Integrator(0,250,step,genCalc);
        Integrator integrator2 = new Integrator(250,500,step,genCalc);
        Integrator integrator3 = new Integrator(500,750,step,genCalc);
        Integrator integrator4 = new Integrator(750,1000,step,genCalc);
        Thread thread = new Thread(integrator);
        Thread thread2 = new Thread(integrator2);
        Thread thread3 = new Thread(integrator3);
        Thread thread4 = new Thread(integrator4);
        thread.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread.join();
        }catch (Exception e){

        }
        long timeSpent = System.currentTimeMillis() - start;
        return timeSpent;
    }

}
