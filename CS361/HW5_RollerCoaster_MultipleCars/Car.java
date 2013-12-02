
public class Car implements Runnable{
	
	private Locks locks;
	private int id;
	
	public Car(Locks locks, int id){
		this.locks = locks;
		this.id = id;
	}
	
	@Override
	public void run() {
		boolean isEmpty = true;
		while (true){
			locks.carWaitsToPullOverAtStation.p();
			locks.currentCarId = id;
			System.out.println("car " + id + " arrives at station. ");
			if (!isEmpty){
				for (int i = 0; i < locks.maxPassenger; i++){
					locks.onBoardPassengenersWaitUntilCarStopsV(id);
				}
			}
			
			for (int i = 0; i < locks.maxPassenger; i++){
				locks.passengerWaitForCarToCome.v();
			}
			// Wait for fully loaded
			locks.carWaitsForFullyLoad.get(id).p();
			isEmpty = false;
			
			System.out.println("car " + id + " is fully loaded and now starts moving. ");
			Locks.sleep(1000);
			locks.carWaitsToPullOverAtStation.v();
		}
	}

}
