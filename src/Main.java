
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

class GameWindow extends JFrame{

    GamePanel gamePanel;

    GameWindow(GamePanel gamePanel){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(gamePanel);
        this.setTitle("PongFINITY");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setBackground(Color.DARK_GRAY);
        this.setVisible(true);
    }
}
class GamePanel extends JPanel implements Runnable{
    static final int GAME_WIDTH = 950;
    static final int GAME_HEIGHT = 550;
    static final int BALL_DIAMETER = 35;
      int xAxisOfBall = 457;
    static final int MALLETS_DIAMETER = 70;
    int xAxisOfMal1 = 235;
    int yAxisOfMal1 = 230;
    int xAxisOfMal2 = 635;
    int yAxisOfMal2 = 230;

    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Mallets mallets1;
    Mallets mallets2;
    Ball ball;
    Score score;
    BufferedImage imgField,imgBall;
    boolean gameStp = true;

    GamePanel(){

        setPanelSize();
        importImg();

        newBall();
        newMallets();

        gameThread = new Thread(this);
        gameThread.start();


        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        this.addKeyListener(new AL());
        this.setFocusable(true);
        this.requestFocusInWindow();

    }

    public void newBall() {
        Random random = new Random();
        ball = new Ball(xAxisOfBall,random.nextInt(100,450-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);

    }
    public void newMallets() {

        mallets1 = new Mallets(xAxisOfMal1,yAxisOfMal1,MALLETS_DIAMETER,MALLETS_DIAMETER,1);
        mallets2 = new Mallets(xAxisOfMal2,yAxisOfMal2,MALLETS_DIAMETER,MALLETS_DIAMETER,2);
    }
    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/lines.png");
        InputStream is1 = getClass().getResourceAsStream("/balls.png");
        try {
            imgField = ImageIO.read(is);
            imgBall = ImageIO.read(is1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
        drawField(g);
        mallets1.draw(g);
        mallets2.draw(g);
        ball.draw(g);
        score.draw(g);
        Toolkit.getDefaultToolkit().sync();

    }

    public void draw(Graphics g) {
    }

    public void drawField(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Stroke stroke = new BasicStroke(5f);

        Color colorRed = new Color(255,70,70);
        Color colorBlue = new Color(51,204,255);

        g2d.setStroke(stroke);

        g2d.setColor(Color.BLACK);
        g2d.drawLine(100,151,100,400);
        g2d.drawLine(850,150,850,400);

        g2d.setColor(colorRed);
        g2d.drawLine(100,100,475,100);
        g2d.drawLine(100,100,100,145);
        g2d.drawLine(100,404,100,450);
        g2d.drawLine(100,450,475,450);

        g2d.setColor(colorBlue);
        g2d.drawLine(475,100,850,100);
        g2d.drawLine(850,100,850,146);
        g2d.drawLine(850,404,850,450);
        g2d.drawLine(850,450,475,450);

        g2d.drawImage(imgField,103,103,null);
    }

    public void move() {
        mallets1.move();
        mallets2.move();
        ball.move();
    }

    public void checkCollision() {

        // Stop BALL at edges of our field
        if (ball.y <= 100) {
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.y >=445-BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.x <= 100 && ball.y <=145) {
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.x <= 100 && ball.y >=404-BALL_DIAMETER) {
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.x >= 850-BALL_DIAMETER && ball.y <=146) {
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.x >= 850-BALL_DIAMETER && ball.y >=404-BALL_DIAMETER) {
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(-ball.yVelocity);
        }


        //   Bounce Ball of the Mallets
        if (ball.intersects(mallets1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.yVelocity = Math.abs(ball.yVelocity);
//            ball.xVelocity++;            // for more difficulty
//            if(ball.yVelocity>0)
//                ball.yVelocity++;      //for more difficulty
//            else
//                ball.yVelocity--;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

//        if (ball.x -BALL_DIAMETER/2 <= mallets1.x -MALLETS_DIAMETER/2) {
//            ball.setXDirection(ball.xVelocity);
//            ball.setYDirection(ball.yVelocity);
//        }

        if (ball.intersects(mallets2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.yVelocity = Math.abs(ball.yVelocity);
//            ball.xVelocity++;                // for more difficulty
//            if(ball.yVelocity>0)
//                ball.yVelocity++;           //  for more difficulty
//            else
//                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        //    Score points
        if (ball.x <=80 && ball.y>= 145- BALL_DIAMETER) {
            score.player2++;
            newMallets();
            newBall();
        }
        if (ball.x <= 80 && ball.y <= 404) {
            score.player2++;
            newMallets();
            newBall();
        }
        if (ball.x >=870 && ball.y>= 145- BALL_DIAMETER) {
            score.player1++;
            newMallets();
            newBall();
        }
        if (ball.x >= 850 && ball.y <= 404) {
            score.player1++;
            newMallets();
            newBall();
        }




        //     Stop mallets at edges of field FOR MALLETS 1
        if (mallets1.x <= 100) {
            mallets1.x = 100;
        }
        if (mallets1.x >= 475-MALLETS_DIAMETER) {
            mallets1.x = 475-MALLETS_DIAMETER;
        }
        if (mallets1.y <= 100) {
            mallets1.y = 100;
        }
        if (mallets1.y >= 450-MALLETS_DIAMETER) {
            mallets1.y = 450-MALLETS_DIAMETER;
        }

        //      Stop mallets at edges of fieldFOR MALLETS 2
        if (mallets2.x <= 475) {
            mallets2.x = 475;
        }
        if (mallets2.x >= 850-MALLETS_DIAMETER) {
            mallets2.x = 850-MALLETS_DIAMETER;
        }
        if (mallets2.y <= 100) {
            mallets2.y = 100;
        }
        if (mallets2.y >= 450-MALLETS_DIAMETER) {
            mallets2.y = 450-MALLETS_DIAMETER;
        }
    }
    public class AL extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            mallets1.keyPressed(e);
            mallets2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e) {
            mallets1.keyReleased(e);
            mallets2.keyReleased(e);
        }
    }
    void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
        setPreferredSize(size);
    }

    @Override
    public void run() {

        final int set_FPS = 90;
        final int set_UPS = 120;

        double timePerFrame = 1000000000.0 / set_FPS;
        double timePerUpdate = 1000000000.0 / set_UPS;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;

        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;

            previousTime = currentTime;

            if (deltaU >= 1) {
                move();
                checkCollision();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                repaint();
                frames++;
                deltaF--;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                frames = 0;
                updates = 0;
            }
        }
    }
}
class Game {
    GamePanel gamePanel;
    GameWindow gameWindow;
    Game(){
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
    }

}

public class Main {
    public static void main(String[] args) {
        new Game();
    }
}
