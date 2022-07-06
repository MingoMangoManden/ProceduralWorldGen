import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Random;

public class World {
	
	public static final BufferedImage map = new BufferedImage(SimulationWindow.width, SimulationWindow.height, BufferedImage.TYPE_INT_RGB);
	
	int sampleSize;
	Random rand;
	long seed;
	OpenSimplexNoise heightMap;
	OpenSimplexNoise continentalnessMap;
	double frequency, amplitude;
	
	public World(int sampleSize) {
		this.sampleSize = sampleSize;
		this.rand = new Random();
		this.seed = rand.nextLong();
		this.heightMap = new OpenSimplexNoise(seed);
		this.continentalnessMap = new OpenSimplexNoise(seed*2);
		this.frequency = 0.004;
		this.amplitude = 10;
	}
	
	public void generate() {
		for (int y = 0; y < SimulationPanel.blocksVertical; y++) {
			for (int x = 0; x < SimulationPanel.blocksHorizontal; x++) {
				Block block = new Block(x*SimulationPanel.blockSize, y*SimulationPanel.blockSize);
				evalBiome(block);
				
				Color color = block.biome.getBiomeColor(seed);
				int r = color.getRed();
				int g = color.getGreen();
				int b = color.getBlue();
				//int rgb = 0xFFFF * r + 0xFF * g + b;
				int rgb = r*65536 + g*256 + b;
				map.setRGB(x, y, rgb);
			}
		}
	}
	
	private void evalBiome(Block b) {
		//double height = heightMap.eval(b.x, b.y);
		double continentalness = continentalnessMap.eval(b.x*frequency, b.y*frequency);
		double height = ( heightMap.eval(b.x*frequency, b.y*frequency) + continentalness ) / 2;
		
		if (height > 0.25)
			b.setBiome(Biome.PLAINS);
		else
			b.setBiome(Biome.OCEAN);
	}
	
	public void reset(long newSeed) {
		this.rand = new Random(newSeed);
		this.seed = newSeed;
		this.heightMap = new OpenSimplexNoise(seed);
		this.continentalnessMap = new OpenSimplexNoise(seed*2);
	}

}
