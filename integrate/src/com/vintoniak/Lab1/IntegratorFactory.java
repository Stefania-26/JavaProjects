package com.vintoniak.Lab1;

public class IntegratorFactory {

    public GeneralCalculate getGeneralCalculate() {
        return generalCalculate;
    }

    private GeneralCalculate generalCalculate;
    private double start;
    private double finish;
    private double dx;
    private double smallFinish;


    public IntegratorFactory(double start,double finish,double dx,double smallFinish,GeneralCalculate generalCalculate){
        this.generalCalculate = generalCalculate;
        this.dx = dx;
        this.start = start;
        this.finish = finish;
        this.smallFinish = smallFinish;
    }

    public Thread getNextIntegrator(){

        if(smallFinish > finish)
            smallFinish = finish;

        Integrator integrator = new Integrator(start,smallFinish,dx,generalCalculate);

        start = smallFinish;

        smallFinish+=smallFinish;

        Thread thread = new Thread(integrator);

        return thread;
    }

}
