import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Random;

public class World {
	
	public static BufferedImage map = new BufferedImage(SimulationWindow.width, SimulationWindow.height, BufferedImage.TYPE_INT_RGB);
	
	int sampleSize;
	Random rand;
	long seed;
	OpenSimplexNoise heightMap;
	OpenSimplexNoise continentalnessMap;
	double frequency, amplitude;
	
	double oceanHeight = .25;
	double beachHeight = .4;
	double hillsHeight = .55;
	double snowHeight = .75;
	
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
				Block block = new Block(x, y);
				evalBiome(block);
				
				double height = ( heightMap.eval(x*frequency, y*frequency) + continentalnessMap.eval(x*frequency, y*frequency) ) * 0.5;
				Color color = block.biome.getBiomeColor(height);
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
		double height = heightMap.eval(b.x*frequency, b.y*frequency);// + continentalness ) / 2;
		
		if (height <= oceanHeight)
			b.setBiome(Biome.OCEAN);
		else if (height > snowHeight)
			b.setBiome(Biome.SNOWY_HILLS);
		else if (height > hillsHeight)
			b.setBiome(Biome.HILLS);
		else if (height < beachHeight)
			b.setBiome(Biome.DESERT);
		else
			b.setBiome(Biome.PLAINS);
	}
	
	public void updateMapSize(int width, int height) {
		Image tmp = map.getScaledInstance(width, height, Image.SCALE_FAST);
	    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2 = img.createGraphics();
	    g2.drawImage(tmp, 0, 0, null);
	    g2.dispose();
	    
	    map = img;
	}
	
	public void reset(long newSeed) {
		this.rand = new Random(newSeed);
		this.seed = newSeed;
		this.heightMap = new OpenSimplexNoise(seed);
		this.continentalnessMap = new OpenSimplexNoise(seed*2);
	}

}
