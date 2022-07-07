import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class SimulationWindow extends JFrame {
	
	final String title = "Procedural World Generation Simulation";
	public static final int width = 1000;
	public static final int height = 800;
	
	static SimulationPanel sp;
	static JPanel buttonPanel;
	
	public SimulationWindow() {
		
		// window settings
		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		this.sp = new SimulationPanel();
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem importMenuItem = new JMenuItem("Import");
		fileMenu.add(importMenuItem);
		
		JMenuItem exportMenuItem = new JMenuItem("Export");
		exportMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalDateTime dateNow = LocalDateTime.now();
			    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh-mm-ss");
				String date = dateNow.format(dateFormatter);
				
				JFileChooser jfc = new JFileChooser();
				String dir = "";
				
				jfc.setDialogTitle("Open the directory of where to export the image file.");
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int status = jfc.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION) {
				  dir = jfc.getSelectedFile().getPath();
				  
				  File f = new File(dir + "/" + date + ".png");
				  if (!f.exists())
					f.mkdirs();
					try {
						ImageIO.write(World.map, "PNG", f);
						System.out.println("Map saved");
						
						//JOptionPane.showInputDialog(null);
						JOptionPane.showMessageDialog(null, "The image file was saved under " + dir);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				//dir = "screenshots/";
			}
			
		});
		fileMenu.add(exportMenuItem);
		
		JMenu worldMenu = new JMenu("World");
		menuBar.add(worldMenu);
		
		JMenuItem copySeed = new JMenuItem("Copy Seed");
		copySeed.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StringSelection seedToCopy = new StringSelection(String.valueOf(sp.world.seed));
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(seedToCopy, null);
				System.out.println("seed copied");
			}
			
		});
		worldMenu.add(copySeed);
		loadButtons();
	}
	
	private void loadButtons() {
		buttonPanel = new JPanel();
		
		// new map
		JButton loadnew = new JButton("Load new map");
		loadnew.setFocusable(false);
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
		
		
		// change frequency
		JTextField frequency = new JTextField();
		frequency.setToolTipText("Frequency");
		frequency.setColumns(4);
		frequency.setBounds(new Rectangle(10, 30));
		frequency.setText(String.valueOf(sp.world.frequency));
		frequency.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					sp.world.frequency = Double.parseDouble(frequency.getText());
					sp.world.generate();
					sp.repaint();
				} catch(Exception error) {
				}
			}
			
		});
		buttonPanel.add(frequency);
		
		
		//buttonPanel.setBackground(Color.BLUE);
		getContentPane().add(buttonPanel, BorderLayout.NORTH);
	}
	
	private void initiateMapImageUpdates() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				SimulationPanel.blocksHorizontal = getWidth();
				SimulationPanel.blocksVertical = getHeight();
				sp.setSize(new Dimension(getWidth(), getHeight()));
				sp.world.updateMapSize(getWidth(), getHeight());
				buttonPanel.repaint();
				//sp.world.generate();
				sp.repaint();
			}
			
		}, 0, 2500);
	}
	
	public void startSimulation() {
		//initiateMapImageUpdates();
		getContentPane().add(sp, BorderLayout.CENTER);
		setVisible(true);
	}

}
