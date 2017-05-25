package andybot.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JPanel;

import andybot.Coord;
import andybot.IMaze;
import andybot.IRobot;
import andybot.model.InternalMaze;

public class JMazePanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -5011576696483366305L;
    final static int CELL_SIZE = 20; // 20 pixels per cell
    private InternalMaze maze;
    private int cellSize = CELL_SIZE;
    private boolean gameOver;
    private String gameOverMsg;
    
    
    /**
     * Create the panel.
     */
    public JMazePanel(int cellSize) {
        this( null, CELL_SIZE);
    }
    
    public JMazePanel(InternalMaze maze, int cellSize) {
    	this.cellSize = cellSize;
    	this.maze = maze;
    	updateMaze(maze);
    }
    
    public IMaze getCurrentMaze() {
    	return this.maze;
    }
    public void updateMaze( InternalMaze newMaze) {
    	maze = newMaze ;
    	if ( maze == null) {
    		return ;
    	}
    	gameOver = false;
    	gameOverMsg = "";
//    	setPreferredSize( new Dimension(
//                cellSize*maze.getRowSize(), 
//                cellSize*maze.getColSize())
//        );
    	repaint();
    }
    
    public void setCellSize(int newCellSize) {
        this.cellSize = newCellSize;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    	g.setColor(Color.WHITE);
    	g.fillRect(0, 0, getWidth(), getHeight());
    	int sz = Math.min(getWidth(), getHeight())/(maze==null ? cellSize : maze.getRowSize());
    	if ( maze != null) {
    		drawMaze(g, maze, sz);
    		
    		List<IRobot> robots = maze.getRobots();
    		for ( IRobot r : robots) {
    			drawRobot( g, r, sz);    		
    		}
    	}
    	
    	// 3. gameover
    	if ( gameOver ) {
    		drawGameOver(g, sz);    		
    	}
    }

    private void drawMaze(Graphics g, InternalMaze maze, int sz) {
        int[][] map = maze.getMapData();
        
        for ( int ir = 0 ; ir < map.length; ir++) {
            for ( int ic = 0 ; ic < map[ir].length; ic++) {
                if ( map[ir][ic] == IMaze.ROAD) {
                    drawRoad(g, ic*sz, ir*sz, sz);
                } else if ( map[ir][ic] == IMaze.WALL ) {
                    drawWall(g, ic*sz, ir*sz, sz);
                }
                
            }
        }
        Coord exit = maze.getExit();
        drawGate(g, exit.colIndex()*sz, exit.rowIndex()*sz, sz, "E");
    }

    private void drawGate(Graphics g, int px, int py, int sz, String v) {
    	Color cache = g.getColor();
    	Font cachef = g.getFont();
    	
		g.setColor(Color.YELLOW);
		g.fillRect(px, py, sz, sz);
		g.setColor(Color.BLACK);
		
		Font f = new Font("", Font.PLAIN, (int)(sz*0.7));
		g.setFont(f);
		
		Dimension d = calculateFontArea(v, f);
		g.drawString(v, px + d.width/2, py+d.height);
		
        g.setColor(cache);
        g.setFont(cachef);
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
    
    private void drawRobot(Graphics g, IRobot robot, int sz) {
        // 1. body
        Coord loc = robot.getLocation();
//        int sz = cellSize;
        int px = loc.colIndex() * sz;
        int py = loc.rowIndex() * sz;
        int margin = 2; // px
        
        Color cache = g.getColor();
        g.setColor(Color.CYAN);
        g.fillRect(px+margin, py+margin, sz - 2*margin, sz - 2*margin);
        
        // 2. eyes
        drawEyes (robot, g, px+margin, py+margin, sz - 2*margin, sz - 2*margin);
        
        g.setColor(cache);
    }


    private void drawEyes(IRobot bot, Graphics g, int px, int py, int width, int height) {
        int x =px, y = py, w = width, h = height ;
        int mgn = 2; // margin
        g.setColor(Color.RED);
        switch( bot.getDirection()) {
        case NORTH :
            x = px + mgn + 0;
            y = py + mgn + 0;
            w = width - 2*mgn;
            h = height/4;
            break;
        case EAST :
            x = (int)(px - mgn + width*0.75);
            y = py + mgn + 0;
            w = width/4;
            h = height - 2*mgn;
            break;
        case SOUTH :
            x = px + 2;
            y = (int) (py - mgn + height*0.75);
            w = width - 2*mgn;
            h = height/4;
            break;
        case WEST :
            x = px + mgn + 0;
            y = py + mgn + 0;
            w = width/4;
            h = height - 2*mgn;
            break;
        }
        
        g.fillRect(x, y, w, h);
    }

    private void drawGameOver(Graphics g, int sz) {
        Color cached = g.getColor();
        
        g.setColor(Color.RED);
        int W = maze.getRowSize() * sz;
        int H = maze.getColSize() * sz;
        Font font = new Font("Gulim", Font.BOLD, sz);
        
        Dimension fontDim = calculateFontArea ( this.gameOverMsg, font);
        int startX = (W - fontDim.width) /2;
        int startY = (H - fontDim.height)/2;
        g.setFont(font);
        g.drawString(gameOverMsg, startX, startY);
        
        g.setColor(cached);
        
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
        this.repaint();
    }
}
