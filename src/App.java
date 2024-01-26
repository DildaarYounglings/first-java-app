import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 600;
        int boardHeight = boardWidth;
        /*creating a new JFrame(String windowTitle)*/
        JFrame frame = new JFrame("Snake");
        /*seting frame to visible to show the frame on screen*/
        frame.setVisible(true);
        /*Sets the window size of the frame to the variables or numbers passed*/
        frame.setSize(boardWidth, boardHeight);
        /*sets the location of the window to the center because null is being passed in as a parameter*/
        frame.setLocationRelativeTo(null);
        /*the window is set to not be able to resize*/
        frame.setResizable(false);
        /* sets the window default close operation to the inbuilt JFrame.EXIT_ON_CLOSE */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        // adds the panel snakeGame to the frame or window //
        frame.add(snakeGame);
        frame.pack();
        // ----------------------------------------------- //
        // requesting focus so the devices keyboard and mouse  input can interact //
        snakeGame.requestFocus();
    }
}
