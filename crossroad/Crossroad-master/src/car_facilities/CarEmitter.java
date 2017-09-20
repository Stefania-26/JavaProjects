package car_facilities;

import helpers.MovementTemplates;
import helpers.Path;
import helpers.RoadType;

import java.util.Random;


public class CarEmitter {

    private static final int PATHWAY_ONE = 1;
    private static final int PATHWAY_TWO = 2;
    private static final int PATHWAY_THREE = 3;
    private static final int PATHWAY_FOUR = 4;

    public static Car generate(String mode){

        Car car = new Car();
        car.setOnCrossRoad(false);

        setWay(car);
        setRoadType(car);
        setPath(car);
        System.out.println(String.format(MovementTemplates.getTemplateStrings(mode).get("enter"), car.getStartWay(), car.getEndWay()));
        return car;
    }

    private static void setWay(Car car){
        Random random = new Random();
        car.setStartWay(random.nextInt(4) + 1);
        do {
            car.setEndWay(random.nextInt(4) + 1);

        } while (car.getStartWay() == car.getEndWay());
    }

    private static void setRoadType(Car car){
        if (car.getStartWay() == PATHWAY_ONE || car.getStartWay() == PATHWAY_TWO){
            car.setRoadType(RoadType.MAIN_ROAD);
        }
        else car.setRoadType(RoadType.OFF_ROAD);
    }

    private static void setPath(Car car){
        switch (car.getStartWay()){
            case PATHWAY_ONE:
                if (car.getEndWay() == PATHWAY_TWO) car.setPath(Path.MOVE_FORWARD);
                if (car.getEndWay() == PATHWAY_THREE) car.setPath(Path.TURN_LEFT);
                if (car.getEndWay() == PATHWAY_FOUR) car.setPath(Path.TURN_RIGHT);
                break;
            case PATHWAY_TWO:
                if (car.getEndWay() == PATHWAY_ONE) car.setPath(Path.MOVE_FORWARD);
                if (car.getEndWay() == PATHWAY_THREE) car.setPath(Path.TURN_RIGHT);
                if (car.getEndWay() == PATHWAY_FOUR) car.setPath(Path.TURN_LEFT);
                break;
            case PATHWAY_THREE:
                if (car.getEndWay() == PATHWAY_FOUR) car.setPath(Path.MOVE_FORWARD);
                if (car.getEndWay() == PATHWAY_ONE) car.setPath(Path.TURN_RIGHT);
                if (car.getEndWay() == PATHWAY_TWO) car.setPath(Path.TURN_LEFT);
                break;
            case PATHWAY_FOUR:
                if (car.getEndWay() == PATHWAY_THREE) car.setPath(Path.MOVE_FORWARD);
                if (car.getEndWay() == PATHWAY_TWO) car.setPath(Path.TURN_RIGHT);
                if (car.getEndWay() == PATHWAY_ONE) car.setPath(Path.TURN_LEFT);
                break;
        }
    }
}
