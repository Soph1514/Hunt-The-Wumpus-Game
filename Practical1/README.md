# Hunt The Wumpus

This is a Java console-based game where the player navigates through a network of caves to hunt the Wumpus, avoiding various hazards like bottomless pits and bats.

# Brief Overview

1. You are a hunter in a cave system seeking the man-eating Wumpus.
2. You have 5 arrows and must navigate through 20 interconnected rooms. You can pick up 3 extra arrows in the cave system.
3. Avoid hazards:
   - Bottomless Pit: Fatal if you fall in. You'll feel a draft if near.
   - Super-Bats: They pick you up and drop you in random rooms. You'll hear rustling near them.
   - The Wumpus: Smells terrible. It has a 75% chance to move after each arrow miss.
4. Commands:
   - `SHOOT room`: Shoot an arrow into a specified room. Example: SHOOT3
   - `MOVE room`: Move to a neighboring room. Example: MOVE5
   - `MAP`: Display a visual representation of the cave system.
   - `AMMO`: Check remaining arrows.
   - `QUIT`: Terminate the game.

# Getting Started

1. Ensure you have Java installed on your system.
2. Load the src directory folder.
2. Compile the Java files using `javac *.java`.
3. Run the game with `java HuntTheWumpusMain`.

Link to the version control repository: https://github.com/Soph1514/Practical1.git