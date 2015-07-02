package andybot.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestRobot {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_robot_movement() {
        /*
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
//        Maze mz = new Maze(4, 5); // 4x5
//        mz.setPath(2, 0); // A
//        mz.setPath(2, 1); // B
//        mz.setPath(3, 1); // C
//        mz.setPath(4, 1); // D
//        mz.setPath(4, 2); // E
//        mz.setPath(4, 3); // F
//        mz.setPath(3, 3); // G
//        mz.setPath(2, 3); // H
//        mz.setPath(1, 3); // I
//        mz.setPath(1, 2); // J
       
        // starting from A(2,0) faced to NORTH(default)
        Robot bot = new Robot(2, 0);
        
        /* 1. A 에서 B로 이동: 뒤로돌고 앞으로 직진 1회*/ 
     
        /* 2. B에서 D로 이동: 왼쪽회전 앞으로 직진 2회*/

        /*3. D에서 F로 이동: 오른쪽 회전 앞으로 직진 2회 */
        
        /* F에서 I로 이동: 오른쪽 회전, 앞으로 직진 3회 */
        
        /* I에서 J로 이동: 오른쪽 회전, 앞으로 직진 1회 */
        
    }

}
