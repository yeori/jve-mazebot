package andybot.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import andybot.model.Coord;
import andybot.model.IMazeFactory;
import andybot.model.MazeFactory;
import andybot.model.MazeLoader;
import andybot.model.IMazeListener;
import andybot.model.IMazeListener.GameOverCause;
import andybot.model.path.DefaultPathFinder;
import andybot.view.renderer.MazeListRenderer;
import andybot.model.Maze;
import andybot.model.Robot;

import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class WinMain extends JFrame {

    private static final long serialVersionUID = 1121929876266708942L;
    private JPanel contentPane;
    
    private Robot bot ;
    private JPanel controllPanel;
    private JButton btnLeft;
    private JButton btnRight;
    private JMazePanel mazePanel;
    private GameOverCause cause;

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
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
    	
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	
                    WinMain frame = new WinMain();
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
    public WinMain( ) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        JPanel robotInfoPanel = new JPanel();
        robotInfoPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        contentPane.add(robotInfoPanel, BorderLayout.WEST);
        robotInfoPanel.setLayout(new BorderLayout(0, 0));
        
        lblMazes = new JLabel("Mazes");
        robotInfoPanel.add(lblMazes, BorderLayout.NORTH);
        
        scrollPane = new JScrollPane();
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
                bot.moveLeft();
            }
        });
        controllPanel.add(btnLeft);
        
        btnRight = new JButton("RIGHT");
        btnRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bot.moveRight();
            }
        });
        controllPanel.add(btnRight);
        
        btnUp = new JButton("UP");
        btnUp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		bot.moveUp();
        	}
        });
        controllPanel.add(btnUp);
        
        btnDown = new JButton("Down");
        btnDown.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		bot.moveDown();
        	}
        });
        controllPanel.add(btnDown);
        
        btnAuto = new JButton("AUTO");
        btnAuto.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		processAuto();
        	}
        });
        controllPanel.add(btnAuto);
        
        mazePanel = initMazePanel();
        contentPane.add(mazePanel, BorderLayout.CENTER);
        
        loadMazeList();
    }
    
    protected void processAuto() {
    	final DefaultPathFinder finder = new DefaultPathFinder(mazePanel.getCurrentMaze(), bot);
    	timer = new Timer( delay, finder);
    	timer.start();
    	Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while ( ! finder.isFinished() ) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
//				timer.stop();
				System.out.println("stop timer");
			}
		});
    	t.start();
	}



	private void loadMazeList() {
    	String [] pathes = System.getProperty("java.class.path").split(File.pathSeparator);
    	MazeLoader loader = new MazeLoader();
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
						updateMaze ( new FileInputStream(list.getSelectedValue()) );
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
	}
    
    void updateMaze(InputStream in) {
        IMazeFactory mf = new MazeFactory(in);
        Maze maze = mf.createMaze();
        installMazeListener(maze);
        Coord start = maze.getStartLoc();
        maze.addRobot ( start.x(), start.y(), "AndyBot");
        this.bot = maze.getRobot();
        mazePanel.updateMaze(maze);
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
    private JMazePanel initMazePanel () {
        JMazePanel panel = new JMazePanel(25);
        return panel;
    }

    private void installMazeListener(Maze maze) {
        final PrintStream out = System.out;
        maze.addMazeListener(new IMazeListener(){

            @Override
            public void robotAdded(Robot newbot) {
                out.println("new bot: " + newbot.getName());
            }

            @Override
            public void gameOver(Robot bot, GameOverCause cause) {
            	renderGameOver(cause);
            	if( timer!= null) {
            		timer.stop();
            	}
            }
			@Override
			public void robotMoved(Robot bot, Coord oldCoord) {
				mazePanel.repaint();
			}
			
			
            
        });
    }

    protected void renderGameOver(GameOverCause cause) {
        mazePanel.setGameOver( cause.getCause() );
    }
}
