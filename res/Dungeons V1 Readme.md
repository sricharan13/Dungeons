## README

### ABOUT THE PROJECT
The **Dungeons** world is a network of tunnels and caves that are interconnected. Players can enter
the dungeon and explore the entire world by traveling from cave to cave through the tunnels that 
connect them. A dungeon can be wrapping or non wrapping. A wrapping dungeon can have paths that 
wrap from one end of the cave to the other end. The caves in the dungeon can have treasure where 
treasure can be DIAMOND, SAPPHIRE, RUBY. Player can pick up treasure from treasure caves. The 
difference between a cav and tunnel is the number of entrances they have a tunnel is a cave with 
2 entrances. Another difference between caves and tunnels is that tunnels do not have treasure. 
Every cave in the dungeon is connected to every other cave and the number of paths between 
caves is determined by the interconnectivity of the dungeon. A dungeon with 0 interconnectivity 
has exactly 1 path between all the caves. As the interconnectivity increases the paths between 
the caves also increases. Every dungeon has a start and end cave. The game ends when player 
reaches the end cave. A Player can only start from the start cave.

### LIST OF FEATURES
 - Create a custom dungeon with n x m caves (where n and m are number of rows and columns), 
   interconnectivity, percentage of caves that should contain treasure and whether the 
   dungeon is wrapping or non wrapping.
 - Randomly assign a start and end cave where the start and end caves have a minimum distance of 5.
 - Make a player move throughout the dungeon from one cave to another cave.
 - Make a player pickup treasure from caves containing treasure.
 - Display the possible moves a player can make from current location.
 - Display the treasure a cave contains.
 - Display the entire dungeon.
 - Display the player's location.
 - Display the player's treasure.
 - Display the start and end caves of the dungeon.

### HOW TO RUN AND USE THE PROGRAM

- **In terminal:** cd to the folder containing the .jar file
- **Type the command:** java -jar Dungeons.jar *arg1* *arg2* *arg3* *arg4* *arg5*
  - where, *arg1* is the number of rows (n) - an integer
  - *arg2* is the number of columns (m) - an integer
  - *arg3* is the interconnectivity - an integer
  - *arg4* is the percentage of treasure caves - an integer
  - *arg5* is a boolean ***true*** if dungeons should wrap, ***false*** otherwise. 
  - **example**: java -jar Dungeons.jar 4 6 6 30 true
- The application prints 3 example runs and then allows the user to play an interactive game. 
- Press the following keys to control the player actions:
  - w - to move player NORTH/ UP
  - a - to move player WEST/ LEFT
  - d - to move player EAST/ RIGHT
  - s - to move player SOUTH/ DOWN
  - p - to pick up treasure from cave
  - the inputs should be in lowercase.
    

### DESCRIPTION OF EXAMPLES
- _Example Run 1_
    - Create a wrapping dungeon with following specs: 4, 6, 8, 20, true
    - Traverse the dungeon.
    - Pickup treasure along the way.
    - Show a wrapping move by Player.
    - Reach end cave.
  
- _Example Run 2_
  - Creating a non wrapping dungeon with the following specs: 4, 6, 3, 30, false
  -Traverse the dungeon.
  - Pickup treasure along the way.
  - Reach end cave.

- _Example Run 3_
  - Creating a dungeon with following specs: 4, 4, 0, 40, false
  - visit every cave before reaching the end.
  - pickup treasure along the way.
  - display the player location, treasure, cave treasure and possible moves for each move.

### DESIGN CHANGES
- Removed the player interface since the user only needs player location and treasure and this 
  is provided by the model.
- Added the DungeonGenerator class which is a package private helper class that creates the dungeon.
- Created 2 methods in Treasure to create treasure for caves.
- Removed the RandomGenerator and created a boolean input to constructor to signal creation of 
  deterministic dungeon.
- Added a Cave interface to allow dumping the dungeon to screen.
- Removed Information class and added getter methods in Game interface to get the game state.
- Added Location class to better deal with locations (x, y).

### ASSUMPTIONS
- The distance between start and end cave is assumed to be manhattan distance.
- The treasure in a treasure cave will always have DIAMOND, RUBY and SAPPHIRE and will always 
  have a quantity of 2.
- Every cave will know the neighbours of that cave. If a cave does not have neighbours in any 
  direction the neighbour is assumed to be itself.
- Every cave is capable of having treasure initially, but as the dungeon is built some caves become 
  tunnels and treasure is not added to such caves. 
- The internal state of the dungeon is not private and the entire dungeon can be dumped to user 
  if necessary. 


### LIMITATIONS
- Since the dungeon has a specification on the number of paths between caves, Smaller dimensions 
  of the dungeon may sometimes lead to dungeon not being generated in which case an exception 
  will be thrown.
- Due to the distance constraint between start and end caves smaller dungeons are not 
  possible. A dungeon should have a minimum dimension of 4x4.
- Treasure in a cave is determined internally when creating the dungeon and is always constant.

### CITATIONS
- https://www.techiedelight.com/kruskals-algorithm-for-finding-minimum-spanning-tree/ - referred 
  to this implementation of kruskhals algorithm when generating the dungeon.