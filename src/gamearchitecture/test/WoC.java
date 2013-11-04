package gamearchitecture.test;

import at.fhooe.im440.ladar.coredata.*;
import at.fhooe.im440.ladar.util.Loader;
import gamearchitecture.engine.MyAttribute;

public class WoC {
	public static void main(String[] args){
		CoreDataFactory factory = Loader.lookup(CoreDataFactory.class);
		
		CoreData coreData = factory.create();
		coreData.createObject("room1");
        coreData.createObject("room2");
        coreData.getObject("room2").setParent(coreData.getObject("room1"));
        System.out.println(coreData.getObject("room1").getChild(0));

        MyAttribute color = new MyAttribute(MyAttribute.Type.INT, "color");
        //coreData.getObject("room2").setAttribute(color);
        System.out.println("World of something");
	}
}
