import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
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
import java.awt.Font;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class SimulationWindow extends JFrame {
	
	final String title = "Procedural World Generation Simulation";
	public static final int width = 1000;
	public static final int height = 800;
	
	static SimulationPanel sp;
	static JPanel buttonPanel;
	private JTextField oceanLevel;
	
	public SimulationWindow() {
		setFont(new Font("Segoe UI", Font.PLAIN, 12));
		getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		// window settings
		setTitle("Procedural World Generation Sampler");
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		this.sp = new SimulationPanel();
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(fileMenu);
		
		JMenuItem importMenuItem = new JMenuItem("Import");
		importMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		fileMenu.add(importMenuItem);
		
		JMenuItem exportMenuItem = new JMenuItem("Export");
		exportMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 12));
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
			}
			
		});
		fileMenu.add(exportMenuItem);
		loadButtons();
	}
	
	private void loadButtons() {
		buttonPanel = new JPanel();
		
		// terrain values label
		JLabel terrainValueLabel = new JLabel("Terrain values");
		terrainValueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		terrainValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		// change frequency
		JTextField frequency = new JTextField();
		frequency.setToolTipText("");
		frequency.setColumns(4);
		frequency.setBounds(new Rectangle(10, 30));
		frequency.setText(String.valueOf(sp.world.frequency));
		frequency.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (frequency.getText() != "" && frequency.getText() != null)
						sp.world.frequency = Double.parseDouble(frequency.getText());
						sp.world.generate();
						sp.repaint();
				} catch(Exception error) {
				}
			}
			
		});
		
		JLabel frequencyLabel = new JLabel("Frequency");
		frequencyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		
		// ocean level
		oceanLevel = new JTextField();
		oceanLevel.setText(String.valueOf(World.oceanHeight));
		oceanLevel.setColumns(4);
		oceanLevel.setBounds(new Rectangle(0, 0, 10, 30));
		oceanLevel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (oceanLevel.getText() != "" && oceanLevel.getText() != null)
					World.oceanHeight = Double.parseDouble(oceanLevel.getText());
					sp.world.generate();
					sp.repaint();
			}
			
		});
		
		JLabel oceanHeightLabel = new JLabel("Ocean level");
		oceanHeightLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		
		// new map
		JButton loadnew = new JButton("Load New Map");
		loadnew.setBackground(SystemColor.menu);
		loadnew.setFont(new Font("Segoe UI", Font.BOLD, 12));
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
		
		
		// copying seed
		JButton copySeedButton = new JButton("Copy Seed");
		copySeedButton.setBackground(SystemColor.menu);
		copySeedButton.setFocusable(false);
		copySeedButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
		copySeedButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StringSelection seedToCopy = new StringSelection(String.valueOf(sp.world.seed));
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(seedToCopy, null);
				System.out.println("seed copied");
			}
			
		});
		
		
		// layout
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(frequencyLabel)
							.addGap(18)
							.addComponent(frequency, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addGap(5)
							.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(copySeedButton)
								.addComponent(loadnew, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(terrainValueLabel))
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(oceanHeightLabel, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(oceanLevel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_buttonPanel.setVerticalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(loadnew)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(copySeedButton)
					.addGap(33)
					.addComponent(terrainValueLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(frequency, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(frequencyLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(oceanLevel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addGap(1)
							.addComponent(oceanHeightLabel, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(572, Short.MAX_VALUE))
		);
		buttonPanel.setLayout(gl_buttonPanel);
		buttonPanel.setOpaque(false);
		buttonPanel.setBackground(new Color(0, 0, 0, 0));
		getContentPane().add(buttonPanel, BorderLayout.WEST);
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

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
