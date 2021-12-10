## README

### ABOUT THE PROJECT
The **Dungeons** world is a network of tunnels and caves that are interconnected. Players can enter
the dungeon and explore the entire world by traveling from cave to cave through the tunnels that 
connect them. A dungeon can be wrapping or non wrapping. A wrapping dungeon can have paths that 
wrap from one end of the cave to the other end. The difference between a cav and tunnel is the 
number of entrances they have a tunnel is a cave with 2 entrances. The caves in the dungeon can have treasure where
treasure can be DIAMOND, SAPPHIRE, RUBY. Caves are also inhabited by Monsters called Otyughs. 
Otyughs are are extremely smelly creatures that lead solitary lives. Otyugh only occupy caves 
and are never found in tunnels. Their caves can also contain treasure or other items. They can 
be detected by their smell. A less pungent smell can be detected when there is a single Otyugh 2 
positions from the player's current location. A more pungent smell either means that there is a 
single Otyugh 1 position from the player's current location or that there are multiple Otyughs 
within 2 positions from the player's current location. A player entering a cave with an Otyugh 
that has not been slayed will be killed and eaten. Player can slay Otyughs by shooting arrows. 
Arrows are crooked and can change direction when in a tunnel but they travel forward in caves. 
It takes 2 hits to kill an Otyugh. Players has a 50% chance of escaping if the Otyugh if they 
enter a cave of an injured Otyugh that has been hit by a single crooked arrow. Player can shoot 
arrow by specifying direction and distance. Distance should be exact if there is no Otyugh at 
the destination it is considered a miss. Player can pick up treasure from treasure caves. 
Additional arrows in the dungeon with the same frequency as treasure. Arrows and 
treasure can be, but are not always, found together. Furthermore, arrows can be found in both 
caves and tunnels. Every cave in the dungeon is connected to every other cave and the number of 
paths between caves is determined by the interconnectivity of the dungeon. A dungeon with 0 
interconnectivity has exactly 1 path between all the caves. As the interconnectivity increases 
the paths between the caves also increases. Every dungeon has a start and end cave. The game 
ends when player reaches the end cave. A Player can only start from the start cave.

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

### HOW TO RUN AND USE THE PROGRAM

- **In terminal:** cd to the folder containing the .jar file
- **Type the command:** java -jar Dungeons.jar *arg1* *arg2* *arg3* *arg4* *arg5* *arg6*
  - where, *arg1* is the number of rows (n) - an integer
  - *arg2* is the number of columns (m) - an integer
  - *arg3* is the interconnectivity - an integer
  - *arg4* is the percentage of treasure and arrows - an integer
  - *arg5* is a boolean ***true*** if dungeons should wrap, ***false*** otherwise. 
  - *arg6* is the number of Otyghs in the dungeon.
  - **example**: java -jar Dungeons.jar 4 6 6 30 true 5
- The application prints 2 example runs and then allows the user to play an interactive game. 
- Player can perform 3 actions: Move, Pick and Shoot
- Press the following keys to control player actions:
  - 'm' - Move, 'p' - Pick, 's' - Shoot
- Press the following keys to control the player directions:
  - 'n' - NORTH, 'w' - WEST, 'e' - EAST, 's' - SOUTH
- the inputs should be in lowercase.
    

### DESCRIPTION OF EXAMPLES
- _Example Run 1_
  - Creating a non wrapping dungeon with the following specs: n: 4, m: 6, connectivity: 3, percent: 30, wrap: false, otyughs: 3, test: true
  - Traverse the dungeon.
  - Pickup treasure along the way and arrows.
  - Die by entering an Otyugh cave.

- _Example Run 2_
  - Creating a non wrapping dungeon with the following specs: n: 4, m: 6, connectivity: 3, percent: 30, wrap: false, otyughs: 3, test: true
  - Traverse the dungeon.
  - Pickup treasure along the way and arrows.
  - Kill Otyughs along the way.
  - Reach the end cave.

### DESIGN CHANGES
- Added interfaces like MonsterDungeonGame and TreasureDungeonGame to differentiate between the 
  type of Game. 
- MonsterHuntDungeonGame and TreasureHuntDungeonGame are their implementations. Abstracted 
  common functionalities of different types of games in AbstractDungeonGame.
- Changed Player to ExploringPlayer - player can explore dungeon and pick treasure.
- Extracted DungeonCave interface and created a new TreasureCave to better define functionality.
- Extended classes to add additional functionalities of MonsterCave and SlayingPlayer.
- Changed DungeonGenerator class to PathGenerator class which only specified the 
  copnnectivity between caves. These paths are used to construct the dungeon by adding the type 
  of caves - MonsterCave or TreasureCave depending on which Game is created.
- Created a new interface Monster and implemented it as Otyugh.
- Changed the previous version of DungeonGame to VisualTreasureDungeonGame because dungeon 
  should be displayed only for that version. This allows for reusability of all the previous 
  code from the older design.

### ASSUMPTIONS
- The distance between start and end cave is assumed to be manhattan distance.
- The treasure in a treasure cave will always have DIAMOND, RUBY and SAPPHIRE and will always 
  have a quantity of 2.
- Every cave will know the neighbours of that cave. If a cave does not have neighbours in any 
  direction the neighbour is assumed to be itself.
- Every cave is capable of having treasure initially, but as the dungeon is built some caves become 
  tunnels and treasure is not added to such caves.
- The additional arrows added will always be 1.
- Only 1 Otyugh can live in 1 cave.


### LIMITATIONS
- Since the dungeon has a specification on the number of paths between caves, Smaller dimensions 
  of the dungeon may sometimes lead to dungeon not being generated in which case an exception 
  will be thrown.
- Due to the distance constraint between start and end caves smaller dungeons are not 
  possible. A dungeon should have a minimum dimension of 4x4.
- Treasure and arrows in a cave is determined internally when creating the dungeon.
- The treasure and arrows added to the caves/ tunnels are constant.
- If the number of Otyughs specified in the dungeon are greater than the number of caves that 
  are formed after creating the dungeon, The program will throw an exception because only 1 
  otyugh can live in one cave.

### CITATIONS
- None.