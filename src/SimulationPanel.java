import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class SimulationPanel extends JPanel {
	
	public static final int blockSize = 1;
	public static int blocksHorizontal, blocksVertical;
	
	World world;
	int mapZoom = 1;
	
	public SimulationPanel() {
		setPreferredSize(new Dimension(SimulationWindow.width, SimulationWindow.height));
		setFocusable(true);
		addKeyListener(new KeyboardListener());
		
		SimulationPanel.blocksHorizontal = (int) (SimulationWindow.width/blockSize)-1;
		SimulationPanel.blocksVertical = (int) (SimulationWindow.height/blockSize)-2;
		
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
