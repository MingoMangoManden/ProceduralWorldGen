import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class SimulationWindow extends JFrame {
	
	final String title = "Procedural World Generation Simulation";
	public static final int width = 1000;
	public static final int height = 800;
	
	static SimulationPanel sp;
	
	public SimulationWindow() {
		
		// window settings
		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		this.sp = new SimulationPanel();
		loadButtons();
		loadMenu();
	}
	
	private void loadMenu() {
		JMenuBar menuBar = new JMenuBar();
		//JMenu fileMenu = new JMenu("File");
		
		//JMenuItem save = new JMenuItem("Save");
		/*save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				File f = new File("image.png");
				try {
					ImageIO.write(World.map, "PNG", f);
					System.out.println("Map saved");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		});*/
		//save.setAccelerator(KeyStroke.getKeyStroke("n"));
		
		//fileMenu.add(save);
		//menuBar.add(fileMenu);
		add(menuBar);
	}
	
	private void loadButtons() {
		JPanel buttonPanel = new JPanel();
		
		// save
		JButton save = new JButton("Save image");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalDateTime dateNow = LocalDateTime.now();
			    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh-mm-ss");
			    
				String date = dateNow.format(dateFormatter);
				File f = new File("screenshots/" + date + ".png");
				if (!f.exists())
					f.mkdirs();
				
				System.out.println(date);
				try {
					ImageIO.write(World.map, "PNG", f);
					System.out.println("Map saved");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		buttonPanel.add(save);
		
		// new map
		JButton loadnew = new JButton("Load new map");
		loadnew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// load new map
				System.out.println("Loading new map");
				
				sp.world.reset(new Random().nextLong());
				sp.world.generate();
				sp.repaint();
			}
			
		});
		buttonPanel.add(loadnew);
		
		buttonPanel.setBackground(Color.BLUE);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void startSimulation() {
		add(sp, BorderLayout.CENTER);
		setVisible(true);
	}

}
