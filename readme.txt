Simple currency calculator that requires data complied with http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml located inside the same directory as executable program. The file must be named "eurofxref-daily.xml".

To start the program double-click the curcalc.jar or compile the code by typing "javac Parser.java Calculator.java" in command line while in the correct direcotry and run it with "java Parser".

How to use the program:
1. Choose the currency to convert into from the dropdown menu in the middle section.
2. Type the amount of EUR to convert from on the left, using dot as a decimal point.
3. Press the "Calculate" button on the right.
4. The calculated amount should show up in the second text box.

The program should inform the user in case of the following errors:
1. "eurofxref-daily.xml" file is not located in the correct directory or could not be read.
2. Value inserted by user is not a valid number.
3. Chosen currency does not have a valid rate value.