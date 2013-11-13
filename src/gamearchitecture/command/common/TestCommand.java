package gamearchitecture.command.common;

import gamearchitecture.command.Command;

/**
 * Outputs a text on system.out
 *
 * Only use it as test class!
 */
public class TestCommand implements Command {


    @Override
    public void execute() {
        System.out.println("=========================");
        System.out.println(" Executed TestCommand... ");
        System.out.println("=========================");
        System.out.println("just work... :)");
    }
}
