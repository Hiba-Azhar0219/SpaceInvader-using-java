import javax.swing.JFrame;

public class GUI {
    public static void main(String[] args) throws Exception {
        int tileSize = 32;
        int boardwidth = tileSize*16;
        int boardHeight = tileSize*16;

        JFrame frame = new JFrame("Space Invaders");
        frame.setSize(boardwidth, boardHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SpaceInvader si = new SpaceInvader();
        frame.add(si);
        frame.pack();
        frame.setVisible(true);

    }
}
