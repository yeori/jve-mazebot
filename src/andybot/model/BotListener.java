package andybot.model;

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
}