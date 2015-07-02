package andybot.model;

import java.awt.Point;
/**
 * 로봇의 상태 변화(위치, 방향)를 통보받을때 사용합니다.
 * 
 * @author chminseo
 *
 */
public interface BotListener {
    /**
     * 로봇의 위치가 oldLoc에서 curLoc으로 변경되었을때 호출됩니다.
     * 
     * @see Robot#addBotListener(BotListener)
     * @param oldLoc
     * @param curLoc
     */
    public void locationChanged (Point oldLoc, Point curLoc) ;
    public void directionChanged ( int oldDir, int newDir) ;
}