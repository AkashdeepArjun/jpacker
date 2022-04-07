package home;

import home.State;
public class Context {

    public static final boolean IS_WIN_OPENED=true;
    public static final boolean IS_WIN_CLOSED=true;
    private State state;
    public Context(){
        state=null;
    }

    public void setState(State state){
        this.state=state;
    }
    public State getState(){
        return state;
    }

    
}
