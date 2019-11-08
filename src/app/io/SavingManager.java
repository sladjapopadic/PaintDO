package app.io;

public class SavingManager implements Saving{

	private Saving saving;
	
	public SavingManager(Saving saving) {
		this.saving = saving;
	}
	
	@Override
	public void save() {
		saving.save();
	}
}
