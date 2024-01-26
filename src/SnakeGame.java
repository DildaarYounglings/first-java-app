import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener,KeyListener{
    private class Tile{
        int x;
        int y;
        Tile(int x,int y){
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;
    Random random = new Random();
    // Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    
    // Food
    Tile food;
    
    // game logic 
    Timer gameLoop;
    boolean gameOver = false;
    int velocityX = 0;
    int velocityY = 0;
    // Constructor //
    SnakeGame(int boardWidth,int boardHeight){
        // stores the variable passed to the Snake Game and gives both of them to their respective interal functions //
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        // SetPreferredSize to the width and height of the board//
        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        // SetBackground changes the color to the color specified//
        setBackground(Color.black);
        // adding KeyListeniner and allowing our app to access device buttons //
        addKeyListener(this);
        setFocusable(true);
        // Initializing the following variables: snakeHead,snakeBody,food//
        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();
        food = new Tile(10,10);
        // method randomly sets the the snakes food at a random position//
        placeFood();
        // creates a timer to store in a gameloop variable and then starts the gameloop //
        gameLoop = new Timer(100,this);
        gameLoop.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        // <Grid> //
        for(int i = 0; i < boardWidth/tileSize; i++){
            g.drawLine(i*tileSize,0, i*tileSize,boardHeight);// drawing verticle lines
            g.drawLine(0, i*tileSize, boardWidth,i*tileSize);// drawing horizontal lines
        }
        // </Grid> //
        // <Food> //
        g.setColor(Color.red);
        g.fillRect(food.x * tileSize,food.y * tileSize,tileSize, tileSize);
        // </Food> //
        // <Snake> //
        g.setColor(Color.green);// sets the pen's drawing color
        g.fillRect(snakeHead.x * tileSize,snakeHead.y * tileSize,tileSize, tileSize); // draws a rectangle on screen and takes 4 int values
        for(int i = 0; i< snakeBody.size();i++){
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x * tileSize,snakePart.y * tileSize,tileSize, tileSize);
        }
        // Score // 
        if(gameOver){
            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.setColor(Color.red);
            g.drawString("GameOver:"+String.valueOf(snakeBody.size()), boardWidth/2, boardHeight/2);
        }
    }
    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardHeight/tileSize);
    }
    public boolean collision(Tile tile1,Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }
    public void move(){
        // eat food //
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x,food.y));
            placeFood();
        }
        // ----------- //
        // Snake Body //
        for (int i = snakeBody.size()-1; i>=0;i--){
            Tile snakePart = snakeBody.get(i);
            if(i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        // Snake Head //
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;
        // game over //
        for (int i = 0; i < snakeBody.size() ;i++){
            Tile snakePart = snakeBody.get(i);
            if(collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }
        // collision between edge of screen and snake head//
        if(snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight){
            gameOver = true;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
        }
        if(e.getKeyCode() == KeyEvent.VK_S && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_A && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_D && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
