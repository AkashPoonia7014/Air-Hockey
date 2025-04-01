

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Mallets extends Rectangle{

    int id;
    int xVelocity;
    int yVelocity;
    BufferedImage imgMall;
    public int speed = 3;
    Mallets(int x,int y,int malWidth,int malHeight,int id) {
        super(x,y,malWidth,malHeight);
        this.id = id;
        importImg();
    }

    public void importImg() {
        InputStream is1 = getClass().getResourceAsStream("/malls.png");
        try {
            imgMall = ImageIO.read(is1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(KeyEvent e) {
        switch (id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    setYDirection(-speed);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    setYDirection(speed);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    setXDirection(-speed);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    setXDirection(speed);
                    move();
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    setYDirection(-speed);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYDirection(speed);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    setXDirection(-speed);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    setXDirection(speed);
                    move();
                }
                break;
        }
    }
    public void keyReleased(KeyEvent e) {
        switch (id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    setYDirection(0);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    setYDirection(0);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    setXDirection(0);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    setXDirection(0);
                    move();
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    setYDirection(0);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYDirection(0);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    setXDirection(0);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    setXDirection(0);
                    move();
                }
                break;
        }
    }
    public void setXDirection(int xDirection) {
        xVelocity = xDirection;
    }
    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }
    public void move() {
        x = x + xVelocity;
        y = y + yVelocity;
    }
    public void draw(Graphics g) {
        importImg();
        if (id==1) {
            g.drawImage(imgMall, x, y, width, height, null);
        }
        else {
            g.drawImage(imgMall, x, y, width, height, null);
        }
    }

}


