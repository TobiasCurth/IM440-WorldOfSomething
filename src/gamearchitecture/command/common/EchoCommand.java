package gamearchitecture.command.common;

import gamearchitecture.command.Command;

public class EchoCommand implements Command {
    /**
     * the message to echo back.
     */
    private String parameter;

    /**
     * Sets the message to echo.
     * @param parameter
     */
    public EchoCommand(String parameter) {
        this.parameter = parameter;
    }

    /**
     * Outputs the message.
     */
    @Override
    public void execute() {


        System.out.println(parameter);
    }
}
