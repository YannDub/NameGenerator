package com.yanndubois.ng;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Markov {
	
	private Map<String, Integer> transitions;
	private int order;
	
	public Markov(int order, String[] database) {
		this.transitions = new HashMap<String, Integer>();
		this.order = order;
		
		this.computeDatabaseTransition(database);
	}
	
	/**
	 * Add transition to the transition map
	 * @param transition the transition to add
	 */
	private void putTransition(String transition) {
		if(!this.transitions.containsKey(transition))
			this.transitions.put(transition, 1);
		else
			this.transitions.put(transition, this.transitions.get(transition) + 1);
	}
	
	/**
	 * Add composition of two string in the transition map
	 * @param prev the prefix
	 * @param next the suffix
	 */
	private void putTransition(String prev, String next) {
		this.putTransition(prev + next);
		this.putTransition("#" + prev);
	}
	
	/**
	 * Coompute all transition
	 * @param name the word to compute the transition
	 */
	public void computeTransition(String name) {
		int size = name.length();
		String prev = "";
		
		for(int i = 0; i < this.order; i++) {
			prev += "^";
		}
		
		for(int i = 0; i < size; i++) {
			String next = name.charAt(i) + "";
			this.putTransition(prev, next);
			prev = prev.substring(prev.length() - this.order + 1) + next;
		}
		
		this.putTransition(prev, "$");
	}
	
	/**
	 * Compute all transitions of all words in names
	 * @param names an array of words
	 */
	public void computeDatabaseTransition(String[] names) {
		for(int i = 0; i < names.length; i++) {
			this.computeTransition(names[i]);
		}
	}
	
	/**
	 * Generate a name
	 * @return a name
	 */
	public String randNameMarkov() {
		String prev = "";
		
		for(int i = 0; i < this.order; i++) {
			prev += "^";
		}
		
		return this.markov(prev);
	}
	
	/**
	 * Markov function to generate realistic name
	 * @param prev the previous state
	 * @return a name
	 */
	private String markov(String prev) {
		String result = "";
		
		while(!prev.equals("$")) {
			String key = "#" + prev;
			if(!this.transitions.containsKey(key)) break;
			
			int total = this.transitions.get(key);
			Random rand = new Random();
			int randValue = rand.nextInt(total);
			
			int k = 0;
			
			for(String each : this.transitions.keySet()) {
				if(this.transitions.containsKey(key) && each.subSequence(0, prev.length()).equals(prev)) {
					k += this.transitions.get(each);
					if(k >= randValue) {
						String next = each.substring(prev.length());
						result += next;
						prev = prev.substring(prev.length() - this.order + 1) + next;
						break;
					}
				}
			}
		}
		
		return result.substring(0, result.length() - 1);
	}
}
