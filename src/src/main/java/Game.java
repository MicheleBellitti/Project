//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    public static int ENEMY_NUMBER = 15;
    private int J = 1;
    private StartMenu mn;
    static boolean on=false;
    private static String title = "Easy Game";
    private Thread thread;
    private boolean running = false;
    private boolean gameOver = false;
    Random r1 = new Random();
    private Handler handler = new Handler();
    private Hud hud=new Hud();
    private Spawner spawner;
    OptionPanel op;
    public Game() {
        new Window(WIDTH, HEIGHT, title, this);
        mn= new StartMenu();
        op = new OptionPanel();
        this.start();
        this.spawner=new Spawner(handler,hud);
        this.addKeyListener(new MyKeyListener(this.handler));
        this.addMouseListener(new MyMouseListener(this.handler));
        this.handler.addGameObject(new Player(350.0F, (float) (HEIGHT - 75), 0.0F, 0.0F, ID.Player));
        for (int i = 0; i < 3; i++) {
            this.handler.addGameObject(new Enemy((float)r1.nextInt(WIDTH), 0.0F, 2*J, 2.0F, ID.Enemy));
            this.J *= -1;
        }

        this.stop();
    }
    public static int Ecollision(GameObject p,Handler handler){
        int hits=0;
        Rectangle player = new Rectangle((int)p.getX(), (int)p.getY(), 32, 32);
        for(int i=0;i<handler.objList.size();i++) {
            GameObject tmp = handler.objList.get(i);
            if (tmp.getId() != ID.Enemy) {
                continue;
            }
            Rectangle e=new Rectangle((int)tmp.getX(),(int)tmp.getY(),16,16);
            if(player.intersects(e)) hits++;
        }
        return hits;

    }

    public  void freezeGame(){
        for(int i=0;i<handler.objList.size();++i){
            GameObject obj=handler.objList.get(i);
            obj.setVelY(0f);
            obj.setVelX(0f);

        }
    }
    /*public boolean check(Handler handler) {
        for(int i = 0; i < handler.objList.size(); ++i) {
            GameObject tmp = (GameObject)handler.objList.get(i);
            if (tmp.getId() == ID.Enemy) {
                Rectangle enemy = new Rectangle((int)tmp.getX(), (int)tmp.getY(), 16, 16);
                enemy.toString();
                for(int j = 0; j < handler.objList.size(); ++j) {
                    GameObject tmpPlayer = (GameObject)handler.objList.get(j);
                    if (tmpPlayer.getId() == ID.Player || tmpPlayer.getId() == ID.Player2) {
                        Rectangle player = new Rectangle((int)tmpPlayer.getX(), (int)tmpPlayer.getY(), 32, 32);
                        if (player.intersects(enemy)) {
                            handler.removeGameObject(tmpPlayer);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }*/
    public GameObject getPlayer(Handler handler) {
        GameObject target=null;
        for (int i = 0; i < handler.objList.size(); i++) {
            GameObject tmp = handler.objList.get(i);
            if (tmp.getId() == ID.Player) {
                target = tmp;
                break;
            }
        }
        return target;
    }
    public GameObject getBullet(Handler handler) {
        GameObject target=null;
        for (int i = 0; i < handler.objList.size(); i++) {
            GameObject tmp = handler.objList.get(i);
            if (tmp.getId() == ID.Bullet) {
                target = tmp;
                break;
            }
        }

        return target;
    }
    public GameObject getEnemy(Handler handler) {
        GameObject target=null;
        for (int i = 0; i < handler.objList.size(); i++) {
            GameObject tmp = handler.objList.get(i);
            if (tmp.getId() == ID.Enemy) {
                target = tmp;
                break;
            }
        }
        return target;
    }
    private synchronized void start() {
        if (!this.running) {
            this.thread = new Thread(this);
            this.thread.start();
            this.running = true;
        }
    }

    private synchronized void stop() {
        if (this.running) {
            try {
                this.thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.running = false;
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0D;
        double ns = 1.0E9D / amountOfTicks;
        double delta = 0.0D;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while(this.running) {
            long now = System.nanoTime();
            delta += (double)(now - lastTime) / ns;

            for(lastTime = now; delta >= 1.0D; --delta) {
                this.tick();
                ++updates;
            }

            this.render();
            ++frames;
            if (System.currentTimeMillis() - timer > 1000L) {
                timer += 1000L;
                frames = 0;
                updates = 0;
            }
        }

    }
    public static int clamp(int val,int min,int max){
        if(val>=max)
            return val=max;
        else if(val<=min)
            return val=min;
        else return val;

    }
    private void tick() {
        if(!StartMenu.on && Game.on) {
            this.handler.tick();
            this.hud.tick();
            hud.HEALTH -= 2 * Ecollision(getPlayer(this.handler), this.handler); // Collision code
            this.spawner.tick();
        }
        // if(hud.getLevel()>=10) freezeGame();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
        } else {
            Graphics g = bs.getDrawGraphics();
            g.setColor(Color.BLACK.darker());
            g.fillRect(0, 0, WIDTH, HEIGHT);
            if(!StartMenu.on  && Game.on) {
                this.handler.render(g);
                this.hud.render(g);
            }
            if(StartMenu.on && !Game.on) this.mn.render(g);
            if(OptionPanel.on && !StartMenu.on) this.op.render(g);
            bs.show();
            g.dispose();
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
