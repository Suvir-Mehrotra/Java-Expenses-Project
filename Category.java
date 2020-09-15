import java.util.ArrayList;

public class Category {
	private String myType;
	private int myNumSubs;
	private ArrayList<SubCategory> mySubs;
	private ArrayList<Expense> myTotalExpenses;
	
	public Category(String type,int numSubs)
	{
		myType=type;
		mySubs= new ArrayList<SubCategory>();
		myTotalExpenses= new ArrayList<Expense>();
		setMyNumSubs(numSubs);
	}
	public String getMyType() {
		return myType;
	}
	public void setMyType(String myType) {
		this.myType = myType;
	}
	public ArrayList<SubCategory> getMySubs() {
		return mySubs;
	}
	public void setMySubs(ArrayList<SubCategory> mySubs) {
		this.mySubs = mySubs;
	}
	public ArrayList<Expense> getMyTotalExpenses()
	{
		return myTotalExpenses;
	}
	/*public int getMyTotalExpenses() {
		if(myNumSubs>0)
		{
			int subTotal = 0;
			for(int i = 0; i<myNumSubs;i++)
			{
				ArrayList<Expense> subExpense = mySubs.get(i).getMyTotalExpenses();
				for(int j = 0;j<subExpense.size();i++)
				{
					subTotal+=subExpense.get(j).getMyAmount();
				}
			}
			return subTotal;
		}
		int total = 0;
		for(int i = 0; i< myTotalExpenses.size();i++)
		{
			total+= myTotalExpenses.get(i).getMyAmount();
		}
		return total;
	}
	*/
	public void setMyTotalExpenses(ArrayList<Expense> myTotalExpenses) {
		this.myTotalExpenses = myTotalExpenses;
	}
	public int getMyNumSubs() {
		return myNumSubs;
	}
	public void setMyNumSubs(int myNumSubs) {
		this.myNumSubs = myNumSubs;
	}
	public void addExpense(Expense catExpense) {
		myTotalExpenses.add(catExpense);

	}
	public void addSubCategory(SubCategory sub)
	{
		mySubs.add(sub);
	}
	public boolean equals(Category cat)
	{
		if(myType.equals(cat.getMyType())&&mySubs.equals(cat.getMySubs())
				&&myTotalExpenses.equals(cat.getMyTotalExpenses())&&myNumSubs==cat.getMyNumSubs())
		{
			return true;
		}
		return false;
	}
	public String toString()
	{
		String str = "";
		str+=myType+"\r\n";
		str+=myNumSubs+"\r\n";
		if(myNumSubs==0)
		{
			str+=myTotalExpenses.size()+"\r\n";
			for(Expense exp: myTotalExpenses)
			{
				str+=exp.toString();
			}
		}
		else
		{
			for(SubCategory sub : mySubs)
			{
				str+=sub.getMyName()+"\r\n";
				ArrayList<Expense> exps = sub.getMyTotalExpenses();
				str+=exps.size()+"\r\n";
				for(Expense exp: exps )
				{
					str+=exp.toString();
				}
			}
		}
		return str;
		
	}
}
