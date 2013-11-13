package gamearchitecture;

import at.fhooe.im440.ladar.message.*;
import at.fhooe.im440.ladar.time.Timekeeper;
import at.fhooe.im440.ladar.time.TimekeeperFactory;
import at.fhooe.im440.ladar.time.Timer;
import at.fhooe.im440.ladar.util.Loader;

public class Logic {
    protected TimekeeperFactory timekeeperFactory;
    protected Timekeeper timekeeper;
    protected Timer timer;
    protected MessengerFactory messengerFactory;
    protected Messenger messenger;

    public Logic() {
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


    }


    public void subscribe() {
        messenger.subscribe("testMessage", new SimpleReceiver() {
            @Override
            public void handleMessage(String type) {
                System.out.println("Message received");
            }
        });

    }
    public void send() {
        messenger.send(new SimpleMessage("testMessage"));
        messenger.execute();
    }
}
