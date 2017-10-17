package Facades;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Floor.Floor;
import Interfaces.FloorInterface;
import UserInputData.UserInputData;

public final class FloorFacade {
	
	//Class attributes
	private static FloorFacade instance;
	private int numberOfFloors;
	private HashMap<String, FloorInterface> floors;
		
	////////////////////////
	//				      //
	//    Constructor     //
	//				      //
	////////////////////////
	
	private FloorFacade() {
		this.initialize();
	}
	
	//A function to get an instance of the class. Initializes instance if needed
	public static FloorFacade getInstance() {
		if (instance == null) instance = new FloorFacade();
		return instance;
	}
	
	//Initializes all things necessary for the class
	private void initialize() {
		
		//Set class variables
		this.setNumberOfFloors(UserInputData.getInstance().getNumFloors());
		
		//Creates the hash map for floors to be stored
		this.createFloorsHashMap();
		
		//Creates floors
		this.createFloors();
	}
	
	//A function to initialize the hash map for floors to be stored
	private void createFloorsHashMap() {
		this.floors = new HashMap<String,FloorInterface>();
	}
	
	//A function to create the chosen number of floors
	private void createFloors() {
		for (int i = 1; i <= UserInputData.getInstance().getNumFloors(); i++) {
			String floorNumber = Integer.toString(i);
			this.floors.put(floorNumber, new Floor(floorNumber));
		}
	}
	

	private void setNumberOfFloors(int floors) {
		// TODO error handling
		this.numberOfFloors = floors;
	}
	
	
	
	//A function to gets the maximum number of floors
	public int getNumberOfFloors() {
		return this.numberOfFloors;
	}
	
	//A function to get the IDs of the all floors
	public String[] getFloorsIds() {
		String[] ids = printMap(this.floors);
		return ids;
	}
	
	private String[] printMap(Map<String, FloorInterface> map) {
	    Iterator it = map.entrySet().iterator();
	    String[] ids = new String[this.floors.size()];
	    int i = 0;
	    while (it.hasNext()) {
	    		Map.Entry<String, FloorInterface> pair = (Map.Entry<String, FloorInterface>)it.next();
	    		ids[i] = pair.getKey();
	        it.remove(); // avoids a ConcurrentModificationException
	        i++;
	    }
	    return ids;
	}
	
	/////////////////////////////////
	//				               //
	//    Facade Communication     //
	//				               //
	/////////////////////////////////
	
		
	public void addRiderToFloor(String floorId, String riderId) {
		FloorInterface theFloor = floors.get(floorId);
		theFloor.addRider(riderId);
		//start time...
	}
	
	
	public void notifyRidersOnThisFloor(String floorId, String elevatorId, String elevatorDirection) {
		FloorInterface theFloor = floors.get(floorId);
		for (String riderId: theFloor.getRiderIds()) {
			if (RiderFacade.getInstance().getRider(riderId).getDirection() == elevatorDirection) {
				ElevatorFacade.getInstance().addRiderToElevator(elevatorId, riderId);
			}
		}
	}
	

}
