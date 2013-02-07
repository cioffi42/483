
public class MathUtils {
    public static double dist(Point a, Point b){
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx*dx+dy*dy);
    }
    
    public static double dist(Node a, Node b){
        return dist(a.getCenter(), b.getCenter());
    }
}
