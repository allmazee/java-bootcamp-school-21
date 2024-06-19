# ImagesToChar

# Download the black-and-white BMP image (https://yadi.sk/i/nt-C_kZKWrlyNQ)

# To compile the project open the console in the project's root folder and execute the commands:
mkdir target
javac -d target src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/ImageConverter.java

# To start the project execute the command:
java -classpath target edu.school21.printer.app.Program --black=[SYMBOL] --white=[SYMBOL] --path=[PATH]