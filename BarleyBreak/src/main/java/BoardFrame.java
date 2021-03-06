import javax.swing.JFrame;
import java.io.*;

public class BoardFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 305;
    private static final int DEFAULT_HEIGHT = 328;

    public BoardFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationByPlatform(true);
        setResizable(false);

        add(new BoardComponent());
    }
}
