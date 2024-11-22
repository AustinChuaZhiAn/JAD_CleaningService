package Model;

public class Frequency {
	private int frequencyId = 0;
	private String frequency = "";
	
	public Frequency(int frequencyId, String frequency) {
		this.frequencyId = frequencyId;
		this.frequency = frequency;
	}

	public int getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(int frequencyId) {
		this.frequencyId = frequencyId;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
}
