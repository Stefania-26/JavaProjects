import car_facilities.Car;
import car_facilities.CarEmitter;
import helpers.MovementTemplates;

import java.util.*;
import java.util.Queue;


class CrossRoad {
    //private boolean isFree = true;
    private Queue<Car> pathwayOne = new LinkedList<>();
    private Queue<Car> pathwayTwo = new LinkedList<>();
    private Queue<Car> pathwayThree = new LinkedList<>();
    private Queue<Car> pathwayFour = new LinkedList<>();



    void start(int limitCarCapacity, String mode) {
        int carCount = 0;

        Queue<Car> currentQueue = new LinkedList<>();
        while (carCount < limitCarCapacity) {
            Car car = CarEmitter.generate(mode);
            carCount++;

            switch (car.getStartWay()) {
                case 1:
                    currentQueue = pathwayOne;
                    break;
                case 2:
                    currentQueue = pathwayTwo;
                    break;
                case 3:
                    currentQueue = pathwayThree;
                    break;
                case 4:
                    currentQueue = pathwayFour;
                    break;
            }

            car.setQueuePosition(currentQueue.size());
            currentQueue.add(car);
            Move move = new Move(car, this, currentQueue, mode);
            Thread thread = new Thread(move);
            thread.start();

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(MovementTemplates.getEndStringSequence(mode));
        System.exit(0);
    }



    Queue<Car> getPathwayOne() {
        return pathwayOne;
    }

    Queue<Car> getPathwayTwo() {
        return pathwayTwo;
    }

    Queue<Car> getPathwayThree() {
        return pathwayThree;
    }

    Queue<Car> getPathwayFour() {
        return pathwayFour;
    }
}
