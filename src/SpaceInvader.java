import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;




 public class SpaceInvader extends JPanel implements ActionListener, KeyListener {
    int tileSize = 32;
    int boardwidth = tileSize*16;
    int boardHeight = tileSize*16;
    int alienRows = 2;
    int alienColumns = 3;

    int shipHeight = tileSize;
    int shipWidth = tileSize*2;
    int shipX = boardwidth/2-(tileSize);
    int shipY = boardHeight-(tileSize*2);
    int velocityX = tileSize;

    Image shipImage;

    class Ship{
        int x = shipX;
        int y = shipY;
        int height = shipHeight;
        int width = shipWidth;
        Image img;
        boolean used = false ; //bullets used or not
        
        Ship(Image img)
        {
            this.img = img;
        }
    }       //ship class ends

    int alienX  = tileSize;
    int alienY = tileSize;
    int alienWidth = tileSize*2;
    int alienHeight = tileSize;
    Image alienCyanImage;
    Image alienWhiteImage;
    Image alienMagentaImage;
    Image alienYellowImage;
    int totalAliens = 0;
    int alienVelocityX = 1;


    class Alien{
        int x = alienX;
        int y = alienY;
        int width = alienWidth;
        int height = alienHeight;
        Image img;
        
       
        boolean alive = true; //aliens alive or not


        Alien(int x, int y, int width, int height, Image img){
            this.x =x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }
    }

    Ship ship;
    ArrayList <Alien> alienArray;
    ArrayList<Image> alienImgArray;

    //timers
    Timer gameLoop;

   
   public SpaceInvader(){
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(boardwidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);
        shipImage = new ImageIcon(getClass().getResource("./ship.png")).getImage();
        alienWhiteImage = new ImageIcon(getClass().getResource("./alien-white.png")).getImage();
        alienYellowImage = new ImageIcon(getClass().getResource("./alien-yellow.png")).getImage();
        alienMagentaImage = new ImageIcon(getClass().getResource("./alien-magenta.png")).getImage();
        alienCyanImage = new ImageIcon(getClass().getResource("./alien-cyan.png")).getImage();
        alienImgArray = new ArrayList<Image>();
        alienImgArray.add(alienCyanImage);
        alienImgArray.add(alienYellowImage);
        alienImgArray.add(alienWhiteImage);
        alienImgArray.add(alienYellowImage);
        alienArray = new ArrayList<Alien>();


        ship = new Ship(shipImage);
        createAliens();

        
        //Timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        //drawing ship
        g.drawImage(shipImage, ship.x, ship.y, shipWidth, shipHeight, null);   

        //drawing aliens
        for (int i = 0; i < alienArray.size(); i++) {
            Alien alien = alienArray.get(i);
            if(alien.alive){
                g.drawImage(alien.img, alien.x, alien.y, alien.width, alien.height, null);
            }
            
        }
    }       //draw ends

     

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }       //actionPerformed ends

    public void createAliens(){
        int randomImg;
        Random random = new Random();
        for(int r = 0; r<alienRows; r++){
            
            for(int c = 0; c<alienColumns; c++){
                randomImg = random.nextInt(alienImgArray.size());
                  Alien alien = new Alien(c*alienWidth, r*alienHeight, alienWidth, alienHeight, alienImgArray.get(randomImg));
                  alienArray.add(alien);
            }

        }
        totalAliens =alienArray.size();
    }

    public void move(){
        for(int i = 0; i<alienArray.size(); i++){
            Alien alien = alienArray.get(i);
            if(alien.alive){
                alien.x += alienVelocityX;
                if(alien.x +alien.width>= boardwidth || alien.x<=0){
                    alienVelocityX *=-1;
                    alien.x += alienVelocityX*2;


                    //aliens moving down
                    for(int j = 0; j<alienArray.size(); j++){
                        alienArray.get(j).y += alienHeight;
                    }
                }

            }
        }


    }       //move ends

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && ship.x+ship.width <boardwidth){

            ship.x +=velocityX;        
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && ship.x>0){
            ship.x -= velocityX;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

   


}   //SpaceInvaders class ends