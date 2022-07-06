import java.awt.Color;
import java.awt.Graphics2D;

public class Block {
	
	int x, y;
	Biome biome;
	
	public Block(int x, int y) {
		this.x = x;
		this.y = y;
		this.biome = Biome.RED_DESERT;
	}
	
	public void draw(Graphics2D g2) {
		
		g2.setColor(biome.getBiomeColor());
		
		g2.fillRect(x, y, SimulationPanel.blockSize, SimulationPanel.blockSize);
	}
	
	public void setBiome(Biome biome) {
		this.biome = biome;
	}

}
