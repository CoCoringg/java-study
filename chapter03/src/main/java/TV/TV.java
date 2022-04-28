package TV;

public class TV {
	private int channel; // 1 ~ 250 rotation
	private int volume; // 0 ~ 100 rotation 
	private boolean power;
	
	public TV() {
	}
	
	public TV(int channel, int volume, boolean power) {
		this.channel = channel;
		this.volume = volume;
		this.power = power;
	}
	
	public void status() {
		System.out.println("TV[channel="+ channel + 
				", volume=" + volume + 
				", power=" + (power ? "on":"off") + "]");
		
	}

	public void power(boolean power) {
		this.power = power;
	}

	public void volume(int volume) {
		if (volume > 100) {
			this.volume = volume % 100;
		} else if (volume < 0) {
			this.volume = 100 - Math.abs(volume);
		} else {
			this.volume = volume;
		}
	}
	
	public void volume(boolean up) {
		volume(volume += (up?1:-1));
	}

	public void channel(int channel) {
		if (channel > 255) {
			this.channel = channel % 255;
		} else if (channel < 0) {
			this.channel = 255 - Math.abs(channel);
		} else {
			this.channel = channel;
		}
			
	}
	
	public void channel(boolean up) {
		channel(channel +=(up?1:-1));
	}

	public int getChannel() {
		return channel;
	}

	public int getVolume() {
		return volume;
	}

	public boolean isPower() {
		return power;
	}
	
	
	

}
