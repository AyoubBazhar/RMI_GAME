package game.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IGameEngine extends Remote {
    void addBullet(int x, int y, float angle, int size, float speed) throws RemoteException;
    void addRocket(int x, int y, int angle) throws RemoteException;
    void updatePlayerPosition(int x, int y, float angle) throws RemoteException;
    void resetGame() throws RemoteException;
    int getScore() throws RemoteException;
    List<Object> getGameObjects() throws RemoteException; // Returns all game objects to update client views
}
