package gamearchitecture;

import at.fhooe.im440.ladar.message.Messenger;
import at.fhooe.im440.ladar.message.MessengerFactory;
import at.fhooe.im440.ladar.message.SimpleMessage;
import at.fhooe.im440.ladar.message.SimpleReceiver;
import at.fhooe.im440.ladar.task.Task;
import at.fhooe.im440.ladar.time.Timekeeper;
import at.fhooe.im440.ladar.time.TimekeeperFactory;
import at.fhooe.im440.ladar.time.Timer;
import at.fhooe.im440.ladar.util.Loader;

public class GameTask implements Task {
    protected TimekeeperFactory timekeeperFactory;
    protected Timekeeper timekeeper;
    protected Timer timer;
    protected MessengerFactory messengerFactory;
    protected Messenger messenger;


    @Override
    public void initialize() {
        System.out.println("Game will be initialize.");

        /**
         * Timesystem
         */
        timekeeperFactory = Loader.lookup(TimekeeperFactory.class);
        timekeeper = timekeeperFactory.create();
        timekeeper.initialize();
        timer = timekeeper.createTimer("logic");

        /**
         * Messenger
         */
        messengerFactory = Loader.lookup(MessengerFactory.class);
        messengerFactory = messengerFactory.timer(timer);
        messenger = messengerFactory.create();
        messenger.initialize();

        System.out.println("subscribe");
        messenger.subscribe("testMessage", new SimpleReceiver() {
            @Override
            public void handleMessage(String type) {
                System.out.println("Message received");
            }
        });
    }

    @Override
    public boolean execute() {

        /**
         * Update Timers
         */
        timekeeper.execute();


        /**
         * Messages
         */
        messenger.send(new SimpleMessage("testMessage"));
        messenger.execute();

        // Todo: receive messages
        // Todo: handle (execute)
        // Todo: send back messages (frontend)
        // Todo: task -> is done
        return true;
    }

    @Override
    public void cleanup() {
        System.out.println("Game will be cleanup.");
        timekeeper.cleanup();
        messenger.cleanup();
    }
}
