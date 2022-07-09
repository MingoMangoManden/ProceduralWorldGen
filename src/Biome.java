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
			new Color(0, 221, 255), new Color(0,191,255), new Color(0, 105, 148), new Color(5, 79, 110), new Color(8, 24, 64)
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
	
	public Color getBiomeColor(double height) {
		
		if (this == Biome.OCEAN) {
			if (height < World.deepest)
				return clrs[4];
			else if (height < World.deeper)
				return clrs[3];
			else if (height < World.deep)
				return clrs[2];
			else if (height < World.lowerLevel)
				return clrs[1];
			else
				return clrs[0];
		}
		
		if (clrs.length > 1) {
			int num = (int) (Math.random()*clrs.length);
			return clrs[num];
		}
		return clrs[0];
	}

}
