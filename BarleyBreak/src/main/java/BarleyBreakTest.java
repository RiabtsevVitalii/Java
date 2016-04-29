import javax.swing.*;
import java.awt.*;

public class BarleyBreakTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                BoardFrame frame = new BoardFrame();
                frame.setTitle("BarleyBreak");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
