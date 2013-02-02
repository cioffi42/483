import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;


public class Test extends Applet implements Runnable{
    
    private static class Point {
        double x,y, vx=0.0, vy=0.0;
        
        Point(double x, double y)
        {
            this.x = x;
            this.y = y;
        }

        public double getX() 
        {
            return this.x;
        }

        public double getY() 
        {
            return this.y;
        }
    }
    
    private static final long serialVersionUID = 1L;
    private static final int FRAME_RATE = 40;
    private static final int FRAME_RATE_MS = 1000/FRAME_RATE;
    private static final int RADIUS = 20;
    
    private static final int NUM_NODES = 10;
    Point[] points = new Point[NUM_NODES];
    
    public Test(){
        for (int i=0; i<points.length; i++){
            points[i] = new Point(Math.random()*1100, Math.random()*500);
        }
    }
    
    private static void drawCircle(Graphics g, double xCenter, double yCenter, int r){
        g.fillOval((int)xCenter-r, (int)yCenter-r, 2*r, 2*r);
    }
    
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.BLUE);
        for (int i=0; i<points.length; i++){
            drawCircle(g, points[i].getX(), points[i].getY(), RADIUS);
        }
    }
    
    public void start() {
        System.out.println("start");
        Thread th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {
        System.out.println("fda");
        while (true) {
            repaint();
            try{
                Thread.sleep(FRAME_RATE_MS);
                this.processFrame();
                
            } catch (InterruptedException e) {}
        }
    }
    
    private static final double FORCE = 100.0;
    private static final double FRICTION = 0.982;
    private static final double NODE_FORCE = 4.0;
    
    public void processFrame(){
        for (int i=0; i<points.length; i++){
            double forceX = 0.0;
            double forceY = 0.0;
            
            forceX += FORCE/Math.abs(points[i].getX());
            forceX -= FORCE/Math.abs(getWidth()-points[i].getX());
            forceY += FORCE/Math.abs(points[i].getY());
            forceY -= FORCE/Math.abs(getHeight()-points[i].getY());
            
            for (int j=0; j<points.length; j++){
                if (i != j){
                    double diffX = points[i].getX()-points[j].getX();
                    double diffY = points[i].getY()-points[j].getY();
                    
                    forceX += (diffX/Math.abs(diffX))*NODE_FORCE/Math.abs(diffX);
                    forceY += (diffY/Math.abs(diffY))*NODE_FORCE/Math.abs(diffY);
                }
                
            }
            
            points[i].x += points[i].vx;
            points[i].y += points[i].vy;
            points[i].vx += forceX;
            points[i].vy += forceY;
            points[i].vx *= FRICTION;
            points[i].vy *= FRICTION;
            if (Math.abs(points[i].vx) > 10.0){
                points[i].vx = 10.0*(points[i].vx/Math.abs(points[i].vx));
            }
            if (Math.abs(points[i].vy) > 10.0){
                points[i].vy = 10.0*(points[i].vy/Math.abs(points[i].vy));
            }
        }
    }
    
}
