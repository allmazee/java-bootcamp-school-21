# ImagesToChar

# To compile the project open the console in the project's root folder and execute the commands:
mkdir target
javac -d target src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/ImageConverter.java

# Copy resources to target folder:
cp -r src/resources target/.

# Create jar file:
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

# Check archive contents:
jar tf target/images-to-chars-printer.jar

# To start the project execute the command:
java -jar target/images-to-chars-printer.jar --black=[SYMBOL] --white=[SYMBOL]