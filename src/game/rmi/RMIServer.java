package game.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import game.rmi.IGameEngine;
import game.rmi.GameEngine;

public class RMIServer {

    public static void main(String[] args) {
        try {
            // Create and install a security manager



            // Start the RMI registry on the local host and on port 1099
            Registry registry = LocateRegistry.createRegistry(1234);
            System.out.println("RMI registry started on port 1099");

            // Create an instance of the remote object
            IGameEngine gameEngine = new GameEngine();

            // Bind the remote object's stub in the registry under the name "GameEngine"
            registry.rebind("GameEngine", gameEngine);
            System.out.println("GameEngine bound in registry");

            // Keep the server running
            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
