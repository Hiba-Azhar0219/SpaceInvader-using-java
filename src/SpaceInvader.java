import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
    int score = 0 ;
    boolean gameOver = false;

    int shipHeight = tileSize;
    int shipWidth = tileSize*2;
    int shipX = boardwidth/2-(tileSize);
    int shipY = boardHeight-(tileSize*2);
    int velocityX = tileSize;
    Image spaceBg;

    Image shipImage;

    class Ship{
        int x = shipX;
        int y = shipY;
        int height = shipHeight;
        int width = shipWidth;
        Image img;
        
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
    int totalAliens;
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
    int bulletX;
    int bulletY;
    int bulletVelocityY = -10;
    int bulletWidth = tileSize/8;
    int bulletHeight = tileSize/2;



    class Bullet{
        int x = bulletX;
        int y = bulletY;
        int height = bulletHeight;
        int width = bulletWidth;
        boolean used = false ; //bullets used or not


        Bullet(int x, int y, int width, int height){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        
    }
    Ship ship;
    ArrayList <Alien> alienArray;
    ArrayList<Image> alienImgArray;
    ArrayList<Bullet> bulletArray;

    //timers
    Timer gameLoop;

   
   public SpaceInvader(){
    spaceBg = new ImageIcon(getClass().getResource("./spaceBg.gif")).getImage();

        setPreferredSize(new Dimension(boardwidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);
        shipImage = new ImageIcon(getClass().getResource("./ship.png")).getImage();
        alienWhiteImage = new ImageIcon(getClass().getResource("./alien-white.png")).getImage();
        alienYellowImage = new ImageIcon(getClass().getResource("./alien-yellow.png")).getImage();
        alienMagentaImage = new ImageIcon(getClass().getResource("./alien-magenta.png")).getImage();
        alienCyanImage = new ImageIcon(getClass().getResource("./alien-cyan.png")).getImage();
        alienImgArray = new ArrayList<>();
        alienImgArray.add(alienCyanImage);
        alienImgArray.add(alienYellowImage);
        alienImgArray.add(alienWhiteImage);
        alienImgArray.add(alienMagentaImage);
        alienArray = new ArrayList<>();
        bulletArray = new ArrayList<>();


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

        //drawing backgroung
        g.drawImage(spaceBg, 0, 0, boardwidth, boardHeight, null);

        //drawing ship
        g.drawImage(shipImage, ship.x, ship.y, shipWidth, shipHeight, null);   

        //drawing aliens
        for (int i = 0; i < alienArray.size(); i++) {
            Alien alien = alienArray.get(i);
            if(alien.alive){
                g.drawImage(alien.img, alien.x, alien.y, alien.width, alien.height, null);
            }
            
        }
        //drwaing bullets
        g.setColor(Color.WHITE);
        for(int i = 0 ; i<bulletArray.size(); i++){
            Bullet bullet = bulletArray.get(i);
            if(!bullet.used){
                g.drawRect(bullet.x, bullet.y, bullet.width, bullet.height);

            }
        }

        if(gameOver){
            g.setFont(new Font("Times New Roman", Font.BOLD, 30));

            g.setColor(Color.RED);
            g.drawString("GAME OVER : "+ score, 5, 30);

        }
        else
        {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString("Score: "+  score, 5, 30);}
    }       //draw ends

     

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }
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
        totalAliens = alienArray.size();
    }       //create aliens ends

   
    public boolean collisionDetection(Bullet a, Alien b){
        return a.x < b.x + b.width && 
        a.x + a.width > b.x &&
        a.y < b.y + b.height &&
        a.y + a.height > b.y;     
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

                if(alien.y>=ship.y){
                    gameOver = true;
                }

            }
        }

        //moving bullets
        for(int i = 0 ; i<bulletArray.size(); i++){
            Bullet bullet = bulletArray.get(i);
            bullet.y +=bulletVelocityY;

            // bullets collision with aliens
            for(int j = 0; j< alienArray.size(); j++){
                Alien alien = alienArray.get(j);
                if(!bullet.used && alien.alive && collisionDetection(bullet, alien)){
                    bullet.used = true;
                    alien.alive = false;
                    totalAliens--;
                    score +=100;

                }
            }
        } 
         //clearing bullets
         while(bulletArray.size()>0 && (bulletArray.get(0).used || bulletArray.get(0).y<0)){
            bulletArray.remove(0);
        } 

        //next level
        if(totalAliens ==0)      {
            alienColumns = Math.min(alienColumns + 1, 6);
            alienRows = Math.min(alienRows + 1, 10);
            alienArray.clear();
            bulletArray.clear();
            alienVelocityX = 1;
            score+=200;
            createAliens();
        }
       
    }       //move ends


    @Override
    public void keyReleased(KeyEvent e) {
        if(gameOver){
            ship.x = shipX;
            alienArray.clear();
            bulletArray.clear();
            score  =0;
            alienVelocityX = 1;
            alienColumns = 3;
            alienRows = 2;
            gameOver = false;
            createAliens();
            gameLoop.start();
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && ship.x+ship.width <boardwidth){

            ship.x +=velocityX;        
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && ship.x>0){
            ship.x -= velocityX;
        }
        else if(e.getKeyCode() ==KeyEvent.VK_SPACE){
            Bullet bullet  = new Bullet(ship.x+ship.width*15/32,ship.y, bulletWidth, bulletHeight);
            bulletArray.add(bullet); 
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

   


}   //SpaceInvaders class ends