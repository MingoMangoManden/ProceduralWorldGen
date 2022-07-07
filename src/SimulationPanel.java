import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Random;

public class SimulationPanel extends JPanel {
	
	public static int blocksHorizontal, blocksVertical;
	
	World world;
	int mapZoom = 1;
	
	public SimulationPanel() {
		setPreferredSize(new Dimension(SimulationWindow.width, SimulationWindow.height));
		setFocusable(true);
		
		SimulationPanel.blocksHorizontal = SimulationWindow.width;
		SimulationPanel.blocksVertical = SimulationWindow.height;
		
		this.world = new World(10);
		this.world.generate();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(World.map, 0, 0, World.map.getWidth() * mapZoom, World.map.getHeight() * mapZoom, this);
		
		g2.dispose();
		
	}

}
