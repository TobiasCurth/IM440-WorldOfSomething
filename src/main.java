import at.fhooe.im440.ladar.loop.GameLoop;
import at.fhooe.im440.ladar.loop.GameLoopFactory;
import at.fhooe.im440.ladar.util.Loader;
import gamearchitecture.GameTask;

public class main {

    public static void main(String[] args) {
        GameLoopFactory gameLoopFactory = Loader.lookup(GameLoopFactory.class);
        GameLoop gameLoop= gameLoopFactory.create();

        gameLoop.loop(new GameTask());
    }
}
