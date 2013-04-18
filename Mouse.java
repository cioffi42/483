import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.event.MouseInputAdapter;

public class Mouse extends MouseInputAdapter {

    private int startX, startY, curX, curY, prevX, prevY;

    // The node being clicked or dragged
    private Node targetNode = null;

    private Date dragStartTime = null;
    
    private boolean isDragged = false;

    @Override
    public void mousePressed(MouseEvent event){
        Node node = MainPanel.displayPane.getMouseNode(event.getX(), event.getY());
        if (node != null){
            // User pressed mouse button down on a node
            targetNode = node;
            dragStartTime = new Date();
            if (event.getButton() == MouseEvent.BUTTON1) {
            	isDragged = true;
            }
			startX = curX = prevX = event.getX();
			startY = curY = prevY = event.getY();
        }
    }

    @Override
    public void mouseDragged(MouseEvent event){
        if (targetNode != null && dragStartTime != null && isDragged){
            prevX = curX;
            prevY = curY;
            curX = event.getX();
            curY = event.getY();
            targetNode.getCenter().x += (curX - prevX);
            targetNode.getCenter().y += (curY - prevY);
            MainPanel.displayPane.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent event){
        if (targetNode != null && dragStartTime != null) {
            // Determine if this was a mouse click, based on the distance the 
            // mouse moved, and how long the click lasted
            double distance = MathUtils.dist(new Point(startX, startY), new Point(curX, curY));
            long timeElapsedMillisecs = new Date().getTime() - dragStartTime.getTime();
            boolean isClick = (distance < 1.0 || (distance < 40.0 && timeElapsedMillisecs < 100));
            //System.out.println(distance + "\n" + timeElapsedMillisecs);
            if (isClick) {
                if (event.getButton() == MouseEvent.BUTTON1) {
					if (targetNode != MainPanel.displayPane.getFocusNode()) {
						// User clicked on a node, so set the focus node
						MainPanel.displayPane.setFocusNode(targetNode, true);
					}
				}
				if (event.getButton() == MouseEvent.BUTTON3){
                	MainPanel.sidePane.updatePanel(targetNode);
                }
            }
        }
        targetNode = null;
        dragStartTime = null;
    }

    @Override
    public void mouseMoved(MouseEvent event){
        Node node = MainPanel.displayPane.getMouseNode(event.getX(), event.getY());
        int cursor = (node != null) ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR;
        MainPanel.displayPane.setCursor(Cursor.getPredefinedCursor(cursor));
        if (DisplayPane.hoverNode != node) {
            // We need to update the hover node (which can be null)
            DisplayPane.hoverNode = node;
            MainPanel.displayPane.repaint();
        }
    }
}
