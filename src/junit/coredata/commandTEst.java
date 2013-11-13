package junit.coredata;

import gamearchitecture.command.Command;
import gamearchitecture.command.CommandStorage;
import gamearchitecture.command.common.TestCommand;
import org.junit.*;

public class commandTest {

    @Test
    public void foo() {
        CommandStorage storage = new CommandStorage();
        Command testCommand = new TestCommand();
        storage.addCommand(testCommand);

        storage.executeCommands();
    }
}
