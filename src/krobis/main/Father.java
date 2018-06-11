package krobis.main;

import krobis.controller.GameController;

public class Father {

  GameController gc;
  
  public static void main(String[] args) {
    new Father(args).begin();
  }

  private Father(String[] args) { 
    //...
  }
  
  private void begin() { 
    gc = GameController.getInstance(this);
    gc.init();
  }

}
