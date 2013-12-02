
public class Passenger implements Runnable{
	
	private Locks locks;
	private int id;
	
	
	public Passenger(Locks locks, int id){
		this.locks = locks;
		this.id = id;
	}
	
	@Override
	public void run() {
		while (true){
			System.out.println("Person " + id + " Waiting for car to come. ");
			locks.passengerWaitForCarToCome.p();
			
			//locks.passengerWaitForOtherPassengersToLeaveBeforeBoard.p();
			System.out.println("Person " + id + " boarded and is now waiting in car: " + locks.currentCarId + ". ");
			locks.addCarToPassenger(id,  locks.currentCarId);
			
			if (locks.getNumPassengerOnCurrentCar() == locks.maxPassenger){
				locks.currentCarWaitForFullLoadedV();
			}
			
			locks.onBoardPassengenersWaitUntilCarStopsP(id);
			System.out.println("Person " + id + " left the car.");
			locks.removeCarToPassenger(id, locks.currentCarId);
			//locks.passengerWaitForOtherPassengersToLeaveBeforeBoard.v();
		}
	}
	
	
	
	
	
	
}
