package gamearchitecture.command;


import java.util.ArrayList;
import java.util.List;

/**
 * Stores the command queue.
 */
@Deprecated
public class CommandStorage {

    /**
     * The command queue.
     */
    protected List<Command> commandQueue;

    /**
     * Initialize the queue.
     */
    public CommandStorage() {
        this.commandQueue = new ArrayList<>();
    }

    /**
     * Adds an command to queue.
     * @param command
     */
    public void addCommand(Command command) {
        commandQueue.add(command);
    }

    /**
     * Removes an command from queue.
     * @param command
     */
    public void removeCommand(Command command) {
        commandQueue.remove(command);
    }

    /**
     * Returns the command queue.
     * @return commandQueue
     */
    public List<Command> getCommandQueue() {
        return commandQueue;
    }

    /**
     * Clears the command queue.
     */
    public void clearCommands() {
        commandQueue.clear();
    }

    /**
     * Executes every command in list.
     *
     * At first copy the whole list to avoid exceptions (deleted objects during executing)
     */
    public void executeCommands() {
        ArrayList<Command> copy = new ArrayList<>();
        for (Command c : commandQueue) {
            copy.add(c);
        }

        for (Command command : copy) {
            command.execute();
            commandQueue.remove(command);
        }
    }
}
