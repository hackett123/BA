package krobis.view;

import krobis.controller.GameController;

public class GuiHandler implements Runnable {

    private GameController gameController;

    public GuiHandler(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void run() {

    }

}
