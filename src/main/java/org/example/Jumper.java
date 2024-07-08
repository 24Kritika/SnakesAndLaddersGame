package org.example;

public class Jumper {

    private Long startPoint;
    private Long endPoint;

    public Jumper(Long startPoint, Long endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Long getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Long startPoint) {
        this.startPoint = startPoint;
    }

    public Long getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Long endPoint) {
        this.endPoint = endPoint;
    }
}
