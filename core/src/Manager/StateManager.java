package Manager;

import java.util.Stack;

import GameState.State;

/**
 * Created by Duy Anh Tang on 9/13/2017.
 */

/**
 * Managing back and forth between States
 */
public class StateManager {
    private Stack<State> stack;

    public StateManager() {
        stack = new Stack<State>();
    }

    public void push(State s) {
        stack.push(s);
    }

    public void pop() {
        stack.pop().dispose();
        stack.peek().resetListener();
    }

    public State peek() {
        return stack.peek();
    }
    public void dispose(){
        while (!stack.empty()){
            stack.pop().dispose();
        }
    }
}
