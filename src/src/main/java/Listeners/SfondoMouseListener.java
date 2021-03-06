package Listeners;

import Game.Game;
import Util.Handler;
import Screens.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class SfondoMouseListener extends MouseAdapter {
    private final Handler handler;
    int mx, my;
    public SfondoMouseListener(Handler handler) {
        this.handler = handler;
    }
    public void mouseClicked(MouseEvent e) {
        mx = e.getX();
        my = e.getY();
        if (Sfondo.on) {
            if (mx >= 330* Game.getWIDTH()/784 && mx <= 430* Game.WIDTH/784) { //sfondo rosa
                if (my >= 250* Game.HEIGHT/561 && my <= 300* Game.HEIGHT/561) {
                    Game.setSfondo("space2.jpg");
                }
            }
            if (mx >= 333* Game.WIDTH/784 && mx <= 433* Game.WIDTH/784) { //sfondo blu
                if (my >= 350* Game.HEIGHT/561 && my <= 400* Game.HEIGHT/561) {
                    Game.setSfondo("sfondo3.jpg");
                }
            }
            if (mx >= 342* Game.WIDTH/784 && mx < 417* Game.WIDTH/784) { //sfondo bianco
                if (my >= 150* Game.HEIGHT/561 && my <= 200* Game.HEIGHT/561) {
                    Game.setSfondo("sfondo2.jpg");
                }
            }

        }
        if(Sfondo.on) {
            if (mx >= 640* Game.WIDTH/784 && mx < 705* Game.WIDTH/784) {
                if (my >= 500* Game.HEIGHT/561 && my <= 545* Game.HEIGHT/561) {
                    StartMenu.on=false;
                    Difficulty.on=false;
                    OptionPanel.on=true;
                    Sfondo.on=false;
                    GameOverScreen.on=false;
                    Game.setOn(false);
                    MovementSettings.on=false;
                    SoundSettings.on=false;
                    GamePause.on=false;

                }
            }
        }
    }
}

