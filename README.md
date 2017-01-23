This is a incomplete version of a puzzle game I am working on for release on Android smartphones.  The goal is to navigate a ball or balls through a grid to their respective goals by bouncing them off a series of walls.  Bouncing works according to the physical rule that the angle of incidence equals the angle of reflection.  Balls start out motionless, and begin moving when the player touches them.  Balls travel vertically, horizontally, or diagonally.  Walls are always at angles which are multiples of 22.5 degrees, which, as it turns out, will keep the ball traveling vertically, horizontally, or diagonally after being reflected off it.  Walls can rotate according to some increment.  The increment is the increase in the multiple of 22.5 degrees.  So if a wall at an angle of 45 degrees rotates with an increment of 4, it will be at 45 + 4 * 22.5 = 135 degrees.  The increment is often indicated by the number of dots at the end of a wall.  There are four types of walls, each with a different trigger for rotating.  

Basic wall (brown): rotates when touched by the player.  On Windows, a left click rotates counterclockwise, while a right click rotates clockwise.  On Android, tilting the screen to the left rotates counterclockwise, while tilting to the right rotates clockwise.  
Fixed wall (black): never rotates.  
Reaction wall (grey): rotates when hit by a ball.  
Spinning wall (blue): rotates at fixed time intervals by an increment of 1.  

Any wall can be weak (yellow), which means that it will be destroyed after being hit by a ball a certain number of times.  A pair of reaction walls can be linked, meaning that when one rotates, the other one does as well.  A basic wall can become a fixed wall after a ball is touched.  These basic walls are black.  
A level is lost if a ball goes off the grid or is destroyed.  Two balls are destroyed if they collide.  A ball is destroyed if it hits the end of a wall rather than the middle.  Lastly, there are spikes (black, pointy) that destroy any ball that hits them.  
A ball is the same color as the goal (open circle) it needs to reach.  A goal can be locked, meaning that there are keys the ball must hit before being able to reach the goal.  The keys are the same color as the goal they unlock.  All keys of a certain color must be hit before the goal is unlocked.  

This is an incomplete version, so it lacks many features.  Here is what is has:

Functional gameplay.  
Assorted levels.  
Rudimentary graphics.  
Rudimentary main menu, help, about, settings, pack select, level select, and game screens.  

Here is what is doesn't have:

Organized levels (most are playable, though, except for pack 4, which is a debug pack.  Packs 9, 10, and 11 are the most organized).  
Level management (all levels are available, levels do not restart when lost).  
Sounds.  
Music.  
Nice-looking graphics.  
Informative about screen.  
Informative help screen.  
Memory management.  
Any sort of player progress or achievements.  
