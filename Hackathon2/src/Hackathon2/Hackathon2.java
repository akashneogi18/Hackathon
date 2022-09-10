package Hackathon2;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
class ToDoList extends JFrame implements ActionListener, WindowListener{
	String tmp;
	JMenuBar mb;
	JMenu menu, help, edit;
	JMenuItem home, view, add, about, delete, update;

	JTextArea ta;

	SpinnerModel hour, minute, second, days, months, years;
	JSpinner hrs, min, sec, day, mon, yrs;

	JLabel task, date, time;
	JTextField tsk;
	JButton save, updt, dlt, save2;
	
	JScrollPane scrollBar;

	Connection conn; 
	Statement stmt;
	ToDoList() {
		mb=new JMenuBar();  
		mb.setBounds(0,0,500,20);
		add(mb);
		menu = new JMenu("Menu");
		help = new JMenu("Help");
		mb.add(menu);mb.add(help);
		view = new JMenuItem("View Task");
		add = new JMenuItem("Add Task");
		edit = new JMenu("Edit Task");
		menu.add(view);menu.add(add);menu.add(edit);
		view.addActionListener(this);
		add.addActionListener(this);
		about = new JMenuItem("About");
		help.add(about);
		about.addActionListener(this);
		update = new JMenuItem("Update Task");
		delete = new JMenuItem("Delete Task");
		edit.add(update);edit.add(delete);
		update.addActionListener(this);
		delete.addActionListener(this);
		
		ta = new JTextArea("");
		ta.setVisible(false);
		ta.setBounds(50,50,400,300);
		ta.setBackground(Color.lightGray);
		add(ta);
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);

		task = new JLabel("Task to do");
		task.setBounds(50,150,150,30);
		task.setVisible(false);
		date = new JLabel("Date(DD/MM/YYYY)");
		date.setBounds(50,100,150,30);
		date.setVisible(false);
		time = new JLabel("Time(HH:MM:SS)");
		time.setBounds(50,50,150,30);
		time.setVisible(false);
		add(task);add(date);add(time);
		tsk = new JTextField("");
		tsk.setBounds(200, 150, 150, 30);
		tsk.setVisible(false);
		add(tsk);

		save = new JButton("SAVE");
		save.setBounds(250,200,100,30);
		save.addActionListener(this);
		save.setVisible(false);
		save.setBackground(Color.green);
		add(save);
		updt = new JButton("UPDATE");
		updt.setBounds(250,200,100,30);
		updt.addActionListener(this);
		updt.setVisible(false);
		updt.setBackground(Color.white);
		add(updt);
		dlt = new JButton("DELETE");
		dlt.setBounds(250,200,100,30);
		dlt.addActionListener(this);
		dlt.setVisible(false);
		dlt.setBackground(Color.red);
		add(dlt);
		save2 = new JButton("SAVE");
		save2.setBounds(250,200,100,30);
		save2.addActionListener(this);
		save2.setVisible(false);
		save2.setBackground(Color.green);
		add(save2);

		hour =  new SpinnerNumberModel(00, -1, 24, 1);
		minute = new SpinnerNumberModel(00, -1, 60, 1);
		second = new SpinnerNumberModel(00, -1, 60, 1);
		hrs = new JSpinner(hour);
		min = new JSpinner(minute);
		sec = new JSpinner(second);
		JSpinner.NumberEditor h = new JSpinner.NumberEditor(hrs, "00"); 
		hrs.setEditor(h);
		JSpinner.NumberEditor m = new JSpinner.NumberEditor(min, "00"); 
		min.setEditor(m);
		JSpinner.NumberEditor s = new JSpinner.NumberEditor(sec, "00"); 
		sec.setEditor(s);
		hrs.setBounds(200 , 50, 50, 30);
		min.setBounds(250 , 50, 50, 30);
		sec.setBounds(300 , 50, 50, 30);
		add(hrs);add(min);add(sec);
		hrs.setVisible(false);
		min.setVisible(false);
		sec.setVisible(false);

