//thanks to perplexity ai for helping me with small logic errors that I couldn't figure out on my own
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

class PongClone extends JFrame implements KeyListener{
    public static char[][] pixels = new char[8][23];
    boolean gameRun = true;
    boolean wKey;
    boolean sKey;
    boolean upKey;
    boolean downKey;
    int[] pointsX = {1, 1, 21, 21};
    int[] pointsY = {-1, -4, -3, -6};
    public static int ballX = 11;
    public static float ballY = -4.0f;
    public static String ballXDir = "right";
    String ballYDir = "up";
    int ballTimer = 0;
    int leftPaddleScore = 0;
    int rightPaddleScore = 0;
    
    public PongClone() {
        this.setTitle("Pong Clone");
        this.setSize(300, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.setVisible(true);
        gameLoop();
    }

    //game loop
    private void gameLoop(){
        while (gameRun == true){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            fillBG();

            //movement of paddles
            if (wKey == true && pointsY[0] < 0){
                pointsY[0] += 1;
                pointsY[1] += 1;
            }
            if (sKey == true && pointsY[1] > -7){
                pointsY[0] -= 1;
                pointsY[1] -= 1;
            }

            if (upKey == true && pointsY[2] < 0){
                pointsY[2] += 1;
                pointsY[3] += 1;
            }
            if (downKey == true && pointsY[3] > -7){
                pointsY[2] -= 1;
                pointsY[3] -= 1;
            }
            
            //draw lines
            drawLine(pointsX[0], pointsY[0], pointsY[1], '/');
            drawLine(pointsX[2], pointsY[2], pointsY[3], '#');

            //handle ball
            if (leftPaddleScore < 5 && rightPaddleScore < 5){
               if (ballTimer < 15){
                ballTimer++;
                }
                else{
                    if (ballYDir == "up"){
                        ballY += 0.5;
                        if (ballY == 0){
                            ballYDir = "down";
                        }
                    }
                    else if (ballYDir == "down"){
                        ballY -= 0.5;
                        if (ballY == -7){
                            ballYDir = "up";
                        }
                    }

                    if (ballXDir == "left"){
                        ballX--;
                        collide(pointsX[0], pointsY[1], pointsY[0], 1, "right");
                    }
                    else if (ballXDir == "right"){
                        ballX++;
                        collide(pointsX[2], pointsY[3], pointsY[2], -1, "left");
                    }
                    if (ballX == 0 || ballX == 22){
                        if (ballX == 0){
                            rightPaddleScore++;
                            ballXDir = "right";
                        }
                        else if (ballX == 22){
                            leftPaddleScore++;
                            ballXDir = "left";
                        }
                        ballX = 11;
                        ballY = -4;
                        ballTimer = 0;
                    }
                }
            }
            
            //draw ball
            if (leftPaddleScore < 5 && rightPaddleScore < 5){
                setPixel(ballX, ballY, '@');
            }

            //show scores
            switch(leftPaddleScore){
                case 0: setPixel(5, -1, '0');
                break;
                case 1: setPixel(5, -1, '1');
                break;
                case 2: setPixel(5, -1, '2');
                break;
                case 3: setPixel(5, -1, '3');
                break;
                case 4: setPixel(5, -1, '4');
                break;
                case 5: setPixel(5, -1, '5');
                break;
            }

            switch(rightPaddleScore){
                case 0: setPixel(17, -1, '0');
                break;
                case 1: setPixel(17, -1, '1');
                break;
                case 2: setPixel(17, -1, '2');
                break;
                case 3: setPixel(17, -1, '3');
                break;
                case 4: setPixel(17, -1, '4');
                break;
                case 5: setPixel(17, -1, '5');
                break;
            }

            //render
            for(int a = 0; a < 8; a++){
                for (int b = 0; b < 23; b++){
                    System.out.print(pixels[a][b]);
                }
                System.out.println();
            }

        try {
		Thread.sleep(100);
            } catch (InterruptedException e) {
		e.printStackTrace();
	        }
        }
    }
    
    //functions
    public static void setPixel(int x, float y, char sym){
        pixels[Math.round(Math.abs(y))][x] = sym;
    }

    public static void drawLine(int x, int y1, int y2, char sym){
        for (int i = y1; i >= y2; i--){
            setPixel(x, i, sym);
        }
    }

    public static void fillBG(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 23; j++){
                setPixel(j, i, '.');
            }
        }
    }

    public static void collide(int x, int minY, int maxY, int pushFactor, String newDir){
        if (ballX == x && ballY >= minY && ballY <= maxY){
            ballX += pushFactor;
            ballXDir = newDir;
        }
    }

    //functions for key input
    @Override
    public void keyTyped(KeyEvent e){
        //not used
    }
    @Override
    public void keyPressed(KeyEvent e){
        //left paddle
        if (e.getKeyChar() == 'w'){
            wKey = true;
        }
        if (e.getKeyChar() == 's'){
            sKey = true;
        }

        //right paddle
        if (e.getKeyCode() == 38){
            upKey = true;
        }
        if (e.getKeyCode() == 40){
            downKey = true;
        }
    }
    @Override
    public void keyReleased(KeyEvent e){
        //left paddle
        if (e.getKeyChar() == 'w'){
            wKey = false;
        }
        if (e.getKeyChar() == 's'){
            sKey = false;
        }

        //right paddle
        if (e.getKeyCode() == 38){
            upKey = false;
        }
        if (e.getKeyCode() == 40){
            downKey = false;
        }
    }

    public static void main(String[] args){
        new PongClone();
    }
}