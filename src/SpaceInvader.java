import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;




 public class SpaceInvader extends JPanel {
    int tileSize = 32;
    int boardwidth = tileSize*16;
    int boardHeight = tileSize*16;

    int shipHeight = tileSize;
    int shipWidth = tileSize*2;
    int shipX = boardwidth/2-(tileSize*2);
    int shipY = boardHeight-(tileSize*2);
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

    int alienX  = boardwidth/2;
    int alienY = boardHeight/2;
    int alienWidth = tileSize*2;
    int alienHeight = tileSize;
    Image alienImage;

    class Alien{
        int x = alienX;
        int y = alienY;
        int width = alienWidth;
        int height = alienHeight;
        Image img;

        Alien(Image img){
            this.img = img;
        }

    }
    Alien alien;

    Ship ship;
    ArrayList<Alien> aliens;

   
   public SpaceInvader(){
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(boardwidth, boardHeight));
        shipImage = new ImageIcon(getClass().getResource("./ship.png")).getImage();
        alienImage = new ImageIcon(getClass().getResource("./alien-white.png")).getImage();
        ship = new Ship(shipImage);
        
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(shipImage, ship.x, ship.y, shipWidth, shipHeight, null);

        
          g.drawImage(alienImage, alien.x, alien.y, alien.width, alien.height, null);

        
    }
}