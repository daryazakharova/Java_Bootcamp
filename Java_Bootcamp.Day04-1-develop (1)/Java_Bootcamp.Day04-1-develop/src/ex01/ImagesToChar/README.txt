# Instructions for Compiling and Running
# Deleting and creating the target directory
    rm -rf target && mkdir target

# Copying resources to the target directory
    cp src/resources/it.bmp target

# Set the destination directory for class files
    javac `find . -name "*.java"` -d ./target

# Creating new jar archive (с) with file name (f) and manifest information (m)
    jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C ./target .

# Run the application
    java -jar ./target/images-to-chars-printer.jar . 0