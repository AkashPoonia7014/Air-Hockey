

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class Ball extends Rectangle {

    BufferedImage imgBall;
    Random random;
    int xVelocity;
    int yVelocity;
    int initialSpeed = 2;
    static final int GAME_WIDTH1 = 950;
    static final int GAME_HEIGHT1 = 550;
    Score score1 = new Score(GAME_WIDTH1 ,GAME_HEIGHT1);
    Ball(int x , int y,int ballWidth,int ballHeight){
        super(x,y,ballWidth,ballHeight);

        random = new Random();
        int randomXDirection = random.nextInt(2);
        if(randomXDirection == 0)
            randomXDirection--;
        setXDirection(randomXDirection*initialSpeed);

        int randomYDirection = random.nextInt(2);
        if(randomYDirection == 0)
            randomYDirection--;
        setYDirection(randomYDirection*initialSpeed);


    }
    public void setXDirection(int randomXDirection) {
        xVelocity = randomXDirection;
    }
    public void setYDirection(int randomYDirection) {
        yVelocity = randomYDirection;
    }
    public void move() {
        x += xVelocity;
        y += yVelocity;
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/balls.png");
        try {
            imgBall = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics g) {
        importImg();
        g.drawImage(imgBall,x,y,width,height,null);
    }
}
