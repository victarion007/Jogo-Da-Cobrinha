import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class JogoDaCobrinha extends JPanel implements ActionListener, KeyListener{
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 30;

    //cobrrinha
    Tile cabeçaDaCobrinha;
    ArrayList<Tile> corpoDaCobrinha;

    //comida
    Tile Comida;
    Random random;

    //lógica de jogo
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    JogoDaCobrinha(int boardwidth, int boardHeight) {
        this.boardWidth = boardwidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        cabeçaDaCobrinha = new Tile(9, 8);
        corpoDaCobrinha = new ArrayList<Tile>();

        Comida = new Tile(8, 18);
        random = new Random();
        placeComida();

        velocityX = 0;
        velocityY= 1;

        gameLoop = new Timer(100, this);
        gameLoop.start();
        
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //grade
        for(int i = 0; i < boardWidth/tileSize; i++) {
            //(x1, y1 x2, y2)
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        }

        //comida
        g.setColor(Color.red);
        g.fillRect(Comida.x * tileSize, Comida.y * tileSize, tileSize, tileSize);

        //cabeça Da cobrinha
        g.setColor(Color.green);
        g.fillRect(cabeçaDaCobrinha.x * tileSize, cabeçaDaCobrinha.y * tileSize, tileSize, tileSize);

        //corpo da cobrinha
        for (int i = 0; i < corpoDaCobrinha.size(); i++) {
            Tile parteDaCobrinha = corpoDaCobrinha.get(i);
            g.fillRect(parteDaCobrinha.x * tileSize, parteDaCobrinha.y * tileSize, tileSize, tileSize);
        }

        //score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("MORREU NO JOGO DA COBRINHA KKKKKKKKKKKKKKK: " + String.valueOf(corpoDaCobrinha.size()), tileSize - 16, tileSize);
        }
        else {
            g.drawString("PONTOS: " + String.valueOf(corpoDaCobrinha.size()), tileSize - 16,tileSize);
        }
    }

    public void placeComida() {
        Comida.x = random.nextInt(boardWidth/tileSize); //600/25 = 24
        Comida.y = random.nextInt(boardHeight/tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        // comer comida
        if (collision(cabeçaDaCobrinha, Comida)) {
            corpoDaCobrinha.add(new Tile(Comida.x, Comida.y));
            placeComida();
        }

        //Corpo Da Cobrinha
        for (int i = corpoDaCobrinha.size()-1; i >= 0; i--) {
            Tile parteDaCobrinha = corpoDaCobrinha.get(i);
            if (i == 0) {
                parteDaCobrinha.x = cabeçaDaCobrinha.x;
                parteDaCobrinha.y = cabeçaDaCobrinha.y;
            }
            else {
                Tile prevParteDaCobrinha = corpoDaCobrinha.get(i-1);
                parteDaCobrinha.x = prevParteDaCobrinha.x;
                parteDaCobrinha.y = prevParteDaCobrinha.y;
            }
        }

        //Cabeça Da Cobrinha
        cabeçaDaCobrinha.x += velocityX;
        cabeçaDaCobrinha.y += velocityY;

        //condições de game over
        for (int i = 0; i < corpoDaCobrinha.size(); i++) {
            Tile parteDaCobrinha = corpoDaCobrinha.get(i);
            //colidir com a cabeça da cobrinha
            if (collision(cabeçaDaCobrinha, parteDaCobrinha)) {
                gameOver = true;
            }
        }

        if (cabeçaDaCobrinha.x*tileSize < 0 || cabeçaDaCobrinha.x*tileSize > boardWidth ||
        cabeçaDaCobrinha.y*tileSize < 0 || cabeçaDaCobrinha.y*tileSize > boardHeight) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    

    @Override
    public void keyReleased(KeyEvent e) {
       if (e.getKeyCode() == KeyEvent.VK_W && velocityY != 1) {
        velocityX = 0;
        velocityY = -1;
       }
       else if(e.getKeyCode() == KeyEvent.VK_S && velocityY != -1) {
        velocityX = 0;
        velocityY = 1;
       }
       else if (e.getKeyCode() == KeyEvent.VK_A && velocityX != 1) {
        velocityX = -1;
        velocityY = 0;
       }
       else if (e.getKeyCode() == KeyEvent.VK_D && velocityX != -1) {
        velocityX = 1;
        velocityY = 0;
       }
    }
    //não precisa

    @Override
    public void keyPressed(KeyEvent e) {}

   

    @Override
    public void keyTyped(KeyEvent e) {}
}
