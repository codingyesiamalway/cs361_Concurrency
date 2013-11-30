
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
			
			locks.passengerWaitForOtherPassengersToLeaveBeforeBoard.p();
			System.out.println("Person " + id + " boarded and is now waiting in the car. ");
			locks.numPassengerOnTheCar++;
			
			if (locks.numPassengerOnTheCar == locks.maxPassenger){
				locks.carWaitsForFullLoad.v();
			}
			
			locks.onBoardPassengenersWaitUntilCarStops.p();
			locks.numPassengerOnTheCar--;
			System.out.println("Person " + id + " left the car.");
			locks.passengerWaitForOtherPassengersToLeaveBeforeBoard.v();
		}
	}
	
	
	
	
	
	
}
