import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

public class Mouse extends MouseInputAdapter {
    
    private int curX, curY, prevX, prevY;
    private boolean isDragged = false;
    
    @Override
    public void mousePressed(MouseEvent event){
        curX = prevX = event.getX();
        curY = prevY = event.getY();
        isDragged = true;
    }
    
    @Override
    public void mouseDragged(MouseEvent event){
        if (isDragged){
            prevX = curX;
            prevY = curY;
            curX = event.getX();
            curY = event.getY();
            //MainApplet.displayPane.changeOffset(prevX-curX, prevY-curY);
            //MainApplet.displayPane.repaint();
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
        if (node != null){
            MainApplet.displayPane.focusNode = node;
            MainApplet.displayPane.repaint();
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent event){
        Node node = MainApplet.displayPane.getMouseNode(event.getX(), event.getY());
        if (node != null){
            MainApplet.displayPane.focusNode = node;
            MainApplet.displayPane.repaint();
        }
    }
}
