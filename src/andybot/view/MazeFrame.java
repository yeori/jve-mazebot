package andybot.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import andybot.Loc;
import andybot.MazeException;
import andybot.BotStatus;
import andybot.view.renderer.MazeListRenderer;
import southbot.SouthMovement;
import andybot.model.Maze;
import andybot.model.MazeFactory;
import andybot.model.Robot;
import andybot.model.Robot.DIR;

import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.CompoundBorder;

public class MazeFrame extends JFrame {

    private static final long serialVersionUID = 1121929876266708942L;
    private JPanel contentPane;
    
    private Robot bot ;
    private JPanel controllPanel;
    private JButton btnLeft;
    private JButton btnRight;
    private JMazePanel mazePanel;
    private BotStatus cause;

    private Map<String, File> mazeFiles = new HashMap<>();
    private DefaultListModel<File> model = new DefaultListModel<>();
    
    private JLabel lblMazes;
    private JScrollPane scrollPane;
    private JList<File> mazeList;
    private JButton btnUp;
    private JButton btnDown;
    private JButton btnAuto;
    
    private volatile Timer timer ;
	private int delay = 500 ; // millisecond
	private JPanel panel_1;
	private JLabel lblInterval;
	private JTextField intervalField;
	private JButton btnIntervalChange;
	private JButton btnReset;
	private JLabel lblMazeView;
	private JPanel statWrapper;
	private JLabel lblStatus;
	private JPanel panel;
	private JLabel lblOfMoves;
	private JTextField moveField;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
    	
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	
                    MazeFrame frame = new MazeFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    

    /**
     * Create the frame.
     */
    public MazeFrame( ) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 680, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(6, 6));
        setContentPane(contentPane);
        
        JPanel robotInfoPanel = new JPanel();
        robotInfoPanel.setBorder(new CompoundBorder(new LineBorder(new Color(180, 180, 180)), new EmptyBorder(4, 4, 4, 4)));
        contentPane.add(robotInfoPanel, BorderLayout.WEST);
        robotInfoPanel.setLayout(new BorderLayout(0, 0));
        
        lblMazes = new JLabel("CHOOSE A MAZE");
        robotInfoPanel.add(lblMazes, BorderLayout.NORTH);
        
        scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        robotInfoPanel.add(scrollPane, BorderLayout.CENTER);
        
        mazeList = new JList<>(model);
        mazeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mazeList.setCellRenderer(new MazeListRenderer());
        scrollPane.setViewportView(mazeList);
        
        controllPanel = new JPanel();
        contentPane.add(controllPanel, BorderLayout.SOUTH);
        
        btnLeft = new JButton("LEFT");
        btnLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	moveBot( bot, Robot.DIR.WEST);
//                bot.moveLeft();
            }
        });
        controllPanel.add(btnLeft);
        
        btnRight = new JButton("RIGHT");
        btnRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	moveBot( bot, Robot.DIR.EAST);
//                bot.moveRight();
            }
        });
        controllPanel.add(btnRight);
        
        btnUp = new JButton("UP");
        btnUp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		moveBot( bot, Robot.DIR.NORTH);
//        		bot.moveUp();
        	}
        });
        controllPanel.add(btnUp);
        
        btnDown = new JButton("Down");
        btnDown.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		moveBot( bot, Robot.DIR.SOUTH);
