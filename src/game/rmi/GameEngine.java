package game.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import game.obj.Bullet;
import game.obj.Player;
import game.obj.Rocket;

public class GameEngine extends UnicastRemoteObject implements IGameEngine {
    private int score;
    private List<Bullet> bullets;
    private List<Rocket> rockets;
    private Player player;

    public GameEngine() throws RemoteException {
        super();
        bullets = new ArrayList<>();
        rockets = new ArrayList<>();
        player = new Player();
        player.changeLocation(150, 150); // Initial player location
    }

    @Override
    public void addBullet(int x, int y, float angle, int size, float speed) throws RemoteException {
        Bullet newBullet = new Bullet(x, y, angle, size, speed);
        synchronized (bullets) {
            bullets.add(newBullet);
        }
    }

    @Override
    public void addRocket(int x, int y, int angle) throws RemoteException {
        Rocket newRocket = new Rocket();
        newRocket.changeLocation(x, y);
        newRocket.changeAngle(angle);
        synchronized (rockets) {
            rockets.add(newRocket);
        }
    }

    @Override
    public void updatePlayerPosition(int x, int y, float angle) throws RemoteException {
        synchronized (player) {
            player.changeLocation(x, y);
            player.changeAngle(angle);
        }
    }

    @Override
    public void resetGame() throws RemoteException {
        synchronized (this) {
            score = 0;
            bullets.clear();
            rockets.clear();
            player.changeLocation(150, 150);
            player.reset();
        }
    }

    @Override
    public int getScore() throws RemoteException {
        return score;
    }

    @Override
    public List<Object> getGameObjects() throws RemoteException {
        List<Object> objects = new ArrayList<>();
        synchronized (bullets) {
            objects.addAll(bullets);
        }
        synchronized (rockets) {
            objects.addAll(rockets);
        }
        synchronized (player) {
            objects.add(player);
        }
        return objects;
    }
}
