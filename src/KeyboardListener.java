import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {
	
	/*
	 * System.out.println("zoom in");
		SimulationWindow.sp.mapZoom++;
		SimulationWindow.sp.repaint();
	 */

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_PLUS) {
			SimulationWindow.sp.mapZoom++;
			SimulationWindow.sp.repaint();
			 
		}
		if (code == KeyEvent.VK_MINUS) {
			SimulationWindow.sp.mapZoom--;
			SimulationWindow.sp.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
