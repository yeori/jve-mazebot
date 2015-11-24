package andybot.model;

import java.awt.Point;
/**
 * 로봇의 상태 변화(위치, 방향)를 통보받을때 사용합니다.
 * 
 * @author chminseo
 *
 */
interface BotListener {
    /**
     * 로봇의 위치가 oldLoc에서 curLoc으로 변경되었을때 호출됩니다.
     * 
     * @see {@link Robot#addBotListener(BotListener)}
     * @param oldLoc
     * @param curLoc
     */
    public void locationChanged (Coord oldLoc, Coord curLoc) ;
    /**
     * 로봇의 방향이 newDir로 바뀌었을때 호출됩니다.
     * 방향값은 {@link Maze} 의 4가지 방향값 중 하나입니다.
     * @see {@link Maze#DIR_NORTH} 
     * @see {@link Maze#DIR_EAST} 
     * @see {@link Maze#DIR_SOUTH} 
     * @see {@link Maze#DIR_WEST} 
     * @param oldDir
     * @param newDir
     */
    public void directionChanged ( int oldDir, int newDir) ;
}