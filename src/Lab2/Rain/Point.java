package Lab2.Rain;

import java.util.ArrayList;
import java.util.Arrays;

public class Point {
	private ArrayList<Point> neighbors;
	private int currentState;
	private int nextState;
	private int numStates = 6;

	public ArrayList<Integer> aliveRules;
	public ArrayList<Integer> deadRules;
	
	public Point() {
		currentState = 0;
		nextState = 0;
		neighbors = new ArrayList<Point>();
		aliveRules = new ArrayList<>(Arrays.asList(3));
		deadRules = new ArrayList<>(Arrays.asList(2,3));

	}


	public void clicked() {
		currentState=(++currentState)%numStates;	
	}
	
	public int getState() {
		return currentState;
	}

	public void setState(int s) {
		currentState = s;
	}

	public void calculateNewState() {
		if (currentState > 0)
			nextState = currentState - 1;

		if (neighbors.size() > 0)
			if (currentState == 0 && neighbors.get(0).getState() > 0)
				nextState = 6;
	}

	public void changeState() {
		currentState = nextState;
	}
	
	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}
	

	public int aliveNeighbors(){
		int count = 0;
		for (Point neighbor : neighbors) {
			if (neighbor.getState() == 1)
				count++;
		}

		return count;
	}

	public void drop(){
		int random = (int)(Math.random()*100);
		if (random <= 5){
			this.setState(6);
		}
	}
}
