# Instructions for Compiling and Running
# Navigate to the project root directory
    rm -rf target && mkdir target
    mkdir target/source
    cp src/it.bmp target/source
# Compile the Java files
    javac `find . -name "*.java"` -d ./target
# Run the application
    java -cp target edu.school21.printer.app.Program target/source/it.bmp . 0
