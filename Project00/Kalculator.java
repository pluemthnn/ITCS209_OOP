//Name: Thanwarat Wongthongtham
//ID: 6288145
//Section: 3

import java.util.ArrayList;
import java.lang.Math; 

public class Kalculator {
	
	/**
	 * Constructor is the fist method to be call at instantiation of a Kalculator object.
	 * If you need to initialize your object, do so here. 
	 */
	
	private ArrayList<Double> input;
	

	private double sum;
	private double std;
	private double max;
	private double min;
	private double avg;
	
	Kalculator()
	{
		this.input = new ArrayList<Double>();
	}
	
	/**
	 * Add number to the list of numbers. 
	 * @param number
	 */
	public void addNumber(double number)
	{
		input.add(number);
	}
	
	/**
	 * Remove the least recently added number from the list. If the list is empty, do nothing.
	 */
	public void deleteFirstNumber()
	{
		if(input.isEmpty()) {
			input.clear();
		}
		else {
			input.remove(0);
		}
	}
	
	/**
	 * Remove the most recently added number from the list. If the list is empty, do nothing.
	 */
	public void deleteLastNumber()
	{
		if(input.isEmpty()) {
			input.clear();
		}
		else {
			input.remove(input.size()-1);
		}
	}
	
	/**
	 * Calculate the summation of all the numbers in the list, then returns the sum. 
	 * If the list is empty, return 0.
	 * @return
	 */
	public double getSum()
	{
		sum = 0;
		if(input.isEmpty()) {
			return 0;
		}
		else {
			for(int i = 0; i < input.size();i++) {
				sum += input.get(i);
			}
		}
		return sum;
	}
	
	/**
	 * Calculate and return the average of all the numbers in the list.
	 * If the list is empty, return 0.
	 * @return
	 */
	public double getAvg()
	{
		if(input.size() == 0) {
			return 0;
		}
		avg = sum/input.size();
		return avg;

	}
	
	/**
	 * Calculate and return the sample standard deviation of all the numbers in the list.
	 * If the list has fewer than 2 numbers, return 0.
	 * @return
	 */
	public double getStd()
	{
		if(input.isEmpty() || input.size() < 2) {
			return 0;
		}
		std = 0;
		double a = 0;
		int n = input.size()-1;
		double x = sum/input.size();
		for(int i = 0; i < input.size(); i++) {
			a += (input.get(i)-x) * (input.get(i)-x);
		}	
		std = a/n;
		std = Math.sqrt(std);
		
		return std;
	}
	
	/**
	 * Find and return the maximum of all the numbers in the list.
	 * If the list is empty, return 0.
	 * @return
	 */
	public double getMax()
	{
		
		if(input.isEmpty()) {
			return 0;
		}
		else {
			max = input.get(0);
			for(int i = 0; i < input.size(); i++) {
				if(max < input.get(i)) {
					max = input.get(i);
				}
			}
			return max;
		}
		
	}
	
	/**
	 * Find and return the minimum of all the numbers in the list.
	 * If the list is empty, return 0.
	 */
	public double getMin()
	{
		
		if(input.isEmpty()) {
			return 0;
		}
		else {
			min = input.get(0);
			for(int i = 0; i < input.size(); i++) {
				if(min > input.get(i)) {
					min = input.get(i);
				}
			}
			return min;
		}
		
	}
	
	/**
	 * Find and return the maximum k numbers of all the numbers in the list as an array of k double number.
	 * The order of the returned k numbers does not matter. (We only care if you can get the max k elements)
	 * If the list has fewer than k numbers, return null.
	 */
	public double[] getMaxK(int k)
	{
		ArrayList<Double> copy = (ArrayList<Double>) input.clone();	
		double sort;
		if(input.size()<k) {
			return null;
		}
		for(int i = 0; i < copy.size(); i++) {
			for(int j = 0;j < copy.size();j++) {
				if(copy.get(i) > copy.get(j)) {
					sort = copy.get(j);
					copy.set(j,copy.get(i));
					copy.set(i, sort);
				}
			}
		}
		double[] getmax = new double[k];

		for(int i=0;i<k;i++) {
			getmax[i] = copy.get(i);
		}
		
		return getmax;
	}
	
	/**
	 * Find and return the minimum k numbers of all the numbers in the list as an array of k double number.
	 * The order of the returned k numbers does not matter. (We only care if you can get the min k elements)
	 * If the list has fewer than k numbers, return null.
	 */
	public double[] getMinK(int k)
	{
		ArrayList<Double> copy = (ArrayList<Double>) input.clone();	
		double sort;
		
		if(input.size()<k) {
			return null;
		}
		for(int i = 0; i < copy.size(); i++) {
			for(int j = 0;j < copy.size();j++) {
				if(copy.get(i) < copy.get(j)) {
					sort = copy.get(j);
					copy.set(j,copy.get(i));
					copy.set(i, sort);
				}
			}
		}
		double[] getmin = new double[k];

		for(int i=0;i<k;i++) {
			getmin[i] = copy.get(i);
		}
		
		return getmin;
		
	}
	
	/**
	 * Print (via System.out.println()) the numbers in the list in format of:
	 * DATA[<N>]:[<n1>, <n2>, <n3>, ...]
	 * Where N is the size of the list. <ni> is the ith number in the list.
	 * E.g., "DATA[4]:[1.0, 2.0, 3.0, 4.0]"
	 */
	public void printData()
	{
		System.out.print("DATA[" + input.size() + "]:[");
		for (int j = 0; j < input.size() ; j++) {
			
			System.out.print(input.get(j));
			if(j != input.size()-1) {
				System.out.print(", ");
			}
		}
		System.out.println("]");
	}
}
