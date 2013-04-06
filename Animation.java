 
public class Animation extends Thread {
    
    private static final double FPS = 60.0;
    private static final double ANIMATION_LENGTH = 0.5;
    
    public static boolean isRunning = false;
    
    public Node[] nodes;
    
    public Animation(Node[] nodes){
        this.nodes = nodes;
    }
    
    @Override
    public void run() {
        try {
            isRunning = true;
            double numFrames = FPS * ANIMATION_LENGTH;
            for (double i=1; i<=numFrames; i++){
                for (Node node : nodes){
                    node.getCenter().x = node.oldCenter.x*(1.0 - i/numFrames) + node.newCenter.x*(i/numFrames);
                    node.getCenter().y = node.oldCenter.y*(1.0 - i/numFrames) + node.newCenter.y*(i/numFrames);
                }
                MainApplet.displayPane.repaint();
                sleep((int)(1000.0*ANIMATION_LENGTH/numFrames));
            }
            isRunning = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
