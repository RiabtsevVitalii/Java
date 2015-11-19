package src;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

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