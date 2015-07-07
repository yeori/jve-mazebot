package andybot.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import andybot.model.BotListener;
import andybot.model.FileMazeFactory;
import andybot.model.IMazeFactory;
import andybot.model.IMazeListener;
import andybot.model.IMazeListener.DeathCause;
import andybot.model.Maze;
import andybot.model.Robot;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.awt.event.ActionEvent;

public class WinMain extends JFrame {

    private static final long serialVersionUID = 1121929876266708942L;
    private JPanel contentPane;
    private JTextField facingField;
    
    private Robot bot = new Robot(2,0); // starts at A
    private JPanel controllPanel;
    private JButton btnLeft;
    private JButton btnRight;
    private JButton btnMove;
    private JMazePanel mazePanel;
    private DeathCause cause;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    IMazeFactory mf = new FileMazeFactory(new File("maze01.mz"));
                    WinMain frame = new WinMain(mf);
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
    public WinMain(IMazeFactory mfac) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        JPanel robotInfoPanel = new JPanel();
        robotInfoPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        contentPane.add(robotInfoPanel, BorderLayout.WEST);
        GridBagLayout gbl_robotInfoPanel = new GridBagLayout();
        gbl_robotInfoPanel.columnWidths = new int[]{0, 0, 0};
        gbl_robotInfoPanel.rowHeights = new int[]{0, 0, 0};
        gbl_robotInfoPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_robotInfoPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        robotInfoPanel.setLayout(gbl_robotInfoPanel);
        
        JLabel lblNewLabel = new JLabel("FACING");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        robotInfoPanel.add(lblNewLabel, gbc_lblNewLabel);
        
        facingField = new JTextField();
        facingField.setEditable(false);
        GridBagConstraints gbc_facingField = new GridBagConstraints();
        gbc_facingField.fill = GridBagConstraints.HORIZONTAL;
        gbc_facingField.gridx = 1;
        gbc_facingField.gridy = 0;
        robotInfoPanel.add(facingField, gbc_facingField);
        facingField.setColumns(10);
        
        controllPanel = new JPanel();
        contentPane.add(controllPanel, BorderLayout.SOUTH);
        
        btnLeft = new JButton("LEFT");
        btnLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bot.turnLeft();
            }
        });
        controllPanel.add(btnLeft);
        
        btnRight = new JButton("RIGHT");
        btnRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bot.turnRight();
            }
        });
        controllPanel.add(btnRight);
        
        btnMove = new JButton("MoveForward");
        btnMove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bot.moveForward(1);
            }
        });
        controllPanel.add(btnMove);
        
        mazePanel = initMazePanel(mfac);
        contentPane.add(mazePanel, BorderLayout.CENTER);
        
        installRobotListener ( bot);
    }
    
    private void installRobotListener(Robot bot) {
        bot.addBotListener(new BotListener() {
            
            @Override
            public void locationChanged(Point oldLoc, Point curLoc) {
                mazePanel.repaint();
            }
            
            @Override
            public void directionChanged(int oldDir, int newDir) {
                printLoc( newDir);
                mazePanel.repaint();
                
            }
        });
        printLoc(bot.getDirection());
    }
    
    private void printLoc(int dir) {
        String v = "???";
        if ( dir == Maze.DIR_NORTH) {
            v = "NORTH";
        } else if ( dir == Maze.DIR_EAST) {
            v = "EAST";
        } else if ( dir == Maze.DIR_SOUTH) {
            v = "SOUTH";
        } else if ( dir == Maze.DIR_WEST) {
            v = "WEST";
        }
        facingField.setText(v);
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
    private JMazePanel initMazePanel (IMazeFactory fac) {
        Maze maze =fac.createMaze();
        maze.setBot(bot);
        JMazePanel panel = new JMazePanel(maze, 25);
        installMazeListener ( maze);
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
            public void robotDead(Robot bot, DeathCause cause) {
                // TODO Auto-generated method stub
                renderGameOver(cause);
            }
            
        });
    }

    protected void renderGameOver(DeathCause cause) {
        mazePanel.setGameOver( cause.getCause() );
    }
}
