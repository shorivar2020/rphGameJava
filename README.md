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

Technické požadavky na semestrální práci:

+ Bude dokladovatelný progres projektu na Gitlabu. Průběžně (každou hotovou funkcionalitu s popisem) komitují všichni členové týmu.
+ Projekt bude v Mavenu.
+ Oba učástníci projektu musí podílet na tvorbě GUI. Alespoň jedno netriviální okno bude vytvořeno bez požití Designeru v Netbeans nebo jiném “klikacím” nástroji. [Thread in Game]
+ V projektu student předvede schopnost správně použít vlákna (za to se nepovažuje použití např. třídy Timer). Například vytvořením hodin reálného času, ktreré interagují s průběhem hry.
+ Několik netriviálních tříd bude pokryto unittesty nebo komplexnějším funkčním testem. Bude použit libovolný testovací framework.[GameTest]
+ Budou použity loggery. Logovací zprávy bude možno zapnout nebo vypnout parametrem při spuštění [@LOGGER]
+ Javadoc - Veškeré public prvky v programu musí mít smysluplný Javadoc
+ Kód bude vhodně okomentován.
+ Návod/Popis - Na wiki FEL GitLabu musí být návod na používání programu (uživatelský manuál) a popis programu - jeho vlastností, struktura projektu, použité technologie (technická dokumentace). Pozor, nesuplujte Javadoc.
+ Angličtina - všechno kromě dokumentace na GitLabu musí být v angličtině. Anglicky musí být i Javadoc.
+ Hra bude umět načítat seznam předmětů ze souboru. Tyto předměty bude mít hráč na začátku hry. Na konci hry bude umět hra uložit seznam předmětů ve stejném formátu. [Player have inventory after lose game it save in "items.txt. After win game player start with basic collar.]
+ Každý level bude popsaný v externím souboru v rozumném formátu – je na vás jaký formát si zvolíte. [MapLoader parsed "map.txt"]
+ V rámci hry bude implementován způsob souboje s příšerami. [Player interact with ENEMY]
+ Hrdina bude umět pomocí sebraných předmětů interagovat s dalšími předměty (otevře dveře klíčem, rozbije truhlu palicí atd.). [Player interact with ITEM]
+ Herní engine musí být vybaven GUI. 