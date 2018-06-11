package krobis.model.state;

import krobis.model.ModelManager;

public class AnonState extends GameState {

  public AnonState(ModelManager mm) {
    super(mm); 
  }

  @Override
  protected String setName() {
    return "ANON";
  }

  @Override
  protected GameState[] setStateStructures() {
    throw new UnsupportedOperationException("Must override setStateStructures() "
        + "in your anonymous GameState instance!");
  }

}
