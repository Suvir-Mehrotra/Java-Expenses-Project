
public class Expense {
	private int myMonth;
	private int myDate;
	private int myYear;
	private double myAmount;
	private String myComment;
	public Expense(int month, int date, int year, double amount, String comment)
	{
		myMonth=month;
		myDate=date;
		myYear=year;
		myAmount=amount;
		myComment=comment;
	}
	public int getMyMonth() {
		return myMonth;
	}
	public void setMyMonth(int myMonth) {
		this.myMonth = myMonth;
	}
	public int getMyDate() {
		return myDate;
	}
	public void setMyDate(int myDate) {
		this.myDate = myDate;
	}
	public double getMyAmount() {
		return myAmount;
	}
	public void setMyAmount(double myAmount) {
		this.myAmount = myAmount;
	}
	public int getMyYear() {
		return myYear;
	}
	public void setMyYear(int myYear) {
		this.myYear = myYear;
	}
	public String getMyComment() {
		return myComment;
	}
	public void setMyComment(String myComment) {
		this.myComment = myComment;
	}
	public String toString()
	{
		String str = "";
		str+=myMonth+"\r\n";
		str+=myDate+"\r\n";
		str+=myYear+"\r\n";
		str+=myAmount+"\r\n";
		str+=myComment+"\r\n";
		return str;
	}
}
