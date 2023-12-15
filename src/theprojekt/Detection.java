//package theprojekt;
//
//import doctrina.Canvas;
//import doctrina.Direction;
//import doctrina.StaticEntity;
//
//import java.awt.*;
//import java.util.HashMap;
//
//public class Detection extends StaticEntity {
//    private int parameterX;
//    private int parameterY;
//    private int parameterWidth = 130;
//    private int parameterHeight = 200;
//    protected java.util.Map<Direction, Direction.TriConsumer<Integer, Integer, Integer>> directionCalculations = new HashMap<>();
//    private Npc npc;
//
//    public StaticEntity parameterDirection() {
//
//        Direction direction = npc.getDirection();
//
//        directionCalculations.put(Direction.RIGHT, (x, y, width) -> {
//            parameterX = x + 10;
//            parameterY = y - 90;
//            detectionParameter(parameterX, parameterY, true);
//        });
//
//        directionCalculations.put(Direction.LEFT, (x, y, width) -> {
//            parameterX = x - 110;
//            parameterY = y - 80;
//            detectionParameter(parameterX, parameterY, true);
//        });
//
//        directionCalculations.put(Direction.DOWN, (x, y, width) -> {
//            parameterX = x - 80;
//            parameterY = y + 8;
//            detectionParameter(parameterX, parameterY, false);
//        });
//
//        directionCalculations.put(Direction.UP, (x, y, width) -> {
//            parameterX = x - 75;
//            parameterY = y - 100;
//            detectionParameter(parameterX, parameterY, false);
//        });
//
//        directionCalculations.getOrDefault(direction, (x, y, width) -> {
//        }).accept(this.getX(), this.getY(), this.getWidth());
//
//        return this;
//    }
//
//    public void detectionParameter(int x, int y, boolean horizontal) {
//        if (horizontal) {
//            new Rectangle(x, y, parameterWidth, parameterHeight);
//        } else {
//            new Rectangle(x, y, parameterHeight, parameterWidth);
//        }
//    }
//
//    public void drawDetection(Canvas canvas) {
//        canvas.drawRectangle(parameterDirection() , new Color(99, 144, 255, 137));
//    }
//    @Override
//    public void draw(Canvas canvas) {
//
//    }
//}
