package gamearchitecture.engine;
import at.fhooe.im440.ladar.coredata.CoreData;
import at.fhooe.im440.ladar.coredata.CoreDataFactory;


public class MyCoreDataFactory implements CoreDataFactory{

	@Override
	public CoreData create() {
		// TODO Auto-generated method stub
		return new MyCoreData();
	}

}
