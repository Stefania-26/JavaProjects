import car_facilities.Car;
import helpers.MovementTemplates;
import helpers.Path;

import java.util.HashMap;
import java.util.Queue;


public class Move implements Runnable {

    private static final int FIRST_ROAD = 1;
    private static final int SECOND_ROAD = 2;
    private static final int THIRD_ROAD = 3;
    private static final int FOURTH_ROAD = 4;
    private HashMap<String, String> modes;

    private boolean canMove = true;
    private boolean isPassed = false;

    private Car car;
    private CrossRoad crossRoad;
    private Queue<Car> currentQueue;

    Move(Car car, CrossRoad crossRoad, Queue<Car> currentQueue, String mode) {
        this.car = car;
        this.crossRoad = crossRoad;
        this.currentQueue = currentQueue;
        this.modes = MovementTemplates.getTemplateStrings(mode);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println(String.format(this.modes.get("appear"), car.getStartWay(), car.getEndWay()));
            startMove();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void startMove() throws InterruptedException {
        while (true) {
            canMove = true;
            if (isPassed){
                return;
            }
            if (car.getQueuePosition() != 0) {
                continue;
            }

            checkRoad();
        }
    }

    private void checkRoad() throws InterruptedException {
        Car first = crossRoad.getPathwayOne().peek();
        Car second = crossRoad.getPathwayTwo().peek();
        Car third = crossRoad.getPathwayThree().peek();
        Car fourth = crossRoad.getPathwayFour().peek();
        switch (car.getStartWay()) {
            case FIRST_ROAD:
                checkCrossRoad(third, fourth, second);
                break;
            case SECOND_ROAD:
                checkCrossRoad(fourth, third, first);
                break;
            case THIRD_ROAD:
                checkCrossRoad(second, first, fourth);
                break;
            case FOURTH_ROAD:
                checkCrossRoad(first, second, third);
        }
    }

    private void checkCrossRoad(Car left, Car right, Car ahead) throws InterruptedException {

        goodDirection(left, Path.TURN_RIGHT);
        switch (car.getPath()) {
            case MOVE_FORWARD:
                badDirection(ahead, Path.TURN_LEFT);
                noDirection(right);
                break;
            case TURN_LEFT:
                noDirection(ahead);
                noDirection(right);
                break;
            case TURN_RIGHT:
                badDirection(ahead, Path.TURN_LEFT);
                break;
        }
        if (canMove) {
            startGoing();
        }else {
            System.out.println(String.format(this.modes.get("wait"), car.getStartWay(), car.getEndWay()));
        }
    }


    // one path when car can`t pass crossroad
    private void badDirection(Car anotherCar, Path path) {
        if (anotherCar != null)
        if (anotherCar.isOnCrossRoad()) {
            if (anotherCar.getPath() == path) {
                canMove = false;
            }
        }
    }

    // one path when car can pass crossroad
    private void goodDirection(Car anotherCar, Path path) {
        if (anotherCar !=null)
        if (anotherCar.isOnCrossRoad()) {
            if (!(anotherCar.getPath() == path)) {
                canMove = false;
            }
        }
    }

    private void noDirection(Car anotherCar) {
        if (anotherCar != null)
        if (anotherCar.isOnCrossRoad()) {
            canMove = false;
        }
    }


    private void startGoing() throws InterruptedException {

        switch (car.getRoadType()) {

            case MAIN_ROAD:
                car.setOnCrossRoad(true);
                goAhead();
                break;
            case OFF_ROAD:
                if (crossRoad.getPathwayOne().size() == 0 && crossRoad.getPathwayTwo().size() == 0) {
                    car.setOnCrossRoad(true);
                    goAhead();
                }
                break;
        }
    }


    private void goAhead() throws InterruptedException {
        System.out.println(String.format(this.modes.get("crossroad"), car.getStartWay(), car.getEndWay()));
        Thread.sleep(1000);
        currentQueue.poll();
        System.out.println(String.format(this.modes.get("leave"), car.getStartWay(), car.getEndWay()));
        isPassed = true;
    }
}
