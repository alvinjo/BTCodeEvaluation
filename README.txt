Steps to run the the program:
1. Open cmd in the directory containing the java file
2. To compile, type "javac MonitoringSystemDisplay.java" 
3. Move back into the "src" directory 
4. To run, type "java BTCodeEvaluation.MonitoringSystemDisplay [file path]" where "file path" is the full path of the text file.


#Notes
I tried to avoid arrays to store the data as the datastream could be very large in a real system and storing the data may take up too much space. Working without arrays proved to be very complex and I decided to use arrays in the end.
Printing the data as it reads was my initial approach and I think this would be the ideal solution.
Some access modifiers in the program have been changed from "private" to "protected", this is to allow the testing class to work.


Assumptions made:
1. Data comes in as a stream and not as a list of messages. In other words, there are no newline characters in the data.
2. Notifications involving two nodes must output twice. The first output will always state the sending node is alive and the second output will involve the second node and the notification type (e.g. "luke Found r2d2" outputs luke ALIVE and r2d2 ALIVE)

Assumptions made but not acted upon:
1. A node that sends a notification outside its 50ms time frame is considerd UNKNOWN. If the difference between a nodes notification generation timestamp exceeds 50ms then it is out of sync and is declared UNKNOWN. This assumption was not acted upon because much of the example data would be out of sync because of this rule and I didn't believe this would be correct.


Difficulties:
1. Some messages in the example data seem to be recieved by the monitoring system before they were generated.
The receieved timestamp was lower than the generated timestamp. Would this be considered malformed data?
In this case I have included a method that detects this type of data and doesn't display the output.
Maybe instead of this, have the program throw an exception and end the program.


Improvements:
1. Write to text file.
2. Invalid data check
	timestamp timestamp node status
	timestamp timestamp node status node
		check if both formats are adhered to
3. Variables in the inSync method can be stored. Avoids having to execute the same statement again, saving time.
4. The inSycn method can be improved by having each character of the two timestamp variables compared (as integers) individually. The comparison would be in a loop that checks a digit from both variables and ends the loop once one of the variables is larger/smaller than the other. The method could then return true or false depending on the result.







