package andybot.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import andybot.model.Maze;
import andybot.model.Robot;

public class JMazePanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -5011576696483366305L;
    final static int CELL_SIZE = 40; // 20 pixels per cell
    private Maze maze;
    private int cellSize = CELL_SIZE;
    private boolean gameOver;
    private String gameOverMsg;
    
    
    /**
     * Create the panel.
     */
    public JMazePanel(Maze maze) {
        this( maze, CELL_SIZE);
    }
    
    public JMazePanel(Maze maze, int cellSize) {
        this.maze = maze;
        this.cellSize = cellSize;
        setPreferredSize( new Dimension(
                cellSize*maze.getRowSize(), 
                cellSize*maze.getColSize())
        );
    }
    
    public void setCellSize(int newCellSize) {
        this.cellSize = newCellSize;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        drawMaze(g, maze);
        drawRobot( g, maze.getRobot());
    }
    

    @Override
    public int getWidth() {
        return cellSize * maze.getColSize();
    }
    
    @Override
    public int getHeight() {
        return cellSize * maze.getRowSize();
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(getWidth(), getHeight());
    }

    private void drawMaze(Graphics g, Maze maze) {
        int[][] map = maze.getMapData();
        int sz = cellSize;
        for ( int ir = 0 ; ir < map.length; ir++) {
            for ( int ic = 0 ; ic < map[ir].length; ic++) {
                if ( map[ir][ic] == Maze.ROAD) {
                    drawRoad(g, ic*sz, ir*sz, sz);
                } else if ( map[ir][ic] == Maze.WALL ) {
                    drawWall(g, ic*sz, ir*sz, sz);
                }
            }
        }
    }

    private void drawRoad(Graphics g, int px, int py, int sz) {
        Color cache = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(px, py, sz, sz);
        g.setColor(cache);
    }
    
    private void drawWall(Graphics g, int px, int py, int sz) {
        Color cache = g.getColor();
        g.setColor(Color.BLACK);
        g.fillRect(px, py, sz, sz);
        g.setColor(cache);
    }
    
    private void drawRobot(Graphics g, Robot robot) {
        // 1. body
        Point loc = robot.getLocation();
        int sz = cellSize;
        int px = loc.x * sz;
        int py = loc.y * sz;
        int margin = 2; // px
        
        Color cache = g.getColor();
        g.setColor(Color.CYAN);
        g.fillRect(px+margin, py+margin, sz - 2*margin, sz - 2*margin);
        
        // 2. eyes
        drawEyes (robot, g, px+margin, py+margin, sz - 2*margin, sz - 2*margin);
        
        // 3. gameover
        drawGameOver(g);
        g.setColor(cache);
    }


    private void drawEyes(Robot bot, Graphics g, int px, int py, int width, int height) {
        int x =px, y = py, w = width, h = height ;
        int mgn = 2; // margin
        g.setColor(Color.RED);
        switch( bot.getDirection()) {
        case Maze.DIR_NORTH :
            x = px + mgn + 0;
            y = py + mgn + 0;
            w = width - 2*mgn;
            h = height/4;
            break;
        case Maze.DIR_EAST :
            x = (int)(px - mgn + width*0.75);
            y = py + mgn + 0;
            w = width/4;
            h = height - 2*mgn;
            break;
        case Maze.DIR_SOUTH :
            x = px + 2;
            y = (int) (py - mgn + height*0.75);
            w = width - 2*mgn;
            h = height/4;
            break;
        case Maze.DIR_WEST :
            x = px + mgn + 0;
            y = py + mgn + 0;
            w = width/4;
            h = height - 2*mgn;
            break;
        }
        
        g.fillRect(x, y, w, h);
    }

    private void drawGameOver(Graphics g) {
        if ( ! this.gameOver) {
            return ;
        }
        
        int W = maze.getRowSize() * cellSize;
        int H = maze.getColSize() * cellSize;
        Font font = new Font("Gulim", Font.BOLD, 14);
        
        Dimension fontDim = calculateFontArea ( this.gameOverMsg, font);
        int startX = (W - fontDim.width) /2;
        int startY = (H - fontDim.height)/2;
        g.setFont(font);
        g.drawString(gameOverMsg, startX, startY);
        
        this.gameOver = false;
        this.gameOverMsg = "";
        
        
    }
    
    private Dimension calculateFontArea(String text, Font font) {
        AffineTransform affinetransform = new AffineTransform();     
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
        
        int textwidth = (int)(font.getStringBounds(text, frc).getWidth());
        int textheight = (int)(font.getStringBounds(text, frc).getHeight());
        return new Dimension(textwidth, textheight);
    }

    public void setGameOver(String cause) {
        this.gameOver = true;
        this.gameOverMsg = cause;
    }
}
