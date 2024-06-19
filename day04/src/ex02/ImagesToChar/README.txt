# ImagesToChar

# In the root folder of the project create a lib folder:
mkdir lib

# Download the archives and place them in the lib folder:
https://repo1.maven.org/maven2/com/beust/jcommander/1.82/jcommander-1.82.jar
https://repo1.maven.org/maven2/com/diogonunes/JColor/5.5.1/JColor-5.5.1.jar

# To unpack lib jar files execute the commands:
cd target
jar xf ../lib/JColor-5.5.1.jar com
jar xf ../lib/jcommander-1.82.jar com
cd ..

# To compile the project open the console in the project's root folder and execute the commands:
mkdir target
[FOR UNIX]: javac -cp ".:./lib/JColor-5.5.1.jar:./lib/jcommander-1.82.jar" -d ./target/ src/java/edu/school21/printer/*/*.java
[FOR WINDOWS]: javac -cp ".;./lib/JColor-5.5.1.jar;./lib/jcommander-1.82.jar" -d ./target/ src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/ImageConverter.java

# Copy resources to target folder:
cp -r src/resources target/.

# Create jar file:
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

# Check archive contents:
jar tf target/images-to-chars-printer.jar

# To start the project execute the command:
java -jar target/images-to-chars-printer.jar --black=[SYMBOL] --white=[SYMBOL]