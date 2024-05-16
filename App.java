import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardwidth = 600;
        int boardHeight = 600;

        JFrame frame = new JFrame("Cobrinha");
        frame.setVisible(true);
        frame.setSize(boardwidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JogoDaCobrinha JogoDaCobrinha = new JogoDaCobrinha(boardwidth, boardHeight);
        frame.add(JogoDaCobrinha);
        frame.pack();
        JogoDaCobrinha.requestFocus();
    }
}