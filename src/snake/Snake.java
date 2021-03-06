package snake;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 */
public class Snake {
    private final List<Segment> body;
    private Color segmentColor = Color.GREEN;
    private Direction direction = Direction.Down;
    private final World world;

    private class Segment extends Rectangle {

        private final IntegerProperty segX;
        private final IntegerProperty segY;

        public Segment(int x, int y, Color color) {
            super(World.UNIT, World.UNIT, color);
            segX = new SimpleIntegerProperty(x);
            segY = new SimpleIntegerProperty(y);
            translateXProperty().bind(segX.multiply(World.UNIT));
            translateYProperty().bind(segY.multiply(World.UNIT));
            world.getChildren().add(this);
        }

        public int getValX() {
            return segX.intValue();
        }

        public int getValY() {
            return segY.intValue();
        }

        public void setLocation( int x, int y ){
            segX.setValue(x);
            segY.setValue(y);
        }
        
    }

    public Snake(int x, int y, World world) {
        this.world = world;
        this.body = new LinkedList<>();
        body.add(new Segment(x, y, Color.RED));
        body.add(new Segment(x - 1, y, Color.GREEN));
    }
    
    private boolean canMoveTo( int x, int y ) {
        return x >= 0 && x < World.SIZE && y >= 0 && y < World.SIZE;
    }
    public void moveOneStep(){
        boolean running = true;
        if(!canMoveTo(getX()+ direction.getdX(), getY() + direction.getdY()) || bitesItself(getX()+ direction.getdX(), getY() + direction.getdY())|| !running){
           world.stop();
           running = false;
        } else if(world.foundFoodAt(getX()+ direction.getdX(), getY() + direction.getdY())){
                body.add(1, new Segment(getX(), getY(), Color.GREEN));
                body.get(0).setLocation(getX()+ direction.getdX(), getY() + direction.getdY());
                world.eatFood();
                
        } else {
            moveBody();
            body.get(0).setLocation(getX()+ direction.getdX(), getY() + direction.getdY());
            
                  
        }}
    

    private void moveBody(  ) {
        Segment successor = body.get(body.size()-1);
        ListIterator<Segment> lit = body.listIterator(body.size()-1); 
        while( lit.hasPrevious() ) {
            Segment current = lit.previous();
            successor.setLocation(current.getValX(), current.getValY());
            successor = current;
        }
    }

    public boolean bitesItself( int headX, int headY ) {
        for (Segment seq : body.subList(1, body.size())) {
            if ( seq.getValX() == headX && seq.getValY() == headY ) {
                return true;
            }
        }
        return false;
    }

    public int getX() {
        return body.get(0).getValX();
    }
    public int getY() {
        return body.get(0).getValY();
    }
    public void setDirection( Direction newDirection ) {
        direction = newDirection;
    }
   
    
}
