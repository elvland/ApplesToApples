echo Compiling files
javac -sourcepath src\ -cp .\out\ .\src\Hemtenta\Game\Main.java -d comp

@echo Running Appels to apples game
java -cp comp Hemtenta.Game.Main
