import java.util.ArrayList;

public class SubCategory {
	private String myName;
	private ArrayList<Expense> myTotalExpenses;
	public SubCategory(String name)
	{
		myName=name;
		setMyTotalExpenses(new ArrayList<Expense>());
	}
	public String getMyName() {
		return myName;
	}
	public void setMyName(String myName) {
		this.myName = myName;
	}
	public ArrayList<Expense> getMyTotalExpenses() {
		return myTotalExpenses;
	}
	public void setMyTotalExpenses(ArrayList<Expense> myTotalExpenses) {
		this.myTotalExpenses = myTotalExpenses;
	}
	public void addExpense(Expense catExpense) {
		myTotalExpenses.add(catExpense);
		
	}
	public boolean equals(SubCategory subCat)
	{
		if(myName.equals(subCat.getMyName())&&myTotalExpenses.equals(subCat.getMyTotalExpenses()))
		{
			return true;
		}
		return false;
	}

	
}
