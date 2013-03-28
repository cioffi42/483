 
public class Animation extends Thread {
    
    private static final double NUM_FRAMES = 30.0;
    private static final double ANIMATION_LENGTH = 0.5;
    
    public Node[] nodes;
    
    public Animation(Node[] nodes){
        this.nodes = nodes;
    }
    
    @Override
    public void run() {
        try {
            for (double i=1; i<=NUM_FRAMES; i++){
                for (Node node : nodes){
                    node.getCenter().x = node.oldCenter.x*(1.0 - i/NUM_FRAMES) + node.newCenter.x*(i/NUM_FRAMES);
                    node.getCenter().y = node.oldCenter.y*(1.0 - i/NUM_FRAMES) + node.newCenter.y*(i/NUM_FRAMES);
                }
                MainApplet.displayPane.repaint();
                sleep((int)(1000.0*ANIMATION_LENGTH/NUM_FRAMES));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
