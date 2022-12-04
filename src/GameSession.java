import IO.Commands;
import IO.ConsoleInput;
import IO.InterfaceView;
import Models.Bot;
import Models.Chip;
import Models.Field;
import Models.HumanPlayer;

public class GameSession {
    private int bestScore=0;
    void startSession(){
        InterfaceView.printStartingInfo();
        InterfaceView.printInfoAboutCommands();
        Commands command;
        while (true){
            try{
                command= ConsoleInput.commandInput();
                break;
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
        while (command!=Commands.End){
            executeCommand(command);
            InterfaceView.printInfoAboutCommands();
            while (true){
                try{
                    command= ConsoleInput.commandInput();
                    break;
                }
                catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    void executeCommand(Commands command){
        if(command==Commands.PVP){
            Field field = new Field();
            Game game = new Game(field,new HumanPlayer(field, Chip.White),new HumanPlayer(field,Chip.White));
            int gameScore = game.GameProcess();
            if(gameScore>bestScore){
                bestScore=gameScore;
            }
        }
        if(command==Commands.PVE){
            Field field = new Field();
            Game game = new Game(field,new HumanPlayer(field,Chip.White),new Bot(field,Chip.Black));
            int gameScore = game.GameProcess();
            if(gameScore>bestScore){
                bestScore=gameScore;
            }
        }
        if(command==Commands.Score){
            System.out.println(bestScore);
        }
    }
}
