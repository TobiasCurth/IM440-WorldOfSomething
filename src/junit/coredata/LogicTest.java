package coredata;

import gamearchitecture.Logic;
import org.junit.Test;
public class LogicTest {

    @Test
    public void firstTest() {
        Logic logic = new Logic();
        logic.subscribe();
        logic.send();
    }
}
