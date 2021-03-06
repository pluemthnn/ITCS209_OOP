# Introduction:

Implementing an automated routine to do things for you is a fundamental application of programming. 
The ability to speak programming languages fluently allows you to communicate with computers<br>
in order to develop a wide range of useful, intelligent applications. In this project,<br>
you will implement a simple calculator, Kalculator, which supports the following calculation 

## functions:
> - Kalculator(): The constructor to initialize a Kalculator object.
> - addNumber(double number): Add a number to the list of data.
> - deleteFirstNumber(): Remove the least recently added number.
> - deleteLastNumber(): Remove the most recently added number.
> - getSum(): Return the sum of all the numbers in the list.
> - getAvg(): Return the average of all the numbers in the list.
> - getStd(): Return the sample standard deviation (std) of all the numbers in the list. The formula for sample std is as follows:
> - getMax(): Return the maximum of all the numbers in the list.
> - getMin(): Return the minimum of all the numbers in the list.
> - getMaxK(int k): Return an array of maximum k numbers of all the numbers in the list.
> - getMinK(int k): Return an array of minimum k numbers of all the numbers in the list.
> - printData(): Print all the numbers in the list. 

After initialization, a Kalculator maintains a list of numbers (you can use either an array or an ArrayList to implement this), each of which can be inserted via addNumber(double number) only. That is, the user programs must not be able to access any Kalculator’s class attributes directly. They must do so via the above public methods. You can assume that a Kalculator can hold not more than 10,000 numbers.