		days =  new SpinnerNumberModel(01, 0, 32, 1);
		months = new SpinnerNumberModel(01, 0, 13, 1);
		years = new SpinnerNumberModel(2022, 2022, 2050, 1);
		day = new JSpinner(days);
		mon = new JSpinner(months);
		yrs = new JSpinner(years);
		JSpinner.NumberEditor d = new JSpinner.NumberEditor(day, "00"); 
		day.setEditor(d);
		JSpinner.NumberEditor ms = new JSpinner.NumberEditor(mon, "00"); 
		mon.setEditor(ms);
		JSpinner.NumberEditor y = new JSpinner.NumberEditor(yrs, "0000"); 
		yrs.setEditor(y);
		day.setBounds(200 , 100, 50, 30);
		mon.setBounds(250 , 100, 50, 30);
		yrs.setBounds(300 , 100, 50, 30);
		add(day);add(mon);add(yrs);
		day.setVisible(false);
		mon.setVisible(false);
		yrs.setVisible(false);
		
		
		if (SystemTray.isSupported()) {
            final SystemTray tray = SystemTray.getSystemTray();
            Image icon = null;
			try {
				icon = ImageIO.read(new File("C:\\Users\\akash\\Downloads\\icon2.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            final TrayIcon trayIcon = new TrayIcon(icon);
            trayIcon.setImageAutoSize(true);
            
            addWindowStateListener(new WindowAdapter() {
                @Override
                public void windowStateChanged(WindowEvent e) {
                    if (e.getNewState() == JFrame.ICONIFIED) {
                        trayIcon.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                setVisible(true);
                                toFront();
                                tray.remove(trayIcon);
                            }
                        });
                        try 
                        {
                            tray.add(trayIcon);
                        } catch (AWTException ex) 
                        {
                            ex.printStackTrace();
                        }
                        setVisible(false);
                    }
                }
            });
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		setLayout(null);
		setLocation(50,50); 
		getContentPane().setBackground(Color.white);
		setLayout(new BorderLayout());
		JLabel background=new JLabel(new ImageIcon("D:/WALLPAPERS/watchglass.jpg"));
		add(background);
		background.setLayout(new FlowLayout());
		setVisible(true);  
		setSize(500,400);
		setTitle("Task To Do Utility");
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				try{  
					Class.forName("com.mysql.cj.jdbc.Driver");
					conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","akash","akash");
					stmt=conn.createStatement();
				}
				catch(Exception e1) {
					System.out.println(e1);
				} 
			}
			public void windowClosing(WindowEvent e) {
				try{
					conn.close();
				}
				catch(Exception e1) {
					System.out.println(e1);
				} 
			}
		});
	}
	void addTask() {
		task.setVisible(true);
		date.setVisible(true);
		time.setVisible(true);
		tsk.setVisible(true);
		hrs.setVisible(true);
		min.setVisible(true);
		sec.setVisible(true);
		day.setVisible(true);
		mon.setVisible(true);
		yrs.setVisible(true);
		save.setVisible(true);

		hrs.addChangeListener(new ChangeListener() {      
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				String s = String.format("%02d", hrs.getValue());
				if ( Integer.parseInt(s) == 24)
				{
					hrs.setValue(0);
				}
				else if (Integer.parseInt(s) == -1)
				{
					hrs.setValue(23);
				}
			}
		});
		min.addChangeListener(new ChangeListener() {      
			@Override
			public void stateChanged(ChangeEvent e) {
				String s1 = String.format("%02d", min.getValue());
				int s2 = Integer.parseInt(String.format("%02d", hrs.getValue()));
				if ( Integer.parseInt(s1) == 60)
				{
					min.setValue(0);
					hrs.setValue(s2+1);
				}
				else if (Integer.parseInt(s1) == -1)
				{
					min.setValue(59);
					hrs.setValue(s2-1);
				}
			}
		});
		sec.addChangeListener(new ChangeListener() {      
			@Override
			public void stateChanged(ChangeEvent e) {
				String s = String.format("%02d", sec.getValue());
				int s2 = Integer.parseInt(String.format("%02d", min.getValue()));
				if ( Integer.parseInt(s) == 60)
				{
					sec.setValue(0);
					min.setValue(s2+1);
				}
				else if (Integer.parseInt(s) == -1)
				{
					sec.setValue(59);
					min.setValue(s2-1);
				}
			}
		});
		day.addChangeListener(new ChangeListener() {      
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				String s = String.format("%02d", day.getValue());
				int s2 = Integer.parseInt(String.format("%02d", mon.getValue()));
				if (s2==1 || s2==3 || s2==5||s2==7||s2==8||s2==10||s2==12)
				{
					if ( Integer.parseInt(s) == 32)
					{
						day.setValue(1);
						mon.setValue(s2+1);
					}
				}
				else if (s2==4||s2==6||s2==9||s2==11)
				{
					if ( Integer.parseInt(s) == 31)
					{
						day.setValue(1);
						mon.setValue(s2+1);
					}
				}
				else
				{
					if ( Integer.parseInt(s) == 29)
					{
						day.setValue(1);
						mon.setValue(s2+1);
					}
				}
				if (s2==1||s2==2||s2==4||s2==6||s2==8||s2==9||s2==11)
				{
					if (Integer.parseInt(s) == 0)
					{
						day.setValue(31);
						mon.setValue(s2-1);
					}
				}
				else if (s2==5 || s2==7 || s2==10 || s2==12)
				{
					if (Integer.parseInt(s) == 0)
					{
						day.setValue(30);
						mon.setValue(s2-1);
					}
				}
				else
				{
					if (Integer.parseInt(s) == 0)
					{
						day.setValue(28);
						mon.setValue(s2-1);
					}
				}
			}
		});
		mon.addChangeListener(new ChangeListener() {      
			@Override
			public void stateChanged(ChangeEvent e) {
				String s = String.format("%02d", mon.getValue());
				int s2 = Integer.parseInt(String.format("%02d", yrs.getValue()));
				if ( Integer.parseInt(s) == 13)
				{
					mon.setValue(1);
					yrs.setValue(s2+1);
				}
				else if (Integer.parseInt(s) == 0)
				{
					mon.setValue(12);
					yrs.setValue(s2-1);
				}
			}
		});
	}
	void viewTask() {
		ta.setVisible(true);
		try{
			ResultSet rs = stmt.executeQuery("SELECT * FROM `mydb`.`tasklist`;");
			String str = String.format("\t%14s\t %10s\t %12s", "TASK", "TIME", "DATE");
			ta.setText(str);
			while (rs.next())
			{
				String t = rs.getString(2);
				String d = rs.getString(3);
				String tim = rs.getString(4);
				str = String.format("\n\t%14s\t %10s\t %12s", t, tim, d);
				ta.append(str);
			}
		}
		catch(Exception e1) {
			System.out.println(e1);
		}
	}
	void updateTask() {
		task.setVisible(true);
		tsk.setVisible(true);
		updt.setVisible(true);
	}
	void deleteTask() {
		task.setVisible(true);
		tsk.setVisible(true);
		dlt.setVisible(true);
	}
	void reset() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT DAY(CURDATE());");
			String str="";
			while(rs.next()) {
				str=rs.getString(1);
				day.setValue(Integer.parseInt(str));
			}
			rs = stmt.executeQuery("SELECT MONTH(CURDATE());");
			while(rs.next()) {
				str=rs.getString(1);
				mon.setValue(Integer.parseInt(str));
			}
			rs = stmt.executeQuery("SELECT YEAR(CURDATE());");
			while(rs.next()) {
				str=rs.getString(1);
				yrs.setValue(Integer.parseInt(str));
			}
		}
		catch(Exception e1) {
			System.out.println(e1);
		}

		task.setVisible(false);
		date.setVisible(false);
		time.setVisible(false);
		tsk.setVisible(false);
		hrs.setVisible(false);
		min.setVisible(false);
		sec.setVisible(false);
		day.setVisible(false);
		mon.setVisible(false);
		yrs.setVisible(false);
		save.setVisible(false);
		updt.setVisible(false);
		dlt.setVisible(false);
		save2.setVisible(false);
		ta.setVisible(false);
	}
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == view)
		{
			reset();
			viewTask();
		}
		else if (e.getSource() == add)
		{
			reset();
			addTask();
		}
		else if (e.getSource() == update)
		{
			reset();
			updateTask();
		}
		else if (e.getSource() == delete)
		{
			reset();
			deleteTask();
		}
		if (e.getSource() == save)
		{
			try{
				String tmp1 = String.format("%02d:%02d:%02d", hrs.getValue(), min.getValue(), sec.getValue());
				String tmp2 = String.format("%04d-%02d-%02d", yrs.getValue(), mon.getValue(), day.getValue());
				stmt.executeUpdate("INSERT INTO `mydb`.`tasklist`(`Task`,`Date`,`Time`)VALUES('"+
						tsk.getText()+"','"+tmp2+"','"+tmp1+"')");

			}
			catch(Exception e1) {
				System.out.println(e1);
			}

			tsk.setText("");
		}
		if (e.getSource() == dlt)
		{
			try{
				stmt.executeUpdate("DELETE FROM `mydb`.`tasklist` WHERE Task = '"+tsk.getText()+"';");
			}
			catch(Exception e1) {
				System.out.println(e1);
			}
			tsk.setText("");
		}
		if (e.getSource() == updt)
		{
			task.setVisible(true);
			date.setVisible(true);
			time.setVisible(true);
			tsk.setVisible(true);
			hrs.setVisible(true);
			min.setVisible(true);
			sec.setVisible(true);
			day.setVisible(true);
			mon.setVisible(true);
			yrs.setVisible(true);
			hrs.addChangeListener(new ChangeListener() {      
				@Override
				public void stateChanged(ChangeEvent e) 
				{
					String s = String.format("%02d", hrs.getValue());
					if ( Integer.parseInt(s) == 24)
					{
						hrs.setValue(0);
					}
					else if (Integer.parseInt(s) == -1)
					{
						hrs.setValue(23);
					}
				}
			});
			min.addChangeListener(new ChangeListener() {      
				@Override
				public void stateChanged(ChangeEvent e) {
					String s1 = String.format("%02d", min.getValue());
					int s2 = Integer.parseInt(String.format("%02d", hrs.getValue()));
					if ( Integer.parseInt(s1) == 60)
					{
						min.setValue(0);
						hrs.setValue(s2+1);
					}
					else if (Integer.parseInt(s1) == -1)
					{
						min.setValue(59);
						hrs.setValue(s2-1);
					}
				}
			});
			sec.addChangeListener(new ChangeListener() {      
				@Override
				public void stateChanged(ChangeEvent e) {
					String s = String.format("%02d", sec.getValue());
					int s2 = Integer.parseInt(String.format("%02d", min.getValue()));
					if ( Integer.parseInt(s) == 60)
					{
						sec.setValue(0);
						min.setValue(s2+1);
					}
					else if (Integer.parseInt(s) == -1)
					{
						sec.setValue(59);
						min.setValue(s2-1);
					}
				}
			});
			day.addChangeListener(new ChangeListener() {      
				@Override
				public void stateChanged(ChangeEvent e) 
				{
					String s = String.format("%02d", day.getValue());
					int s2 = Integer.parseInt(String.format("%02d", mon.getValue()));
					if (s2==1 || s2==3 || s2==5||s2==7||s2==8||s2==10||s2==12)
					{
						if ( Integer.parseInt(s) == 32)
						{
							day.setValue(1);
							mon.setValue(s2+1);
						}
					}
					else if (s2==4||s2==6||s2==9||s2==11)
					{
						if ( Integer.parseInt(s) == 31)
						{
							day.setValue(1);
							mon.setValue(s2+1);
						}
					}
					else
					{
						if ( Integer.parseInt(s) == 29)
						{
							day.setValue(1);
							mon.setValue(s2+1);
						}
					}
					if (s2==1||s2==2||s2==4||s2==6||s2==8||s2==9||s2==11)
					{
						if (Integer.parseInt(s) == 0)
						{
							day.setValue(31);
							mon.setValue(s2-1);
						}
					}
					else if (s2==5 || s2==7 || s2==10 || s2==12)
					{
						if (Integer.parseInt(s) == 0)
						{
							day.setValue(30);
							mon.setValue(s2-1);
						}
					}
					else
					{
						if (Integer.parseInt(s) == 0)
						{
							day.setValue(28);
							mon.setValue(s2-1);
						}
					}
				}
			});
			mon.addChangeListener(new ChangeListener() {      
				@Override
				public void stateChanged(ChangeEvent e) {
					String s = String.format("%02d", mon.getValue());
					int s2 = Integer.parseInt(String.format("%02d", yrs.getValue()));
					if ( Integer.parseInt(s) == 13)
					{
						mon.setValue(1);
						yrs.setValue(s2+1);
					}
					else if (Integer.parseInt(s) == 0)
					{
						mon.setValue(12);
						yrs.setValue(s2-1);
					}
				}
			});
			try{
				ResultSet rs = stmt.executeQuery("SELECT `tasklist`.`Task` FROM `mydb`.`tasklist` WHERE +"
						+ "Task = '" + tsk.getText() + "';");
				String str="";
				String str2="";
				while (rs.next())
					str = rs.getString(1);
				tsk.setText(str);
				rs = stmt.executeQuery("SELECT `tasklist`.`Date` FROM `mydb`.`tasklist` WHERE +"
						+ "Task = '" + tsk.getText() + "';");
				while (rs.next())
					str = rs.getString(1);
				rs = stmt.executeQuery("SELECT DAY('"+ str +"');");
				while(rs.next()) {
					str2=rs.getString(1);
					day.setValue(Integer.parseInt(str2));
				}
				rs = stmt.executeQuery("SELECT MONTH('"+ str +"');");
				while(rs.next()) {
					str2=rs.getString(1);
					mon.setValue(Integer.parseInt(str2));
				}
				rs = stmt.executeQuery("SELECT YEAR('"+ str +"');");
				while(rs.next()) {
					str2=rs.getString(1);
					yrs.setValue(Integer.parseInt(str2));
				}
				rs = stmt.executeQuery("SELECT `tasklist`.`Time` FROM `mydb`.`tasklist` WHERE +"
						+ "Task = '" + tsk.getText() + "';");
				while (rs.next())
					str = rs.getString(1);
				rs = stmt.executeQuery("SELECT HOUR('"+ str +"');");
				while(rs.next()) {
					str2=rs.getString(1);
					hrs.setValue(Integer.parseInt(str2));
				}
				rs = stmt.executeQuery("SELECT MINUTE('"+ str +"');");
				while(rs.next()) {
					str2=rs.getString(1);
					min.setValue(Integer.parseInt(str2));
				}
				rs = stmt.executeQuery("SELECT SECOND('"+ str +"');");
				while(rs.next()) {
					str2=rs.getString(1);
					sec.setValue(Integer.parseInt(str2));
				}
			}
			catch(Exception e1) {
				System.out.println(e1);
			}
			updt.setVisible(false);
			save2.setVisible(true);
			tmp = tsk.getText();
		}
		if (e.getSource() == save2)
		{
//			UPDATE `mydb`.`tasklist`
			//			SET
			//			`index` = ,
			//			`Task` = ,
			//			`Date` = ,
			//			`Time` = 
			//			WHERE `index` = ;
			try{
				String tmp1 = String.format("%02d:%02d:%02d", hrs.getValue(), min.getValue(), sec.getValue());
				String tmp2 = String.format("%04d-%02d-%02d", yrs.getValue(), mon.getValue(), day.getValue());
				String str = "UPDATE `mydb`.`tasklist` SET `Task` = '" + tsk.getText() 
				+ "', `Date` = '"+ tmp2 +"', `Time` = '"+tmp1 + "' WHERE `Task` ='"+
				tmp + "';";
				System.out.println(str);
				stmt.executeUpdate(str);
			}
			catch(Exception e1) {
				System.out.println(e1);
			}
			tsk.setText("");
		}
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}

public class Hackathon2 {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ToDoList td = new ToDoList();
	}
}
