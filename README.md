# Cat Adventure
PJV project 2023 Shorina Varvara

## Gameplay
#### Control with the keyboard WASD or arrow keys.
#### For open inventory push I.
The user controls the cat. The cat can look for things in garbage cans in the streets and pick up things in houses. With the key he can open doors. Each door has its own unique key. Food adds health. A collar adds health and strength. When in contact with the enemy, damage can be received, as well as you can hit the enemy. 

## List of classes

##### |--- Main.java
##### |--- Player.java
##### |--- MapLoader.java
##### |--- InterfaceBar.java
##### |--- Game.java //Manage process
##### |--- GameOverView.java //Ending option
##### |--- GameWonView.java //Ending option
##### |--- Entity //Objects with which you can interact
##### |#####|--- Entity.java [Abstract]
##### |#####|--- Door.java //For open need key
##### |#####|--- Exit.java
##### |#####|--- Background.java
##### |#####|--- Obstacle.java //Structures, trees and water bodies
##### |#####|--- TrashCan.java //Place for storing objects
##### |#####|--- enemy
##### |#####|#####|--- Dog.java
##### |#####|#####|--- Enemy.java [Abstract]
##### |#####|#####|--- Rat.java
##### |#####|--- item //Objects that can be placed in the inventory or to wear
##### |#####|#####|--- Item.java [Abstract]
##### |#####|#####|--- Food.java
##### |#####|#####|--- Key.java
##### |#####|#####|--- collar //Armor
##### |#####|#####|#####|--- Collar.java [Abstract]
##### |#####|#####|#####|--- GoldCollar.java
##### |#####|#####|#####|--- SilverCollar.java

