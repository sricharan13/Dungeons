## README

### ABOUT THE PROJECT
The **Dungeons** world is a network of tunnels and caves that are interconnected. Players can enter
the dungeon and explore the entire world by traveling from cave to cave through the tunnels that 
connect them. A dungeon can be wrapping or non wrapping. A wrapping dungeon can have paths that 
wrap from one end of the cave to the other end. The difference between a cav and tunnel is the 
number of entrances they have a tunnel is a cave with 2 entrances. The caves in the dungeon can have treasure where
treasure can be DIAMOND, SAPPHIRE, RUBY. Caves are also inhabited by Monsters called Otyughs. 
Otyughs are extremely smelly creatures that lead solitary lives. Otyugh only occupy caves 
and are never found in tunnels. Their caves can also contain treasure or other items. They can 
be detected by their smell. A less pungent smell can be detected when there is a single Otyugh 2 
positions from the player's current location. A more pungent smell either means that there is a 
single Otyugh 1 position from the player's current location or that there are multiple Otyughs 
within 2 positions from the player's current location. A player entering a cave with an Otyugh 
that has not been slayed will be killed and eaten. Player can slay Otyughs by shooting arrows. 
Arrows are crooked and can change direction when in a tunnel but they travel forward in caves. 
It takes 2 hits to kill an Otyugh. Player has a 50% chance of escaping if the Otyugh if they 
enter a cave of an injured Otyugh that has been hit by a single crooked arrow. Player can shoot 
arrow by specifying direction and distance. Distance should be exact if there is no Otyugh at 
the destination it is considered a miss. Player can pick up treasure from treasure caves. 
Additional arrows in the dungeon with the same frequency as treasure. Arrows and 
treasure can be, but are not always, found together. Furthermore, arrows can be found in both 
caves and tunnels. Every cave in the dungeon is connected to every other cave and the number of 
paths between caves is determined by the interconnectivity of the dungeon. A dungeon with 0 
interconnectivity has exactly 1 path between all the caves. As the interconnectivity increases 
the paths between the caves also increases. Every dungeon has a start and end cave. The game 
ends when player reaches the end cave. A Player can only start from the start cave. This project 
provides 3 different levels of implementations for the Dungeons world. The first implementation 
is provides the user to play a treasure hunt game - user moves through the dungeon collecting 
treasure and should eventually reach the end. A text based adventure game where user plays 
through a dungeon game containing monsters. A GUI driven monster dungeon game where user can 
play the game with visuals.

### LIST OF FEATURES
 - Create a custom dungeon with n x m caves (where n and m are number of rows and columns), 
   interconnectivity, percentage of caves that should contain treasure and arrows and whether the 
   dungeon is wrapping or non wrapping. Specify the number of Otyghs the dungeon has.
 - Randomly assign a start and end cave where the start and end caves have a minimum distance of 5.
 - Make a player move throughout the dungeon from one cave to another cave.
 - Make a player pickup treasure from caves containing treasure.
 - Make a player pickup arrows from caves containing arrows.
 - Display the possible moves a player can make from current location.
 - Display the treasure a cave contains.
 - Display the player's location.
 - Display the player's treasure.
 - Display Player's arrows.
 - Shoot arrows in the direction and distance specified by player to slay Otyughs.
 - Display the outcomes of shooting an arrow.
 - Display the smell perceived by player in player's location.
 - A controller that takes in a game model, inputs and output and plays' the game with given model.
 - A GUI view that displays the game to user using graphics.

### HOW TO RUN AND USE THE PROGRAM
- Extract zip
- **In terminal:** change directory to 'res' folder 
  - Eg: if zip was extracted to Downloads folder then the command would be: cd 
    Downloads/Dungeons/res
- This application can be run in 2 ways:
- **Type the command:** java -jar Dungeons.jar 
  - to run the game with GUI
- **Type the command:** java -jar Dungeons.jar *arg1* *arg2* *arg3* *arg4* *arg5* *arg6*
  - to run a text adventure game.
    - where, *arg1* is the number of rows (n) - an integer
    - *arg2* is the number of columns (m) - an integer
    - *arg3* is the interconnectivity - an integer
    - *arg4* is the percentage of treasure and arrows - an integer
    - *arg5* is a boolean ***true*** if dungeons should wrap, ***false*** otherwise. 
    - *arg6* is the number of Otyghs in the dungeon.
    - **example**: java -jar Dungeons.jar 4 6 6 30 true 5
- Inputs for GUI game:
  - use arrow keys to move player
  - use 'p' to pick items
  - use 's', 'arrow keys' to enter shoot mode and enter the distance in the pop-up window. 
- Inputs for text based game:
  - Player can perform 3 actions: Move, Pick and Shoot
  - Press the following keys to control player actions:
    - 'm' - Move, 'p' - Pick, 's' - Shoot
  - Press the following keys to control the player directions:
    - 'n' - NORTH, 'w' - WEST, 'e' - EAST, 's' - SOUTH
  - the inputs should be in lowercase.
    

### DESCRIPTION OF EXAMPLES
- _Example Runs_: The Example Runs pdf contains the following GUI pictures of the Monster Hunt Game.
  - Start game.
  - Exploring Dungeon.
  - Picking up treasure and arrows.
  - Different levels of smell.
  - Shooting.
  - Winning and losing games.
  - Dungeon Details.
  - Different game options to reset, restart and start a new game.
  - Launcher to launch a new game.

### DESIGN CHANGES
- Added a new controller interface for ViewController.
- Created an implementation of this view controller using Java's Swing.
- Original design included one JFrame instantiating multiple panels and MenuBar making the 
  design cluttered and complicated.
- The revised version abstracts these panels and menu bar into different classes. This design 
  choice provides a cleaner code and better abstraction.

### ASSUMPTIONS
- it is the views job to compute user input into controller understandable input.
- View converts mouse clicks into directions and passes these directions to the controller. 
- View also converts keyboard inputs and passes them to controller.
- View has a readonly copy of the model.
- Controller specifies which view to show to user - Game view or Game Launcher view.
- to launch the game should be constructed first. It is not possible to create a game using just 
  the view.

### LIMITATIONS
- dungeon cannot be resized.
- while the GUI window can be resized, the rendered dungeon size stays constant at 64 x 64
  pixels per cave.
- The game window always open with a 1000 x 700 dimensions. This may cause the game window to be 
  bigger than screen size if the screen size is small.
- Description displayed in GUI is scrollable but the contents and font are rigid. 

### CITATIONS
- https://www.baeldung.com/java-resize-image - used code snippet from this resource to resize 
  images in project.