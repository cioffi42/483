import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

public class Mouse extends MouseInputAdapter {
    
    private int startX, startY, originalOffsetX, originalOffsetY;
    private boolean isDragged = false;
    
    @Override
    public void mousePressed(MouseEvent event){
        Node node = MainApplet.displayPane.getMouseNode(event.getX(), event.getY());
        if (node != null){
            // User clicked on a node
            DisplayPane.hoverNode = node;
            MainApplet.displayPane.repaint();
        } else {
            // User clicked on anything but a node
            startX = event.getX();
            startY = event.getY();
            originalOffsetX = MainApplet.displayPane.offsetX;
            originalOffsetY = MainApplet.displayPane.offsetY;
            isDragged = true;
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent event){
        if (isDragged){
            MainApplet.displayPane.setOffset(originalOffsetX + startX-event.getX(), originalOffsetY + startY-event.getY());
            MainApplet.displayPane.repaint();
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent event){
        isDragged = false;
    }
    
    @Override
    public void mouseMoved(MouseEvent event){
        Node node = MainApplet.displayPane.getMouseNode(event.getX(), event.getY());
        int cursor = (node != null) ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR;
        MainApplet.displayPane.setCursor(Cursor.getPredefinedCursor(cursor));
        if (DisplayPane.hoverNode != node){
            // We need to update the hover node (which can be null)
            DisplayPane.hoverNode = node;
            MainApplet.displayPane.repaint();
        }
    }
}
