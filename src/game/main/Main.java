package game.main;

import game.component.PanelGame;
import game.rmi.IGameEngine;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private IGameEngine engine;
    private PanelGame panelGame;

    public Main() {
        initRMI();  // Initialize RMI connection
        initUI();   // Initialize the user interface
    }

    private void initRMI() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1234);
            engine = (IGameEngine) registry.lookup("GameEngine");
            // Set up a timer to periodically update the game state from the server
            new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateGame();
                }
            }).start();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    private void initUI() {
        setTitle("Java 2D Game with RMI");
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panelGame = new PanelGame();  // Assuming PanelGame can handle dynamic updates
        add(panelGame, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                panelGame.start();  // Start the game when the window is opened
            }
        });

        setVisible(true);
    }

    private void updateGame() {
        try {
            // Fetch the latest game state from the server
            panelGame.setObjects(engine.getGameObjects());  // Update local game objects
            panelGame.repaint();  // Redraw the panel
        } catch (Exception e) {
            System.err.println("Failed to update game objects: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();  // Create and show the main game window
    }
}
