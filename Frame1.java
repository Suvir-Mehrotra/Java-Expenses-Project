import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.UIManager;

public class Frame1 {

	private static final int maxWidth = 600;
	private static final int maxLength = 600;
	private JFrame frmExpenses;
	private ArrayList<Category> myCategories;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 window = new Frame1();
					window.frmExpenses.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame1() {
		frmExpenses = new JFrame();
		frmExpenses.setResizable(false);
		frmExpenses.setBounds(100, 100, maxWidth, maxLength);
		frmExpenses.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmExpenses.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				onExit(); 
			}
		});

		JPanel panel = new JPanel();
		frmExpenses.getContentPane().add(panel, BorderLayout.CENTER);
		initializeCategories();
		loadCategories();
		mainMenu(panel);


	}


	public void onExit() {
		saveCategories();
		System.exit(0);
	}
	private void loadCategories() {
		File file = new File("C:/Expenses/categories.txt");

		try {
			if(file.exists()==false)
			{
				file.createNewFile();
			}
			BufferedReader reader = new BufferedReader(new FileReader(file));
			int numCategories = Integer.parseInt(reader.readLine());
			for(int i =0; i<numCategories;i++)
			{
				String name = reader.readLine();
				int numSubs = Integer.parseInt(reader.readLine());
				if(numSubs==0)
				{
					int numExpenses = Integer.parseInt(reader.readLine());
					ArrayList<Expense> expenses= new ArrayList<Expense>();
					for(int j=0;j<numExpenses;j++)
					{
						int month = Integer.parseInt(reader.readLine());
						int date = Integer.parseInt(reader.readLine());
						int year = Integer.parseInt(reader.readLine());
						double amount = Double.parseDouble(reader.readLine());
						String comment = reader.readLine();
						Expense add = new Expense(month,date,year,amount,comment);
						expenses.add(add);
					}
					Category cat = new Category(name,0);
					cat.setMyTotalExpenses(expenses);
					myCategories.add(cat);
				}
				else
				{
					ArrayList<SubCategory> subs = new ArrayList<SubCategory>();
					for(int j=0;j<numSubs;j++)
					{
						String subName = reader.readLine();
						ArrayList<Expense> expenses= new ArrayList<Expense>();
						int numExpenses = Integer.parseInt(reader.readLine());
						for(int k=0;k<numExpenses;k++)
						{
							int month = Integer.parseInt(reader.readLine());
							int date = Integer.parseInt(reader.readLine());
							int year = Integer.parseInt(reader.readLine());
							double amount = Double.parseDouble(reader.readLine());
							String comment = reader.readLine();
							Expense add = new Expense(month,date,year,amount,comment);
							expenses.add(add);
						}
						SubCategory sub = new SubCategory(subName);
						sub.setMyTotalExpenses(expenses);
						subs.add(sub);
					}
					Category cat = new Category(name,numSubs);
					cat.setMySubs(subs);
					myCategories.add(cat);
				}

			}

			reader.close();
		} catch (Exception e) {

			e.printStackTrace();
		}



	}
	private void saveCategories() {
		// TODO Auto-generated method stub
		File file = new File("C:/Expenses/categories.txt");

		try {
			if(file.exists()==false)
			{
				file.createNewFile();
			}
			PrintWriter writer = new PrintWriter(file);
			writer.print(myCategories.size()+"\r\n");
			for(Category cat: myCategories)
			{
				writer.print(cat.toString());
			}
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initializeCategories() {
		// TODO Auto-generated method stub
		myCategories = new ArrayList<Category>();
	}
	public void viewExpenses(JPanel oldPanel)
	{
		frmExpenses.setTitle("View Expenses");
		JPanel newPanel = new JPanel();
		frmExpenses.getContentPane().remove(oldPanel);
		frmExpenses.getContentPane().add(newPanel);

		JButton btnNewButton = new JButton("View by Category(Individual Expenses)");
		btnNewButton.setBounds(136, 10, 250, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(myCategories.size()==0)
				{
					noCategories(newPanel);

				}
				else
				{
					listCategoriesForExpense(newPanel);
				}
			}
		});
		JButton btnTotal = new JButton("View by Category(Total for Category)");
		btnTotal.setBounds(136, 40, 250, 25);
		btnTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(myCategories.size()==0)
				{
					noCategories(newPanel);

				}
				else
				{
					listCategoriesForExpense(newPanel);
				}
			}
		});
		JButton btnQuarterly = new JButton("View by Quarterly");
		btnQuarterly.setBounds(136, 70, 250, 25);
		btnQuarterly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(myCategories.size()==0)
				{
					noCategories(newPanel);

				}
				else
				{
					listYears(newPanel,1);
				}
			}
		});
		JButton btnYearly = new JButton("View by Year");
		btnYearly.setBounds(136, 100, 250, 25);
		btnYearly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(myCategories.size()==0)
				{
					noCategories(newPanel);

				}
				else
				{
					listYears(newPanel,0);
				}
			}
		});
		newPanel.setLayout(null);
		newPanel.add(btnNewButton);
		newPanel.add(btnQuarterly);
		newPanel.add(btnYearly);
		newPanel.add(btnTotal);
		frmExpenses.setVisible(true);
	} 
	public void listYears(JPanel oldPanel,int i) {
		// TODO Auto-generated method stub

		JPanel newPanel = new JPanel();
		frmExpenses.getContentPane().remove(oldPanel);
		frmExpenses.getContentPane().add(newPanel);
		frmExpenses.setTitle("Enter Year");
		JTextPane textYear = new JTextPane();
		textYear.setText("Enter year below");
		textYear.setBounds(136, 11, 158, 25);
		newPanel.add(textYear);
		newPanel.setLayout(null);
		JTextField yearField = new JTextField();
		yearField.setBounds(136, 50, 100, 35);
		newPanel.add(yearField);
		yearField.setColumns(10);

		JButton btnNewButton = new JButton("Continue.");
		btnNewButton.setBounds(136, 125, 158, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int year = 0;
				try
				{

					year = Integer.parseInt(yearField.getText());
					if(i == 0)
					{
						listYearExpense(newPanel,year);
					}
					else
					{
						listQuarters(newPanel,year);
					}
				}
				catch(Exception e)
				{
				}
			}

		});
		newPanel.add(btnNewButton);
		frmExpenses.setVisible(true);
	}
	public void listYearExpense(JPanel oldPanel,int currYear) {
		// TODO Auto-generated method stub
		frmExpenses.setTitle("Yearly Expense for "+currYear);
		JPanel newPanel = new JPanel();
		frmExpenses.getContentPane().remove(oldPanel);
		frmExpenses.getContentPane().add(newPanel);
		double total = 0;
		for(int i = 0; i<myCategories.size(); i++)
		{
			Category curr = myCategories.get(i);
			if(curr.getMyNumSubs()==0)
			{
				ArrayList<Expense> exp = curr.getMyTotalExpenses();
				for(int j =0;j<exp.size();j++)
				{
					Expense currExp = exp.get(j);
					int year = currExp.getMyYear();
					if(year==currYear)
					{
						total+=currExp.getMyAmount();
					}

				}
			}
			else
			{
				ArrayList<SubCategory> subs = curr.getMySubs();
				for(int k = 0;k<subs.size();k++)
				{
					ArrayList<Expense> exp = subs.get(k).getMyTotalExpenses();
					for(int j =0;j<exp.size();j++)
					{
						Expense currExp = exp.get(j);
						int year = currExp.getMyYear();
						if(year==currYear)
						{
							total+=currExp.getMyAmount();
						}


					}
				}
			}
		}
		newPanel.setLayout(null);
		JTextPane textTotal = new JTextPane();
		textTotal.setText("Expense Total for year "+ currYear+": "+total);
		textTotal.setBounds(136, 11, 158, 50);
		newPanel.add(textTotal);

		JButton btnMenu = new JButton("Return to main Menu");
		btnMenu.setBounds(200, 85, 200, 25);
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainMenu(newPanel);
			}
		});
		newPanel.add(btnMenu);
		newPanel.setLayout(null);

		frmExpenses.setVisible(true);
	}

	public void listQuarters(JPanel oldPanel,int year) {
		frmExpenses.setTitle("View Quarters");
		JPanel newPanel = new JPanel();
		frmExpenses.getContentPane().remove(oldPanel);
		frmExpenses.getContentPane().add(newPanel);
		// TODO Auto-generated method stub
		JButton btnFirst = new JButton("Quarter 1");
		btnFirst.setBounds(136, 10, 158, 25);
		btnFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewQuarter(newPanel,1,year);
			}
		});
		JButton btnSecond = new JButton("Quarter 2");
		btnSecond.setBounds(136, 40, 158, 25);
		btnSecond.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewQuarter(newPanel,2,year);
			}
		});
		JButton btnThird = new JButton("Quarter 3");
		btnThird.setBounds(136, 70, 158, 25);
		btnThird.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewQuarter(newPanel,3,year);
			}
		});
		JButton btnFourth = new JButton("Quarter 4");
		btnFourth.setBounds(136, 100, 158, 25);
		btnFourth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewQuarter(newPanel,4,year);
			}
		});
		newPanel.setLayout(null);
		newPanel.add(btnFirst);
		newPanel.add(btnSecond);
		newPanel.add(btnThird);
		newPanel.add(btnFourth);
		frmExpenses.setVisible(true);
	}

	public void viewQuarter(JPanel oldPanel, int quarter,int currYear) {
		frmExpenses.setTitle("View Quarter "+ quarter);
		JPanel newPanel = new JPanel();
		frmExpenses.getContentPane().remove(oldPanel);
		frmExpenses.getContentPane().add(newPanel);
		double total = 0;
		for(int i = 0; i<myCategories.size(); i++)
		{
			Category curr = myCategories.get(i);
			if(curr.getMyNumSubs()==0)
			{
				ArrayList<Expense> exp = curr.getMyTotalExpenses();
				for(int j =0;j<exp.size();j++)
				{
					Expense currExp = exp.get(j);
					int month = currExp.getMyMonth();
					int year = currExp.getMyYear();
					if(year==currYear)
					{
						if(quarter==1)
						{
							if(month==1||month==2||month==3)
							{
								total+= currExp.getMyAmount();
							}						
						}
						else if(quarter==2)
						{
							if(month==4||month==5||month==6)
							{
								total+= currExp.getMyAmount();
							}
						}
						else if (quarter==3)
						{
							if(month==7||month==8||month==9)
							{
								total+= currExp.getMyAmount();
							}
						}
						else if(quarter==4)
						{
							if(month==10||month==11||month==12)
							{
								total+=currExp.getMyAmount();
							}
						}
					}
				}
			}
			else
			{
				ArrayList<SubCategory> subs = curr.getMySubs();
				for(int k = 0;k<subs.size();k++)
				{
					ArrayList<Expense> exp = subs.get(k).getMyTotalExpenses();
					for(int j =0;j<exp.size();j++)
					{

						Expense currExp = exp.get(j);
						int month = currExp.getMyMonth();
						int year = currExp.getMyYear();
						if(year==currYear)
						{
							if(quarter==1)
							{
								if(month==1||month==2||month==3)
								{
									total+= currExp.getMyAmount();
								}						
							}
							else if(quarter==2)
							{
								if(month==4||month==5||month==6)
								{
									total+= currExp.getMyAmount();
								}
							}
							else if (quarter==3)
							{
								if(month==7||month==8||month==9)
								{
									total+= currExp.getMyAmount();
								}
							}
							else if(quarter==4)
							{
								if(month==10||month==11||month==12)
								{
									total+=currExp.getMyAmount();
								}
							}
						}
					}
				}
			}
		}
		newPanel.setLayout(null);
		JTextPane textTotal = new JTextPane();
		textTotal.setText("Expense Total for Quarter "+ quarter+": "+total);
		textTotal.setBounds(136, 11, 158, 50);
		newPanel.add(textTotal);

		JButton btnMenu = new JButton("Return to main Menu");
		btnMenu.setBounds(200, 85, 200, 25);
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainMenu(newPanel);
			}
		});
		newPanel.add(btnMenu);
		newPanel.setLayout(null);

		frmExpenses.setVisible(true);
	}

	public void noCategories(JPanel oldPanel)
	{
		JPanel newPanel = new JPanel();
		frmExpenses.getContentPane().remove(oldPanel);
		frmExpenses.getContentPane().add(newPanel);
		frmExpenses.setTitle("No categories");
		JTextPane textNoCategories = new JTextPane();
		textNoCategories.setText("No categories entered. Please return to main menu.");
		textNoCategories.setBounds(200, 11, 158, 50);
		newPanel.add(textNoCategories);

		JButton btnNewButton = new JButton("Return to menu.");
		btnNewButton.setBounds(136, 85, 158, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainMenu(newPanel);
			}
		});
		newPanel.setLayout(null);
		newPanel.add(btnNewButton);
		frmExpenses.setVisible(true);
	}
	public void listCategoriesForExpense(JPanel oldPanel)
	{
		JPanel newPanel = new JPanel(); 
		frmExpenses.getContentPane().remove(oldPanel);
		frmExpenses.getContentPane().add(newPanel);
		frmExpenses.setTitle("Categories List");
		int x = 0;
		int y = 0;
		int width = 100;
		int length = 25;
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		for(int i =0;i<myCategories.size();i++)
		{
			JButton btnNewButton = new JButton(myCategories.get(i).getMyType());
			btnNewButton.setBounds(x, y, width, length);
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					viewExpenses(newPanel,btnNewButton.getText());


				}
			});
			newPanel.setLayout(null);
			newPanel.add(btnNewButton);
			if(x+width<=maxWidth)
			{
				x+=width;
			}
			else
			{
				x=0;
				y+=length;
			}


		}
		newPanel.setLayout(null);
		frmExpenses.setVisible(true);
	}
	public void viewExpenses(JPanel oldPanel,String name)
	{
		JPanel newPanel = new JPanel(); 
		frmExpenses.getContentPane().remove(oldPanel);
		frmExpenses.getContentPane().add(newPanel);
		frmExpenses.setTitle("Expenses List");
		Category cat = null;
		for(int i = 0; i< myCategories.size();i++)
		{
			if(myCategories.get(i).getMyType().equals(name))
			{
				cat = myCategories.get(i);
			}
		}
		if(cat.getMyNumSubs()>0)
		{
			int x = 0;
			int y = 0;
			int width = 175;
			int length = 25;
			JPanel newerPanel = new JPanel(); 
			frmExpenses.getContentPane().remove(newPanel);
			newPanel.setVisible(false);
			frmExpenses.getContentPane().add(newerPanel);
			for(SubCategory sub: cat.getMySubs())
			{

				JButton btnNewButton = new JButton(sub.getMyName());
				btnNewButton.setBounds(x, y, width, length);
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int x = 0;
						int y = 0;
						int width = 175;
						int length = 25;
						JPanel evenNewerPanel = new JPanel(); 
						frmExpenses.getContentPane().remove(newerPanel);
						newerPanel.setVisible(false);
						frmExpenses.getContentPane().validate();
						frmExpenses.getContentPane().add(evenNewerPanel);
						ArrayList<Expense> subExp =sub.getMyTotalExpenses();
						for(Expense exp: subExp)
						{

							JButton btnNewButton = new JButton("Date: "+ exp.getMyMonth()+"/"+exp.getMyDate()+"/"+exp.getMyYear());
							btnNewButton.setBounds(x, y, width, length);
							btnNewButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent arg0) {
									JPanel newestPanel = new JPanel(); 
									frmExpenses.getContentPane().remove(evenNewerPanel);
									evenNewerPanel.setVisible(false);
									frmExpenses.getContentPane().validate();
									frmExpenses.getContentPane().add(newestPanel);


									JTextPane textMonth = new JTextPane();
									textMonth.setText("Expense Month.");
									textMonth.setEditable(false);
									textMonth.setBounds(100, 10, 100, 35);
									newestPanel.add(textMonth);
									JTextPane monthField = new JTextPane();
									monthField.setBounds(200, 10, 100, 35);
									monthField.setText(""+exp.getMyMonth());
									newestPanel.add(monthField);
									monthField.setEditable(false);
									JTextPane textDate = new JTextPane();
									textDate.setText("Expense Date.");
									textDate.setEditable(false);
									textDate.setBounds(100, 55, 100, 35);
									newestPanel.add(textDate);
									JTextPane dateField = new JTextPane();
									dateField.setBounds(200, 55, 100, 35);
									dateField.setText(""+exp.getMyDate());
									newestPanel.add(dateField);
									dateField.setEditable(false);
									JTextPane textYear = new JTextPane();
									textYear.setText("Expense Year.");
									textYear.setEditable(false);
									textYear.setBounds(100, 100, 100, 35);
									newestPanel.add(textYear);
									JTextPane yearField = new JTextPane();
									yearField.setBounds(200, 100, 100, 35);
									yearField.setText(""+exp.getMyYear());
									newestPanel.add(yearField);
									yearField.setEditable(false);
									JTextPane textAmount = new JTextPane();
									textAmount.setText("Expense Amount.");
									textAmount.setEditable(false);
									textAmount.setBounds(100, 145, 100, 35);
									newestPanel.add(textAmount);
									JTextPane amountField = new JTextPane();
									amountField.setBounds(200, 145, 100, 35);
									amountField.setText(""+exp.getMyAmount());
									newestPanel.add(amountField);
									amountField.setEditable(false);
									JTextPane textComment = new JTextPane();
									textComment.setText("Expense Comment.");
									textComment.setEditable(false);
									textComment.setBounds(100, 190, 100, 35);
									newestPanel.add(textComment);
									JTextPane commentField = new JTextPane();
									commentField.setBounds(200, 190, 100, 70);
									commentField.setText(""+exp.getMyComment());
									newestPanel.add(commentField);
									commentField.setEditable(false);
									newestPanel.setLayout(null);
									JButton btnMenu = new JButton("Return to main Menu");
									btnMenu.setBounds(300, 85, 200, 25);
									btnMenu.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent arg0) {
											mainMenu(newestPanel);
										}
									});
									newestPanel.add(btnMenu);
									frmExpenses.setVisible(true);


								}

							});
							if(x+(2*width)<=maxWidth)
							{
								x+=width;
							}
							else
							{
								x=0;
								y+=length;
							}
							evenNewerPanel.add(btnNewButton);
							evenNewerPanel.setLayout(null);
							frmExpenses.setVisible(true);
						}
					}
				});
				newerPanel.add(btnNewButton);
				newerPanel.setLayout(null);
				frmExpenses.setVisible(true);
				if(x+(2*width)<=maxWidth)
				{
					x+=width;
				}
				else
				{
					x=0;
					y+=length;
				}
			}
		}
		else
		{
			JPanel newerPanel = new JPanel(); 
			frmExpenses.getContentPane().remove(newPanel);
			frmExpenses.getContentPane().add(newerPanel);
			newPanel.setVisible(false);
			int x = 0;
			int y = 0;
			int width = 125;
			int length = 25;
			ArrayList<Expense> catList = cat.getMyTotalExpenses();
			for(Expense exp: catList)
			{

				JButton btnNewButton = new JButton("Date: "+ exp.getMyMonth()+"/"+exp.getMyDate()+"/"+exp.getMyYear());
				btnNewButton.setBounds(x, y, width, length);
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JPanel evenNewerPanel = new JPanel(); 
						frmExpenses.getContentPane().remove(newerPanel);
						newerPanel.setVisible(false);
						frmExpenses.getContentPane().validate();
						frmExpenses.getContentPane().add(evenNewerPanel);


						JTextPane textMonth = new JTextPane();
						textMonth.setText("Expense Month.");
						textMonth.setEditable(false);
						textMonth.setBounds(100, 10, 100, 35);
						evenNewerPanel.add(textMonth);
						JTextPane monthField = new JTextPane();
						monthField.setBounds(200, 10, 100, 35);
						monthField.setText(""+exp.getMyMonth());
						evenNewerPanel.add(monthField);
						monthField.setEditable(false);
						JTextPane textDate = new JTextPane();
						textDate.setText("Expense Date.");
						textDate.setEditable(false);
						textDate.setBounds(100, 55, 100, 35);
						evenNewerPanel.add(textDate);
						JTextPane dateField = new JTextPane();
						dateField.setBounds(200, 55, 100, 35);
						dateField.setText(""+exp.getMyDate());
						evenNewerPanel.add(dateField);
						dateField.setEditable(false);
						JTextPane textYear = new JTextPane();
						textYear.setText("Expense Year.");
						textYear.setEditable(false);
						textYear.setBounds(100, 100, 100, 35);
						evenNewerPanel.add(textYear);
						JTextPane yearField = new JTextPane();
						yearField.setBounds(200, 100, 100, 35);
						yearField.setText(""+exp.getMyYear());
						evenNewerPanel.add(yearField);
						yearField.setEditable(false);
						JTextPane textAmount = new JTextPane();
						textAmount.setText("Expense Amount.");
						textAmount.setEditable(false);
						textAmount.setBounds(100, 145, 100, 35);
						evenNewerPanel.add(textAmount);
						JTextPane amountField = new JTextPane();
						amountField.setBounds(200, 145, 100, 35);
						amountField.setText(""+exp.getMyAmount());
						evenNewerPanel.add(amountField);
						amountField.setEditable(false);
						JTextPane textComment = new JTextPane();
						textComment.setText("Expense Comment.");
						textComment.setEditable(false);
						textComment.setBounds(100, 190, 100, 35);
						evenNewerPanel.add(textComment);
						JTextPane commentField = new JTextPane();
						commentField.setBounds(200, 190, 100, 35);
						commentField.setText(""+exp.getMyComment());
						evenNewerPanel.add(commentField);
						commentField.setEditable(false);
						evenNewerPanel.setLayout(null);
						JButton btnMenu = new JButton("Return to main Menu");
						btnMenu.setBounds(300, 85, 200, 25);
						btnMenu.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								mainMenu(evenNewerPanel);
							}
						});
						evenNewerPanel.add(btnMenu);
						frmExpenses.setVisible(true);


					}
				});
				newerPanel.setLayout(null);
				newerPanel.add(btnNewButton);
				frmExpenses.setVisible(true);
				if(x+width<=maxWidth)
				{
					x+=width;
				}
				else
				{
					x=0;
					y+=length;
				}

			}
		}
	}


	public void enterExpenses(JPanel oldPanel)
	{
		frmExpenses.setTitle("Enter Expenses");
		JPanel newPanel = new JPanel();
		frmExpenses.getContentPane().remove(oldPanel);
		frmExpenses.getContentPane().add(newPanel);
		if(myCategories.size()==0)
		{
			JTextPane textNoCategories = new JTextPane();
			textNoCategories.setText("No categories entered. Please click below to add a Category.");
			textNoCategories.setBounds(136, 11, 158, 50);
			newPanel.add(textNoCategories);
			JButton btnNewButton = new JButton("Add Category");
			btnNewButton.setBounds(136, 85, 158, 25);
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addCategory(newPanel);
				}
			});
			JButton btnMenu = new JButton("Return to main Menu");
			btnMenu.setBounds(136, 115, 200, 25);
			btnMenu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mainMenu(newPanel);
				}
			});
			newPanel.add(btnMenu);
			newPanel.setLayout(null);
			newPanel.add(btnNewButton);
		}
		else
		{
			JButton btnNewButton = new JButton("Add a category");
			btnNewButton.setBounds(100, 85, 250, 25);
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addCategory(newPanel);
				}
			});
			JButton btnAddCurr = new JButton("Add to a currently existing category");
			btnAddCurr.setBounds(100, 55, 250, 25);
			btnAddCurr.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					listCategoriesForAdd(newPanel);
				}
			});
			newPanel.add(btnNewButton);
			newPanel.add(btnAddCurr);
			JButton btnMenu = new JButton("Return to main Menu");
			btnMenu.setBounds(100, 115, 250, 25);
			btnMenu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mainMenu(newPanel);
				}
			});
			newPanel.add(btnMenu);
			newPanel.setLayout(null);
		}
		frmExpenses.setVisible(true);
	}
	public void listCategoriesForAdd(JPanel oldPanel) {
		// TODO Auto-generated method stub
		JPanel newPanel = new JPanel(); 
		frmExpenses.getContentPane().remove(oldPanel);
		frmExpenses.getContentPane().add(newPanel);
		frmExpenses.setTitle("Categories List");
		int x = 0;
		int y = 0;
		int width = 100;
		int length = 25;
		for(int i =0;i<myCategories.size();i++)
		{
			JButton btnNewButton = new JButton(myCategories.get(i).getMyType());
			btnNewButton.setBounds(x, y, width, length);
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					addToCategory(newPanel,btnNewButton.getText());


				}
			});
			newPanel.setLayout(null);
			newPanel.add(btnNewButton);
			if(x+width<=maxWidth)
			{
				x+=width;
			}
			else
			{
				x=0;
				y+=length;
			}


		}
		newPanel.setLayout(null);
		frmExpenses.setVisible(true);
	}

	public void addToCategory(JPanel oldPanel, String name) {
		// TODO Auto-generated method stub
		JPanel newPanel = new JPanel(); 
		frmExpenses.getContentPane().remove(oldPanel);
		frmExpenses.getContentPane().add(newPanel);
		frmExpenses.setTitle("Expenses List");
		Category cat = null;
		for(int i = 0; i< myCategories.size();i++)
		{
			if(myCategories.get(i).getMyType().equals(name))
			{
				cat = myCategories.get(i);
			}
		}
		if(cat.getMyNumSubs()>0)
		{
			int x = 0;
			int y = 0;
			int width = 100;
			int length = 25;
			JPanel newerPanel = new JPanel(); 
			frmExpenses.getContentPane().remove(newPanel);
			newPanel.setVisible(false);
			frmExpenses.getContentPane().add(newerPanel);
			for(SubCategory sub: cat.getMySubs())
			{




				JButton btnNewButton = new JButton(sub.getMyName());
				btnNewButton.setBounds(x, y, width, length);
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Category cat = null;
						for(int i = 0; i< myCategories.size();i++)
						{
							if(myCategories.get(i).getMyType().equals(name))
							{
								cat = myCategories.get(i);
							}
						}
						enterSingleExpense(newerPanel, cat, sub, cat.getMyNumSubs());
					}
				});
				newerPanel.setLayout(null);
				newerPanel.add(btnNewButton);
				frmExpenses.setVisible(true);
				if(x+width<maxWidth)
				{
					x+=width;
				}
				else
				{
					x=0;
					y+=length;
				}


			}

		}
		else
		{
			enterSingleExpense(newPanel,cat,null,0);
		}
	}

	public void addCategory(JPanel oldPanel)
	{
		frmExpenses.setTitle("Add Category");
		JPanel newPanel = new JPanel();
		frmExpenses.getContentPane().remove(oldPanel);
		frmExpenses.getContentPane().add(newPanel);
		JTextPane textName = new JTextPane();
		textName.setText("Enter name.");
		textName.setEditable(false);
		textName.setBounds(100, 10, 100, 35);
		newPanel.add(textName);
		JTextField name = new JTextField();
		name.setBounds(200, 10, 100, 35);
		newPanel.add(name);
		name.setColumns(10);
		JTextPane textSub = new JTextPane();
		textSub.setText("Enter number of subcategories.");
		textSub.setEditable(false);
		textSub.setBounds(100, 50, 100, 55);
		newPanel.add(textSub);
		JTextField sub = new JTextField();
		sub.setBounds(200, 50, 100, 35);
		newPanel.add(sub);
		sub.setColumns(10);

		JButton btnNewButton = new JButton("Continue.");
		btnNewButton.setBounds(136, 125, 158, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String catName = name.getText();
				String catSub = sub.getText();
				int catNumSub = -1;
				try
				{
					catNumSub = Integer.parseInt(catSub);
				}
				catch(Exception e)
				{
					JTextPane textTryAgain = new JTextPane();
					textTryAgain.setText("Try again please");
					textTryAgain.setEditable(false);
					textTryAgain.setBounds(175, 160, 100, 20);
					newPanel.add(textTryAgain);
					textSub.setText("Please enter a nonnegative # of subcategories.");
					return;
				}
				if(catNumSub<0)
				{
					JTextPane textTryAgain = new JTextPane();
					textTryAgain.setText("Try again please");
					textTryAgain.setEditable(false);
					textTryAgain.setBounds(175, 160, 100, 20);
					newPanel.add(textTryAgain);
					textSub.setText("Please enter a nonnegative # of subcategories.");
					return;
				}
				else
				{

					Category category1 = new Category(catName,catNumSub);
					processCategory(newPanel,category1,1);
				}
			}
		});
		newPanel.setLayout(null);
		newPanel.add(btnNewButton);

		frmExpenses.setVisible(true);
	}
	public void processCategory(JPanel oldPanel,Category category, int numSubsProcessed)
	{
		frmExpenses.setTitle("Process Category");
		JPanel newPanel = new JPanel();
		frmExpenses.getContentPane().remove(oldPanel);
		frmExpenses.getContentPane().add(newPanel);
		int numSubs = category.getMyNumSubs();
		boolean found =false;
		int index = 0;
		for(int i =0;i<myCategories.size();i++)
		{
			if(myCategories.get(i).equals(category))
			{
				found = true;
				index = i;
			}
		}
		if(!found)
		{
			myCategories.add(category);
		}
		if(numSubs==0)
		{

			enterSingleExpense(newPanel,category,null,0);


		}
		else
		{
			if(numSubsProcessed<=numSubs)
			{
				addSubCategory(newPanel,myCategories.get(index),numSubsProcessed);
			}
			else
			{
				mainMenu(newPanel);
			}
		}
		newPanel.setLayout(null);
		frmExpenses.setVisible(true);
	}
	private void addSubCategory(JPanel oldPanel, Category category, int subNum) {
		if(myCategories.get(myCategories.size()-1).getMyNumSubs() <=subNum)
		{
			mainMenu(oldPanel);
		}
		frmExpenses.setTitle("Enter Subcategories");
		JPanel newPanel = new JPanel();
		frmExpenses.getContentPane().remove(oldPanel);
		frmExpenses.getContentPane().add(newPanel);
		JTextPane textSubNum = new JTextPane();
		textSubNum.setText("Entering subcategory #" + subNum);
		textSubNum.setEditable(false);
		textSubNum.setBounds(135, 10, 158, 35);
		newPanel.add(textSubNum);
		JTextPane textName = new JTextPane();
		textName.setText("Enter Subcategory Name:");
		textName.setEditable(false);
		textName.setBounds(100, 55, 100,55);
		newPanel.add(textName);
		JTextField nameField = new JTextField();
		nameField.setBounds(200, 55, 100, 55);
		newPanel.add(nameField);
		nameField.setColumns(10);
		JButton btnNewButton = new JButton("Continue.");
		btnNewButton.setBounds(300, 125, 100, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = nameField.getText();
				SubCategory sub = new SubCategory(name);
				myCategories.get(myCategories.size()-1).addSubCategory(sub);
				newPanel.setVisible(false);
				enterSingleExpense(newPanel,myCategories.get(myCategories.size()-1),sub,subNum);
			}
		});
		newPanel.setLayout(null);
		newPanel.add(btnNewButton);
		frmExpenses.setVisible(true);

		//add to last mycategories slot
	}

	public void enterSingleExpense(JPanel oldPanel,Category category, SubCategory subCat,int subNum)
	{
		frmExpenses.setTitle("Enter Expense");
		JPanel newPanel = new JPanel();
		frmExpenses.getContentPane().remove(oldPanel);
		frmExpenses.getContentPane().validate();
		frmExpenses.getContentPane().add(newPanel);
		JTextPane textMonth = new JTextPane();
		textMonth.setText("Enter Expense Month.");
		textMonth.setEditable(false);
		textMonth.setBounds(100, 10, 100, 35);
		newPanel.add(textMonth);
		JTextField monthField = new JTextField();
		monthField.setBounds(200, 10, 100, 35);
		newPanel.add(monthField);
		monthField.setColumns(10);
		JTextPane textDate = new JTextPane();
		textDate.setText("Enter Expense Date.");
		textDate.setEditable(false);
		textDate.setBounds(100, 55, 100, 35);
		newPanel.add(textDate);
		JTextField dateField = new JTextField();
		dateField.setBounds(200, 55, 100, 35);
		newPanel.add(dateField);
		dateField.setColumns(10);
		JTextPane textYear = new JTextPane();
		textYear.setText("Enter Expense Year.");
		textYear.setEditable(false);
		textYear.setBounds(100, 100, 100, 35);
		newPanel.add(textYear);
		JTextField yearField = new JTextField();
		yearField.setBounds(200, 100, 100, 35);
		newPanel.add(yearField);
		yearField.setColumns(10);
		JTextPane textAmount = new JTextPane();
		textAmount.setText("Enter Expense Amount(ex. 12.00).");
		textAmount.setEditable(false);
		textAmount.setBounds(100, 145, 100, 35);
		newPanel.add(textAmount);
		JTextField amountField = new JTextField();
		amountField.setBounds(200, 145, 100, 35);
		newPanel.add(amountField);
		amountField.setColumns(10);
		JTextPane textComment = new JTextPane();
		textComment.setText("Enter Expense Comment.(ex. No Comment.)");
		textComment.setEditable(false);
		textComment.setBounds(100, 190, 100, 35);
		newPanel.add(textComment);
		JTextField commentField = new JTextField();
		commentField.setBounds(200, 190, 100, 35);
		newPanel.add(commentField);
		commentField.setColumns(10);
		JButton btnNewButton = new JButton("Continue.");
		btnNewButton.setBounds(300, 125, 100, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int month =-1;
				int date = -1;
				int year = -1;
				double amount = -1.0;
				String comment = "";
				try
				{
					month = Integer.parseInt(monthField.getText());
					date = Integer.parseInt(dateField.getText());
					year = Integer.parseInt(yearField.getText());
					amount = Double.parseDouble(amountField.getText());
					comment = commentField.getText();

				}
				catch(Exception e)
				{
					JTextPane textTryAgain = new JTextPane();
					textTryAgain.setText("Try again please.");
					textTryAgain.setEditable(false);
					textTryAgain.setBounds(300, 160, 100, 20);
					newPanel.add(textTryAgain);
				}
				if(month<0||date<0||year<0||amount<0.0||comment.length()==0)
				{
					JTextPane textTryAgain = new JTextPane();
					textTryAgain.setText("Try again please.");
					textTryAgain.setEditable(false);
					textTryAgain.setBounds(300, 160, 100, 20);
					newPanel.add(textTryAgain);
				}
				else
				{
					Expense retExpense  = new Expense(month,date,year,amount,comment);

					for(int i = 0; i<myCategories.size();i++)
					{
						if(myCategories.get(i).equals(category))
						{
							if(subCat==null)
							{
								myCategories.get(i).addExpense(retExpense);
								mainMenu(newPanel);
							}
							else
							{
								for(int j = 0; j<myCategories.get(i).getMySubs().size();j++)
								{
									if(myCategories.get(i).getMySubs().get(j).equals(subCat))
									{
										myCategories.get(i).getMySubs().get(j).addExpense(retExpense);
										processCategory(newPanel,myCategories.get(i),subNum+1);
									}
								}
							}
						}
					}
				}
			}
		});
		newPanel.setLayout(null);
		newPanel.add(btnNewButton);
		frmExpenses.setVisible(true);

	}

	public void mainMenu(JPanel oldPanel)
	{

		frmExpenses.setTitle("Expenses Program");
		JPanel panel = new JPanel();
		frmExpenses.getContentPane().remove(oldPanel);
		frmExpenses.getContentPane().add(panel);
		JButton btnNewButton = new JButton("View Expenses");
		btnNewButton.setBounds(136, 11, 158, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				viewExpenses(panel);
			}
		});
		panel.setLayout(null);
		panel.add(btnNewButton);

		JButton btnEnterExpenses = new JButton("Enter Expenses");
		btnEnterExpenses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enterExpenses(panel);
			}
		});
		btnEnterExpenses.setBounds(136, 49, 158, 25);
		panel.add(btnEnterExpenses);

		JButton btnEnterRevenue = new JButton("Enter Total Revenue");
		btnEnterRevenue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnEnterRevenue.setBounds(136, 85, 158, 25);
		panel.add(btnEnterRevenue);

		JButton btnViewTotalProfit = new JButton("View Total Profit/Loss");
		btnViewTotalProfit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnViewTotalProfit.setBounds(136, 125, 158, 25);
		panel.add(btnViewTotalProfit);

		JTextPane txtpnPleaseSelectAn = new JTextPane();
		txtpnPleaseSelectAn.setEditable(false);
		txtpnPleaseSelectAn.setText("Please select an option.");
		txtpnPleaseSelectAn.setBounds(153, 168, 141, 25);

		panel.add(txtpnPleaseSelectAn);
	}
}
