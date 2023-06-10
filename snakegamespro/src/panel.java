import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

public class panel extends  JPanel implements ActionListener {
    // setting the size of the panel
    static final int width = 1200;
    static  final int height = 600;
    int unit = 50; //it is the of the pixel

    boolean flag = false;
    Random random ;
    int score = 0;
    int fx, fy;
    char dir = 'R';
    int length = 3;
    Timer timer;
    int xsnakes[] = new int[288];
    int ysnakes[] = new int[288 ];
    // creating panel constructor
     panel(){
        this.setPreferredSize(new Dimension(width , height));
        this.setBackground(Color.BLACK);
        random = new Random();

        this.setFocusable(true);
        this.addKeyListener(new key());

        gamestart();
     }
     public void gamestart(){
         spawnfood();
         flag = true;
         timer = new Timer(160, this);
         timer.start();



     }
     public void  spawnfood() {
         fx = random.nextInt(width / unit) * 50;
         fy = random.nextInt(height / unit) * 50;
     }


    public void paintComponent(Graphics graphic){
         super.paintComponent(graphic);
         draw(graphic);
    }
    public void draw(Graphics graphic){
         // drawing the food particle
        //when the game running
        if(flag){
            graphic.setColor(Color.red);
            graphic.fillOval(fx, fy, unit, unit);
            // to draw the snake
            for(int i=0; i<length; i++){
                graphic.setColor(Color.green);
                graphic.fillRect(xsnakes[i], ysnakes[i], unit,unit);
            }
            //for drawing a score elements
            graphic.setColor(Color.white);
            graphic.setFont(new Font("Comic Sons MS" , Font.BOLD,40));
            FontMetrics fme = getFontMetrics(graphic.getFont()) ;
            graphic.drawString("score : " + score , (width - fme.stringWidth("score" + score))/2,graphic.getFont().getSize() );
            //when the game is not running

        }
        else{
            // to display the final score
            graphic.setColor(Color.WHITE);
            graphic.setFont(new Font("Comic Sons MS" , Font.BOLD,40));
            FontMetrics fme = getFontMetrics(graphic.getFont()) ;
            graphic.drawString("score : " + score , (width - fme.stringWidth("score" + score))/2,graphic.getFont().getSize() );

            // to display the gameover text
            graphic.setColor(Color.RED);
            graphic.setFont(new Font("Comic Sons MS" , Font.BOLD,80));
            fme = getFontMetrics(graphic.getFont()) ;
            graphic.drawString("GAME OVER!", (width - fme.stringWidth("GAME OVER!"))/2, height/2 );

            // to display the prompt
            graphic.setColor(Color.green);
            graphic.setFont(new Font("Comic Sons MS" , Font.BOLD,40));
            fme = getFontMetrics(graphic.getFont()) ;
            graphic.drawString("Press R to replay", (width - fme.stringWidth("Press R to replay"))/2,height/2 + 150 );


        }
    }
    public void move(){
        for(int i = length; i>0; i--){
            // this will update the body of snake except head of snake
            xsnakes[i] = xsnakes[i-1];
            ysnakes[i] = ysnakes[i-1];
        }
        switch (dir){
            case 'R': xsnakes[0] = xsnakes[0] + unit;
            break;
            case 'L': xsnakes[0] = xsnakes[0] - unit;
            break;
            case 'U': ysnakes[0] = ysnakes[0] - unit;
            break;
            case 'D': ysnakes[0] = ysnakes[0] + unit;
            break;


        }
    }
    public void foodeaten(){
         if((xsnakes[0] == fx) && (ysnakes[0] == fy)){
             length++;
             score++;
             spawnfood();
         }
    }

    public void checkhit(){
         // condition for checking while the snakes hit boundary
//         if(ysnakes[0]<0 || ysnakes[0]>600 || xsnakes[0]<0 || xsnakes[0]>1200){
//             flag = false;
        if(ysnakes[0]<0){
            flag = false;
         }
        if(ysnakes[0]>600){
            flag = false;
        }
        if(xsnakes[0]<0){
            flag = false;
        }
        if(xsnakes[0]>1200){
            flag = false;
        }
        // condition for checking while the snakes hit itself
         for(int i = length; i>0; i--){
             if((xsnakes[0] == xsnakes[i]) && (ysnakes[0] == ysnakes[i])){
                 flag = false;

             }
         }
         if(flag == false){
             timer.stop();
         }
    }


    public class key extends KeyAdapter{

         public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(dir != 'R'){
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir != 'L'){
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(dir != 'D'){
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir != 'U'){
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_R:
                    score = 0;
                    length = 3;
                    dir = 'R';
                    Arrays.fill(xsnakes,0);
                    Arrays.fill(ysnakes,0);
                    gamestart();

            }

        }
    }

    public void actionPerformed(ActionEvent evt){
        if(flag){
            move();
            foodeaten();
            checkhit();
        }
        repaint();
    }
}
