# Instructions for Compiling and Running
# Deleting and creating the target directory
    rm -rf target && mkdir target

# Copying resources to the target directory
    cp src/resources/it.bmp target

# Compiles all Java source files using the specified JAR files as dependencies, 
# and outputs the compiled .class files to the target directory
    javac -cp lib/JColor-5.5.1.jar:lib/jcommander-1.82.jar `find . -name "*.java"` -d ./target

# Extract files and replace to directory target
    cd target && jar fx ../lib/JColor-5.5.1.jar && jar fx ../lib/jcommander-1.82.jar && cd ..

# Creating new jar archive (с) with file name (f) and manifest information (m)
    jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C ./target .

# Run the application
    java -jar ./target/images-to-chars-printer.jar --white=RED --black=GREEN





