# Setting up the example environment
After having cloned the repository, change location to the project root and issue below command to setup the correct gradle wrapper for the project.

    gradle wrapper --gradle-version 4.4

If you don't have `Gradle` installed yet, you can get it by issuing below command on a fairly recent Debian based system:

    sudo apt install gradle

You can then run the simulator from the project root, encrypting the example text, like so:

    ./gradlew clean run

# Introduction to the Enigma machine
Enigma is an electro-mechanical encryption machine, originally developed by German engineer Arthur Scherbius as a way for companies to secure their business secrets and communications. Later versions were also used by the German military during WWII.

## Components
There are four main components to consider in an updated military edition Enigma machine:

### Keyboard
The keyboard is the main source of input where the operator enters the raw text to be encrypted, letter by letter, similar to the keyboard on any computer of today.

### Plugboard
The plugboard is a board with plugs allowing character-pairs to be switched without affecting the actual layout of the keyboard. If the plugboard is configured to switch the letters 'E' and 'Z' then, each time the operator types the letter 'E', the plugboard will instead send the letter 'Z' to the scrambling mechanism. And similarly when the operator types the letter 'Z', the letter 'E' will be sent instead. This adds an extra layer of obscurity to the Enigma algorithm.

### Rotors
The rotors are the core of the scrambling mechanism and can, essentially, be viewed as a set of Oreo cookies being threaded on a big horizontal nail. The "cookie lids" connect the rotors to one-another and the "cream" contains the scrambling magic. Each rotor can rotate independently on the nail, occasionally forcing a neighboring rotor to rotate along with it. The rotors will be discussed in more detail further down.

### Display
The display is as primitive as a board of lamps with corresponding letters next to them. It will show the scrambled output for the letter the operator just entered. If, for example, the letter 'S' is entered on the keyboard, then the lamp next to letter 'J' may light up, all depending on how the machine is configured. The operator then takes a note of 'J' and enters the next letter on the keyboard.

# How the Enigma machine operates
The Enigma machine was a magnificent battery driven beast. Each letter on the keyboard had its own dedicated copper line through the entire machine. The current was flowing from the battery, through the keyboard, continued to the plugboard where it was potentially redirected to another letter's line, entered the rotors at the rightmost rotor in which its path got redirected to another line (see details below), then entered the next rotor, and the next and so on, until it reached the leftmost rotor. The leftmost rotor then "reflected" the current and forced the current to flow through the rotors back again on a different path, scrambling the signal even more. When the current reached the rightmost rotor again it was routed back to the plugboard where it could potentially be redirected again, and so to the display where it lit a light to show the final result of the encryption process before finally returning to the battery.

Wikipedia has a good graphical resource describing the flow: https://en.wikipedia.org/wiki/Enigma_machine#/media/File:Enigma_wiring_kleur.svg

## A closer look at the rotors
Let's stick to the Oreo parable for the rotors. The Enigma machine can have up to five rotors rotating on a horizontal axle. The rightmost "lid" of each "Oreo" has 26 spring loaded copper pins, one for each letter in the [A-Z] alphabet, and the leftmost "lid" has 26 corresponding copper pads. The pads on a rotor will connect with the pins on its left neighbor and allow current to flow through from one rotor to the next. The "cream" is in reality a rubber ring with copper lines running inside it in an intricate pattern, unique to each type of rotor. Each of these internal lines connects a single copper pad to a single copper pin. Furthermore, this rubber ring, the "cream", can be manually rotated, independently of the "lids", before taken into operation, offering yet another layer of obscurity as the internal alphabet of the rubber ring is now offset the corresponding pins-and-pads-alphabet of the rotor.

Besides the static routing between input lines and output lines in the rubber ring, the Enigma machine also rotates its rotors. Each time a key is pressed on the keyboard, but before the signal is sent out on a scramble tour, the rightmost rotor is rotated one step offsetting the rotor lines relative to the lines comming from the plugboard. This way each letter is scrambled to a different output everytime it occurs in the clear text and it doesn't seem to be a pattern of recurrence. At a certain character, also unique to each type of rotor, the rotor will hook into its left neighbor and rotate it as well and then unhook from it, further adding to the possible scramble combinations. Similarly the neighbor would at some point hook into its left neighbor, rotate it and then unhook from it, and so on. The pattern very much resembles a binary counter.

When the operator assembles the Enigma configuration, (s)he has 10 + 4 different rotors to chose five candidates from. It's important that the sender and receiver has the same configuration for the decryption to work. The specific details of each rotor is described here (you will need this information in order to successfully build your Enigma simulator): http://www.codesandciphers.org.uk/enigma/rotorspec.htm

