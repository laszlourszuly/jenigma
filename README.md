## Setting up the build system

After having cloned the repository, change location to the project root and
issue below command to setup the correct gradle wrapper for the project.

    gradle wrapper --gradle-version 4.4

If you don't have `Gradle` installed yet, you can get it by issuing below
command on a fairly recent Debian based system:

    sudo apt install gradle

## Running the simulator

You can run the simulator from the project root, encrypting the example text,
like so:

    ./gradlew clean run

