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
    int velocityX;
    int velocityY;

    SnakeGame(int boardWidth,int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.black);
        // adding key Listeniner and allowing our app to access device buttons //
        addKeyListener(this);
        setFocusable(true);
        // ------------------------------------------------------------------ //
        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();
        food = new Tile(10,10);
        placeFood();
        velocityX = 0;
        velocityY = 0;
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
        // </Snake> // 
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
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
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