### The reflector
The leftmost rotor in an Enigma machine is always a "reflector". A reflector doesn't have any copper pads on its left "lid", but it rather redirects the current from one copper pin to another one, allowing the signal to continue the scrambling path through the other rotors again, but this time from left to right. Another feature of the reflector is that its right neighbor can't hook into it, hence, it doesn't rotate. The reflector is an essential part when it comes to decrypting a previously encrypted message.

### The extra rotor
The German navy required a higher level of complexity in their Enigma machines than the rest of the military and added therefore an extra "numb" rotor to the right of the reflector - this way reaching a total of five rotors (including the reflector). This was a regular rotor with the exception that it, just as the reflector, couldn't rotate. It added a significant amount of scrambling combinations, though.

# Enigma configuration
Since its essential for the sender and receiver to have the same configuration on their Enigma machines the actual details has to be agreed on in advance (this fact was highly contributing to the fall of Enigma). The contract describes which reflector and which rotors to use, how to offset each rotor's rubber ring (the "cream") relative to it's pads and pins (the "lids"), and which letters on the plugboard to switch. The notation looks like this:

    * B BETA III IV I AXLE (YF) (ZH)

The above means:

* This is a configuration line
* Use reflector "B"
* Use extra rotor "BETA"
* Use regular rotors "III", "IV" and "I".
* Offset "BETA" so that 'A' on its "lids" matches 'A' on its "cream"
* Offset "III" so that 'A' on its "lids" matches 'X' on its "cream"
* Offset "IV" so that 'A' on its "lids" matches 'L' on its "cream"
* Offset "I" so that 'A' on its "lids" matches 'E' on its "cream"
* Switch characters 'Y' and 'F' on the plugboard
* Switch characters 'Z' and 'H' on the plugboard

Note how the contract implicitly also describes the internal order of the rotors.

# Example 1
Below step-by-step example will describe a characters lifecycle and how it changes as it passes through the Enigma statemachine. You can use it as a debug scheme when verifying your rotor implementation in the lab.

Assuming above configuration of your Enigma machine:

1. Pressing the letter **'Y'** on the keyboard will now cause the **"I"-rotor** to rotate one step, from 'E' to 'F'. According to "I"-rotor documentation (see above link) there is no "hook" to the next rotor at character 'E', hence the other rotors are not rotated at this time.

1. After the rotation, the current will flow from the battery through the 'Y' copper line and hit the **plugboard** which will convert our input character **'Y'** to **'F'** according to our initial configuration.

1. The current now enters the **"I"-rotor"** as **'F'** and leaves it as **'I'**.

   1. The current enters the right "lid" of the rotor at the 'F' copper line from the plugboard, which has the index 5 (because 'A'=0). The "cream" is, however, offset by 'E'=4 steps relative to the lids, and, on top of that, the entire rotor has rotated one step relative to the global reference lines comming from the plugboard. The current therefore enters the "cream" at position 5 + 4 + 1 = 10 as the letter 'K'.

   1. According to documentation, the "cream" of an "I"-rotor will convert 'K' to 'N', which has index 13, but since the "cream" is offset by 4 steps ('E') relative to its "lids", the current will hit the left "lid" at pad 13 - 4 = 9. This rotor has, however, rotated one step which makes pad 9 being offset by 1 relative to the global plugboard lines, hence, the "I"-rotor will ultimately deliver a signal at line 13 - 4 - 1 = 8, which is the line for the letter 'I'.

1. Next, the current will reach the **"IV"-rotor** as **'I'** and leave it as **'V'**.

   1. The same reasoning applies here; the current enters the right "lid" of the rotor on the global 'I'=8 line. This rotor has it's "cream" offset by 'L'=11 relative to its "lids", which means that the current will hit the "cream" as 'T' (8 + 11 = 19). This rotor has not rotated yet so we don't have any global plugboard offset to compensate for.

   1. Documentation says that the "cream" of an "IV"-rotor will convert 'T' to 'G' which has index 6. The "cream" offset will result in the rotor delivering a signal on the 'V' pad on its left "lid", because 6 - 11 = -5 and -5 mod 26 = 21, the index of 'V'.

1. Next, the current will reach the **"III"-rotor** as **'V'** and leave it as **'J'**.

   1. Similarly, this rotor has its "cream" offset by 'X'=23 steps, which will result in the current entering the "cream" as 'S', because 21 + 23 = 44 and 44 mod 26 = 18, which is the index of 'S'. This rotor hasn't rotated either, hence there is no global  plugboard index to honor here either.

   1. According to documentation, the "III"-rotor "cream" will convert an 'S' to 'G' which has index 6, and compensating for the "cream" offset the signal will leave at pad 'J', because 6 - 23 = -17 and -17 mod 26 = 9, the index of 'J'.

1. Now, the current enters the extra **"BETA"-rotor** as **'J'** and leaves it as **'W'**.

   1. This rotor has no offset between its "cream" and its "lids", hence, there is nothing to adjust for. This is also en extra rotor which, by design, can't rotate regardless what its right neighbor does. So no global offset to calculate either.

