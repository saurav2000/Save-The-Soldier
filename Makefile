all: frame
	java Game

frame: panel Game.java
	javac Game.java

panel: menu outside camp healing Panel.java
	javac Panel.java

menu: imgload Menu.java
	javac Menu.java

outside: imgload soldier Outside.java
	javac Outside.java

healing: imgload Healing.java
	javac Healing.java

camp: imgload soldier Camp.java
	javac Camp.java

imgload: ImageLoader.java
	javac ImageLoader.java

soldier: Soldier.java
	javac Soldier.java

clean: 
	rm *.class