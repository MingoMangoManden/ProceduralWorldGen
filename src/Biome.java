import java.awt.Color;

public enum Biome {
	
	PLAINS(new Color[] {
			new Color(0,128,0), new Color(0,100,0), new Color(34,139,34)
	}),
	DESERT(new Color[] {
			new Color(242, 209, 107)
	}),
	RED_DESERT(new Color[] {
			new Color(215, 122, 105)
	}),
	OCEAN(new Color[] {
			new Color(0,191,255)
	}),
	HILLS(new Color[] {
			Color.GRAY
	}),
	SNOWY_HILLS(new Color[] {
			Color.WHITE
	});
	//DESERT, RED_DESERT, OCEAN, HILLS, SNOWY_HILLS;
	
	Color[] clrs;
	
	Biome(Color[] clrs) {
		this.clrs = clrs;
	}
	
	public Color getBiomeColor(long seed) {
		if (clrs.length > 1) {
			int num = (int) (Math.random()*clrs.length);
			return clrs[num];
		}
		return clrs[0];
	}

}