1. The current now reaches the **"B"-reflector** as **'W'**, which is reflected as **'H'** according to documentation.

   1. The reflector doesn't have any output ports on its left "lid", but rather redirects an input from a pin on its right "lid" to another pin on the right "lid".

1. Now, the current enters the **"BETA"-rotor** again, but this time as an **'H'** from the left "lid" and leaves as an **'X'** through the right "lid".

   1. The very same rules applies when the current flows "the other way" in a rotor. We just have to apply the inverse of the transformation matrix, that is, we have to look for the input character, 'H', in the scramble alphabet and find the corresponding character, 'X' in this case, in the reference alphabet.

1. Next, the **"III"-rotor** will apply it's inverse logic so that the **'X'** becomes a **'Z'**.

   1. Remember the "III"-rotor has it's "cream" offset by 'X'=23 steps, so the current will enter the left "lid" on its way back on line 23 (the 'X' delivered from the previous rotor) and, due to offset, enter the "cream" as 'U'=20 because 23 + 23 = 46 and 46 mod 26 = 20, the index of 'U'.

   1. The reverse conversion of 'U' in a "III"-rotor is documented to be 'W'=22, which, when compensated for the "cream" offset, will leave the right "lid" at the 'Z'=25 pin, because 22 - 23 = -1 and -1 mod 26 is 25, the index of 'Z' in the zero based alphabet.

1. Then the current enters the **"IV"-rotor** as **'Z'** and leaves it as **'J'**.

   1. The "IV"-rotor has a "cream" offset of 'L'=11, hence the current enters the cream as 'K', while 25 + 11 = 36 and 36 mod 26 = 10, which is 'K'.

   1. The "IV"-rotor "cream" reverse-converts 'K' to 'U', which, when compensated for offset delivers 'J' to the next rotor, because 20 - 11 = 9, which is 'J'.

1. The current finally enters the **"I"-rotor"** as **'J'** and leaves as **'H'**.

   1. The "I"-rotor has a "cream" offset of 'E'=4 and has, on top of that, rotated one step, so the 'J' from the previous rotor enters the "cream" through the left "lid" as 'O' at position 9 + 4 + 1 = 14.

   1. The reverse conversion of 'O' in a "I"-rotor "cream" is 'M' which, when compensated for the "cream" offset enters the right "lid" at pin 12 - 4 = 8 which ultimately is translated to the global plugboard line of 'H'=7, due to the rotation offset which we need to take into account for the rightmost rotor.

1. Finally the **plugboard** converts the **'H'** to **'Z'** which is also delivered as the final result of the encryption algorithm: 'Y' is encrypted as 'Z'.

# Example 2
When you're done implementing your Enigma simulator you can configure it as:

    * B BETA III IV I AXLE (HQ) (EX) (IP) (TR) (BY)

and feed it with this input:

```
FROM his shoulder Hiawatha
Took the camera of rosewood
Made of sliding folding rosewood
Neatly put it all together
In its case it lay compactly
Folded into nearly nothing
But he opened out the hinges
Pushed and pulled the joints
and hinges
Till it looked all squares
and oblongs
Like a complicated figure
In the Second Book of Euclid
```

If done right you should get the below output.

```
QVPQS OKOIL PUBKJ ZPISF XDWBH
CNSCX NUOAA TZXSR CFYDG UFLPN
XGXIX TYJUJ RCAUG EUNCF MKUFW
JFGKC IIRGX ODJGV CGPQO HALWE
BUHTZ MOXII VXUEF PRPRK CGVPF
PYKIK ITLBU RVGTS FUSMB NKFRI
IMPDO FJVTT UGRZM UVCYL FDZPG
IBXRE WXUEB ZQJOY MHIPG RREGO
HETUX DTWLC MMWAV NVJVH OUFAN
TQACK KTOZZ RDABQ NNVPO IEFQA
FSVVI CVUDU EREYN PFFMN BJVGQ
```

# Implementing the simulator
The lab is about implementing an Enigma simulator in a programming language of your choice. To your help you have the Internet and the discussion above. The introduction section could give you an idea on how to model your implementation. You won't be able to make it without the link describing the internal wireing of the rotors (http://www.codesandciphers.org.uk/enigma/rotorspec.htm).

 Your implementation should be case insensitive regarding the input, and it should ignore anything that's not in the range `[A-Za-z]`. To honor the old ways, you should add a space after each fifth encrypted character and a line break after each fifth group of encrypted characters.

`Example 1` could be of good use when debugging your rotor implementation. The master test is to configure and feed your finished implementation according to `Example 2` and verify you get the expected output. You should also be able to feed your encrypted result as input and get the clear text as output.

May the force be with you!
