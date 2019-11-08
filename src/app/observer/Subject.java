package app.observer;

public interface Subject {
	void setObserver(Observer observer);

	void notifyObserver();
}
