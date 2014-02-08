package ua.com.zaibalo.helper.ajax;

public class RateCommentResponse extends AjaxResponse{

	private int sum;
	private int count;
	
	public RateCommentResponse(int sum, int count) {
		super(true);
		this.sum = sum;
		this.count = count;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}