package ua.com.zaibalo.model;

public class UserRating{
	
	private long sum;
	private long count;
	
	public UserRating(long sum, long count){
		this.setSum(sum);
		this.setCount(count);
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getCount() {
		return count;
	}

	public void setSum(long sum) {
		this.sum = sum;
	}

	public long getSum() {
		return sum;
	}
}
