package car_facilities;

import helpers.Path;
import helpers.RoadType;


public class Car {

    private int startWay;
    private int endWay;
    private int queuePosition;
    private boolean isOnCrossRoad;
    private RoadType roadType;
    private Path path;



    public int getQueuePosition() {
        return queuePosition;
    }

    public void setQueuePosition(int queuePosition) {
        this.queuePosition = queuePosition;
    }

    public int getStartWay() {
        return startWay;
    }

     void setStartWay(int startWay) {
        this.startWay = startWay;
    }

    public int getEndWay() {
        return endWay;
    }

    void setEndWay(int endWay) {
        this.endWay = endWay;
    }

    public RoadType getRoadType() {
        return roadType;
    }

    void setRoadType(RoadType roadType) {
        this.roadType = roadType;
    }

    public Path getPath() {
        return path;
    }

    void setPath(Path path) {
        this.path = path;
    }

    public boolean isOnCrossRoad() {
        return isOnCrossRoad;
    }

    public void setOnCrossRoad(boolean onCrossRoad) {
        isOnCrossRoad = onCrossRoad;
    }
}
