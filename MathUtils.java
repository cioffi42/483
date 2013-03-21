
public class MathUtils {
    
    private static final double VERTICAL = Double.MAX_VALUE;
    private static final double HORIZONTAL = 0.0;
    
    public static double dist(Point a, Point b){
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx*dx+dy*dy);
    }
    
    public static double dist(Node a, Node b){
        return dist(a.getCenter(), b.getCenter());
    }
    
    public static double length(Edge edge){
        return dist(edge.node1.getCenter(), edge.node2.getCenter());
    }
    
    public static double slope(Point a, Point b){
        if ((a.x - b.x) != 0){
            return (a.y - b.y) / (a.x - b.x);
        } else {
            return VERTICAL;
        }
    }
    
    public static Point projection(Point point, Point endpoint1, Point endpoint2){
        double conSlope = slope(endpoint1, endpoint2);
        if (conSlope == HORIZONTAL){
            return new Point(point.x, endpoint1.y);
        } else if (conSlope == VERTICAL){
            return new Point(endpoint1.x, point.y);
        } else {
            double nodeSlope = -1.0 / conSlope;
            double conIntercept = (endpoint1.y - conSlope*endpoint1.x);
            double nodeIntercept = (point.y - nodeSlope*point.x);
            double x = (conIntercept-nodeIntercept) / (nodeSlope-conSlope);
            double y = nodeSlope*x + nodeIntercept;
            return new Point(x,y);
        } 
    }
    
    public static double dist(Node node, Edge edge){
        Point con1 = edge.node1.getCenter();
        Point con2 = edge.node2.getCenter();
        Point nodePt = node.getCenter();
        Point projection = projection(node.getCenter(), con1, con2);
        if (    projection.x > con1.x && projection.x < con2.x || 
                projection.x < con1.x && projection.x > con2.x || 
                projection.y > con1.y && projection.y < con2.y || 
                projection.y < con1.y && projection.y > con2.y){
            return dist(nodePt, projection);
        } else {
            return Math.min(dist(nodePt, con1), dist(nodePt, con2));
        }
    }
    
    public static double min(double...numbers){
        double result = numbers[0];
        for (int i=1; i<numbers.length; i++){
            result = Math.min(result, numbers[i]);
        }
        return result;
    }
    
    public static int argmin(double...numbers){
        int result = 0;
        for (int i=1; i<numbers.length; i++){
            if (numbers[i] < numbers[result]){
                result = i;
            }
        }
        return result;
    }
    
    public static double average(double...numbers){
        double sum = 0.0;
        for (double number : numbers){
            sum += number;
        }
        return sum / numbers.length;
    }
    
    public static double weight(double number, Rating.Category cat){
        if (number < cat.MIN){
            return number/cat.MIN;
        } else if (number < cat.DESIRED){
            return 1.0 + (number-cat.MIN)/(cat.DESIRED-cat.MIN);
        } else if (cat.CAP){
            return 2.0;
        } else {
            // TODO: figure out what I should return here
            return 1.0 + (number-cat.MIN)/(cat.DESIRED-cat.MIN);
        }
    }
}
