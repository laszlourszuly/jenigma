# Setting up the development environment
After having cloned the repository, change location to the project root and issue below command to setup the correct gradle wrapper for the project.

    gradle wrapper --gradle-version 4.4

If you don't have `Gradle` installed yet, you can get it by issuing below command on a fairly recent Debian based system:

    sudo apt install gradle

You can then run the simulator from the project root, encrypting the example text, like so:

    ./gradlew clean run

# Introduction to the Enigma machine
Enigma is an electro-mechanical encryption machine, originally developed by German engineer Arthur Scherbius as a way for companies to secure their business secrets and communications. Later versions were also used by the German military during WWII.

## Components
There are four (4) main components to consider in the Enigma machine:

### Keyboard
The keyboard is the main input channel where the operator enters the raw text to be encrypted, letter by letter.

### Plugboard
The plugboard is exactly what the name suggests; a board with plugs that connect the keyboard with the rest of the scrambling mechanism. It's main purpose is to allow for individual characters on the keyboard to be switched. If, say, the plug from the letter 'E' was switched with the plug from letter 'Z' then, each time the operator would type the letter 'E', the plugboard would send the letter 'Z' to the scrambling mechanism instead. This added an extra layer of obscurity to Enigma.

### Rotors
There are three different types of rotors, each with it's own specific characteristics and they are the core of the scrambling mechanism. Each rotor is a complex piece of electro-mechanical marvel and can, essentially be viewed as an Oreo cookie being threaded on a big horizontal nail. The "lids" connect the rotors to one-another and the "cream" contains the scrambling magic. The rightmost "lid" has 26 copper pins, one for each letter in the [A-Z] alphabet, and the leftmost "lid" has 26 corresponding copper pads. When threading several rotors ("Oreos") on the same axle ("nail") the pins will connect with pads. The cream is in reality a rubber pad with copper lines intricately connecting the copper pads with a different copper pin. Furthermore the "cream" can be rotated independently of the "lids", offering yet another layer of obscurity as the scrambling magic now is offset the corresponding pins and pads. This is a very important feature when it comes to the scrambling configuration of the Enigma machine and the concrete implementation of our simulator.

### Display
The display is actually just a board of lamps with corresponding letters next to them. It will show the scrambled output for the letter the operator just entered. If, for example the letter 'E' is entered on the keyboard, then the lamp next to letter 'Z' may light up, all depending on how the machine is configured.

# How the Enigma machine operates
To be added

# Implementing the simulator
To be added
