package com.vintoniak.Lab1;

public class Main {

    public static void main(String[] args) {

        GeneralCalculate genCalc = new GeneralCalculate();

        double start = 0;
        double finish = 10;
        double dx = 1;
        int countOfThreds = 10;
        double smallFinish = finish/countOfThreds;

        IntegratorFactory factory = new IntegratorFactory(start,finish,dx,smallFinish,genCalc);

        long startT = System.currentTimeMillis();
        for(double i=0; i < countOfThreds; i++ ){

            Thread integrator = factory.getNextIntegrator();

            integrator.start();

            try {
                integrator.join();
            }catch (Exception e){

            }

        }
        long timeSpent = System.currentTimeMillis() - startT;
        System.out.println(factory.getGeneralCalculate().getSum());
        System.out.println("Time : " + timeSpent);
    }
}