//        		bot.moveDown();
        	}
        });
        controllPanel.add(btnDown);
        
        btnAuto = new JButton("AUTO");
        btnAuto.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		disableControl();
        		processAuto();
        	}
        });
        controllPanel.add(btnAuto);
        
        btnReset = new JButton("RESET");
        btnReset.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		resetControl();
        	}
        });
        controllPanel.add(btnReset);
        
        JPanel mazeWrapper = initMazePanel();
        contentPane.add(mazeWrapper, BorderLayout.CENTER);
        
        panel_1 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
        flowLayout.setAlignment(FlowLayout.LEADING);
        contentPane.add(panel_1, BorderLayout.NORTH);
        
        lblInterval = new JLabel("INTERVAL");
        panel_1.add(lblInterval);
        
        intervalField = new JTextField();
        intervalField.setText("500");
        panel_1.add(intervalField);
        intervalField.setColumns(10);
        
        btnIntervalChange = new JButton("CHANGE");
        btnIntervalChange.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String val = intervalField.getText();
        		updateTimerInterval(val);
        	}
        });
        
        panel_1.add(btnIntervalChange);
        
        loadMazeList();
        disableControl();
        this.mazeList.setEnabled(true);
        
        statWrapper = new JPanel();
        statWrapper.setBorder(new CompoundBorder(new LineBorder(new Color(180, 180, 180)), new EmptyBorder(4, 4, 4, 4)));
        contentPane.add(statWrapper, BorderLayout.EAST);
        statWrapper.setLayout(new BorderLayout(0, 0));
        
        lblStatus = new JLabel("STATUS");
        statWrapper.add(lblStatus, BorderLayout.NORTH);
        
        panel = new JPanel();
        statWrapper.add(panel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);
        
        lblOfMoves = new JLabel("# of Moves");
        GridBagConstraints gbc_lblOfMoves = new GridBagConstraints();
        gbc_lblOfMoves.anchor = GridBagConstraints.LINE_START;
        gbc_lblOfMoves.insets = new Insets(0, 0, 5, 0);
        gbc_lblOfMoves.gridx = 0;
        gbc_lblOfMoves.gridy = 0;
        panel.add(lblOfMoves, gbc_lblOfMoves);
        
        moveField = new JTextField();
        moveField.setEnabled(false);
        GridBagConstraints gbc_moveField = new GridBagConstraints();
        gbc_moveField.fill = GridBagConstraints.HORIZONTAL;
        gbc_moveField.gridx = 0;
        gbc_moveField.gridy = 1;
        panel.add(moveField, gbc_moveField);
        moveField.setColumns(10);
    }

	protected void moveBot(Robot bot, DIR dir) {
		BotStatus status = mazePanel.getCurrentMaze().moveBot(bot, dir);
		if( status == BotStatus.NOT_ON_THE_ROAD || 
				status == BotStatus.OUT_OF_MAP || 
				status == BotStatus.ON_THE_EXIT) {
			renderGameOver(status);
		} else {
			mazePanel.repaint();
		}
	}


	protected void updateTimerInterval(String msec) {
    	/*
    	 * 숫자 이외의 문자열을 모두 걸러낸 후 숫자들만 이어붙입니다.
    	 */
    	String [] digits = msec.split("[^0-9]");
		msec = String.join("", digits);
		intervalField.setText(msec);
		
    	int newDelay = 1000;
    	try {
			newDelay = Integer.parseInt(msec);
			this.delay = newDelay;
		} catch (NumberFormatException e) {
			System.out.println("invalid millis value: " + msec + ", set to 1000 millis");
		}
    	
    	if ( timer != null && timer.isRunning() ) {
    		timer.setDelay(newDelay);
    	}
		
	}
    
    protected void resetControl() {
    	timer = null;
    	try( FileInputStream fis = new FileInputStream(this.mazeList.getSelectedValue()) ) {
			updateMaze(this.mazeList.getSelectedValue().getName(), fis);
			enableControl();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected void disableControl() {
		btnLeft.setEnabled(false);
		btnRight.setEnabled(false);
		btnUp.setEnabled(false);
		btnDown.setEnabled(false);
		btnAuto.setEnabled(false);
		btnReset.setEnabled(false);
		mazeList.setEnabled(false);
	}
    
    protected void enableControl() {
    	btnLeft.setEnabled(true);
		btnRight.setEnabled(true);
		btnUp.setEnabled(true);
		btnDown.setEnabled(true);
		btnAuto.setEnabled(true);
		btnReset.setEnabled(false);
		mazeList.setEnabled(true);
    }

	protected void processAuto() {
		final Maze maze = mazePanel.getCurrentMaze();
    	final SouthMovement finder = new SouthMovement();
    	timer = new Timer( delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				final Robot bot = new ProxyBot( maze.getRobots().get(0)) ;
				finder.moveRobot(maze, bot);
			}
		});
    	finder.onReady(maze, maze.getRobot() );
    	timer.start();
	}

	private void loadMazeList() {
    	String [] pathes = System.getProperty("java.class.path").split(File.pathSeparator);
    	MazeFileReader loader = new MazeFileReader();
    	List<File> mazefiles = loader.findMazeFiles(pathes);
    	
    	for ( File f : mazefiles) {
    		this.mazeFiles.put(f.getName(), f);
    		model.addElement(f);
    	}
    	
    	mazeList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				JList<File> list = (JList<File>) e.getSource();
				if ( !e.getValueIsAdjusting() ) {
					try {
						File file = list.getSelectedValue();
						updateMaze (file.getName(), new FileInputStream(list.getSelectedValue()) );
						enableControl();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
	}
    
    void updateMaze(String filePath, InputStream in) {
        MazeFactory mf = new MazeFactory(in);
        try {
        	Maze maze = (Maze) mf.createMaze();
        	Robot robot = maze.getRobot();
        	
        	this.bot = robot;
        	mazePanel.updateMaze(maze);
        } catch ( MazeException e ) {
        	JOptionPane.showMessageDialog(
        			this, 
        			e.getMessage(), 
        			"FILE " + filePath, JOptionPane.ERROR_MESSAGE);
        }
	}

	/**
     *           north
     *       +------------> X(colSize)
     *       | . . A . .                    A(2,0)
     *       | . . B C D                    B(2,1) C(3,1) D(4,1)
     *  west | . J . . E    east     J(1,2)               E(4,2)
     *       | . I H G F             I(1,3) H(2,3) G(3,3) F(4,3)
     *       v
     *       Y(rowSize)
     *       
     *           south
     */
    private JPanel initMazePanel () {
    	JPanel mazeWrapper = new JPanel();
    	mazeWrapper.setBorder(new CompoundBorder(new LineBorder(new Color(180, 180, 180)), new EmptyBorder(4, 4, 4, 4)));
    	mazeWrapper.setLayout(new BorderLayout());
        this.mazePanel = new JMazePanel(25);
        mazeWrapper.add(this.mazePanel, BorderLayout.CENTER);
        
        lblMazeView = new JLabel("MAZE VIEW");
        mazeWrapper.add(lblMazeView, BorderLayout.NORTH);
        return  mazeWrapper;
    }

    protected void renderGameOver(BotStatus cause) {
        mazePanel.setGameOver( cause.getCause() );
        disableControl();
        btnReset.setEnabled(true);
    }
}
