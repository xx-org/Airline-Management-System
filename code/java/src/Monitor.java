//Usually you will require both swing and awt packages
// even if you are working with just swings.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Monitor {
    static JPanel Tech_Panel = new JPanel();
    static JLabel Label_1 = new JLabel("");
    static JTextField Text_1 = new JTextField(15);
    static JButton Enter_button = new JButton("Enter");
    static JLabel Label_2 = new JLabel("");
    static JTextField Text_2=new JTextField(15);
    static JLabel Label_3 = new JLabel("");
    static JTextField Text_3=new JTextField(15);
    static JLabel Label_4 = new JLabel("");
    static JTextField Text_4=new JTextField(15);
    static JLabel Label_5 = new JLabel("");
    static JTextField Text_5=new JTextField(15);
    static JLabel Label_6 = new JLabel("");
    static JTextField Text_6=new JTextField(15);
    static JLabel Label_7 = new JLabel("");
    static JTextField Text_7=new JTextField(15);
    static JLabel Label_8 = new JLabel("");
    static JTextField Text_8=new JTextField(15);
    static JLabel Label_9 = new JLabel("");
    static JTextField Text_9=new JTextField(15);
    static JLabel Label_10 = new JLabel("");
    static JTextField Text_10=new JTextField(15);
    static JLabel Output_Label = new JLabel("");
    static JPanel Date_Panel = new JPanel();
    static JTextField year_Text = new JTextField(4);
    static JTextField month_Text = new JTextField(2);
    static JTextField day_Text = new JTextField(2);
    static JPanel Date_Panel2 = new JPanel();
    static JTextField year_Text2 = new JTextField(4);
    static JTextField month_Text2 = new JTextField(2);
    static JTextField day_Text2 = new JTextField(2);
    
    public static void main(String args[]) {
        Date_Panel.setLayout(new FlowLayout());
        Date_Panel.add(new JLabel("Day: "));
        Date_Panel.add(day_Text);
        Date_Panel.add(new JLabel("Month: "));
        Date_Panel.add(month_Text);
        Date_Panel.add(new JLabel("Year: "));
        Date_Panel.add(year_Text);
        Date_Panel2.setLayout(new FlowLayout());
        Date_Panel2.add(new JLabel("Day: "));
        Date_Panel2.add(day_Text2);
        Date_Panel2.add(new JLabel("Month: "));
        Date_Panel2.add(month_Text2);
        Date_Panel2.add(new JLabel("Year: "));
        Date_Panel2.add(year_Text2);
        JFrame frame = new JFrame("Airline Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,700);

        JPanel Manu_panel = new JPanel();
        JLabel Manu_label = new JLabel("Please select the option you want by clicking the button below: ");
        JButton AddPlane_button = new JButton("Add Plane");
        JButton AddFlight_button = new JButton("Add Flight");
        JButton AddPilot_button = new JButton("Add Pilot");
        JButton AddTechnician_button = new JButton("Add Technician");
        JButton BookFlight_button = new JButton("Book Flight");
        JButton ListNumberOfAvailableSeats_button = new JButton("ListNumberOfAvailableSeats");
        JButton ListsTotalNumberOfRepairsPerPlane_button = new JButton("ListsTotalNumberOfRepairsPerPlane");
        JButton ListTotalNumberOfRepairsPerYear_button = new JButton("ListTotalNumberOfRepairsPerYear");
        JButton FindPassengersCountWithStatus_button = new JButton("FindPassengersCountWithStatus");

        //For ADDTECHNICIAN
        //Tech_Panel.setBorder(BorderFactory.createEmptyBorder(0, 150,150, 150));
        Tech_Panel.setBounds(200,200,200,200);
        Tech_Panel.setVisible(false);
        AddTechnician_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                Tech_Panel.setVisible(false);
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
                Tech_Panel.removeAll();
                Tech_Panel.add(Label_1);
                Label_1.setText("Please enter the First name");
                Tech_Panel.add(Text_1);
                Tech_Panel.add(Label_2);
                Label_2.setText("Please enter the Last name");
                Tech_Panel.add(Text_2);
                Enter_button.addActionListener(new ActionListener(){ 
                    public void actionPerformed(ActionEvent e){ 
                Output_Label.setText(Text_1.getText()+Text_2.getText());}});
                Tech_Panel.add(Enter_button);
                
                Tech_Panel.add(Output_Label);
                Tech_Panel.setVisible(true);
            }  
        });

        AddPlane_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                Tech_Panel.setVisible(false);
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
                Tech_Panel.removeAll();
                Tech_Panel.add(Label_1);
                Label_1.setText("Please enter the make");
                Tech_Panel.add(Text_1);
                Tech_Panel.add(Label_2);
                Label_2.setText("Please enter the model");
                Tech_Panel.add(Text_2);
                Tech_Panel.add(Label_3);
                Label_3.setText("Please enter the age");
                Tech_Panel.add(Text_3);

                Tech_Panel.add(Label_4);
                Label_4.setText("Please enter the seats");
                Tech_Panel.add(Text_4);
                Tech_Panel.add(Enter_button);
                Tech_Panel.add(Output_Label);
                Tech_Panel.setVisible(true);
            }  
        });

        AddFlight_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                Tech_Panel.setVisible(false);
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
                Tech_Panel.removeAll();
                Tech_Panel.add(Label_1);
                Label_1.setText("Please enter the cost");
                Tech_Panel.add(Text_1);
                Tech_Panel.add(Label_2);
                Label_2.setText("Please enter the num sold");
                Tech_Panel.add(Text_2);
                Tech_Panel.add(Label_3);
                Label_3.setText("Please enter the num stop");
                Tech_Panel.add(Text_3);

                Tech_Panel.add(Label_4);
                Label_4.setText("Please enter the seats");
                Tech_Panel.add(Text_4);
                Tech_Panel.add(Label_5);
                Label_5.setText("Please enter the actual departure date");
                Tech_Panel.add(Date_Panel);
                Tech_Panel.add(Label_6);
                Label_6.setText("Please enter the actual arrival date");
                Tech_Panel.add(Date_Panel2);
                Tech_Panel.add(Label_7);
                Label_7.setText("Please enter the arrival airport");
                Tech_Panel.add(Text_7);
                Tech_Panel.add(Label_8);
                Label_8.setText("Please enter the departure airport");
                Tech_Panel.add(Text_8);
                Tech_Panel.add(Label_9);
                Label_9.setText("Please enter the pilot id");
                Tech_Panel.add(Text_9);
                Tech_Panel.add(Label_10);
                Label_10.setText("Please enter the plane id");
                Tech_Panel.add(Text_10);
                Tech_Panel.add(Enter_button);
                Tech_Panel.add(Output_Label);
                Tech_Panel.setVisible(true);
            }  
        });

        AddPilot_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                Tech_Panel.setVisible(false);
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
                Tech_Panel.removeAll();
                Tech_Panel.add(Label_1);
                Label_1.setText("Please enter the First name");
                Tech_Panel.add(Text_1);
                Tech_Panel.add(Label_2);
                Label_2.setText("Please enter the Last name");
                Tech_Panel.add(Text_2);
                Tech_Panel.add(Label_3);
                Label_3.setText("Please enter the nationality");
                Tech_Panel.add(Text_3);

                Tech_Panel.add(Enter_button);
                Tech_Panel.add(Output_Label);
                Tech_Panel.setVisible(true);
            }  
        });

        BookFlight_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                Tech_Panel.setVisible(false);
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
                Tech_Panel.removeAll();
                Tech_Panel.add(Label_1);
                Label_1.setText("Please enter the customer id");
                Tech_Panel.add(Text_1);
                Tech_Panel.add(Label_2);
                Label_2.setText("Please enter the flight id");
                Tech_Panel.add(Text_2);

                Tech_Panel.add(Enter_button);
                Tech_Panel.add(Output_Label);
                Tech_Panel.setVisible(true);
            }  
        });
        ListNumberOfAvailableSeats_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){ 
                Tech_Panel.setVisible(false); 
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
                Tech_Panel.removeAll();
                Tech_Panel.add(Label_1);
                Label_1.setText("Please enter the flight number: ");
                Tech_Panel.add(Text_1);
                Tech_Panel.add(Label_2);
                Label_2.setText("Please enter the actual departure date");
                Tech_Panel.add(Date_Panel);
                Tech_Panel.add(Enter_button);
                Tech_Panel.add(Output_Label);
                Tech_Panel.setVisible(true);
            }  
        });
        ListsTotalNumberOfRepairsPerPlane_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  

                Tech_Panel.setVisible(false);
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
                Tech_Panel.removeAll();

                Tech_Panel.add(Output_Label);
                Tech_Panel.setVisible(true);
            }  
        });
        FindPassengersCountWithStatus_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  

                Tech_Panel.setVisible(false);
                Tech_Panel.removeAll();
                JButton W_button = new JButton("W");
                JButton C_button = new JButton("C");
                JButton R_button = new JButton("R");
                Tech_Panel.setLayout(new FlowLayout());
                Tech_Panel.add( W_button);
                Tech_Panel.add(C_button);
                Tech_Panel.add(R_button);
                
                Tech_Panel.add(Output_Label);
                Tech_Panel.setVisible(true);
            }  
        });
        ListTotalNumberOfRepairsPerYear_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){ 
                 
                Tech_Panel.setVisible(false);
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
                Tech_Panel.removeAll();
                Tech_Panel.add(Output_Label);
                Tech_Panel.setVisible(true);
            }  
        });
        Manu_panel.add(AddPlane_button);
        Manu_panel.add(AddFlight_button);
        Manu_panel.add(AddPilot_button);
        Manu_panel.add(AddTechnician_button);
        Manu_panel.add(BookFlight_button);
        Manu_panel.add(ListNumberOfAvailableSeats_button);
        Manu_panel.add(ListsTotalNumberOfRepairsPerPlane_button);
        Manu_panel.add(ListTotalNumberOfRepairsPerYear_button);
        Manu_panel.add(FindPassengersCountWithStatus_button);
        frame.getContentPane().add(BorderLayout.NORTH, Manu_label);
        frame.getContentPane().add(BorderLayout.CENTER,Manu_panel);
        //frame.getContentPane().add(BorderLayout.SOUTH,Tech_Panel);
        Manu_panel.add(Tech_Panel);
        frame.setVisible(true);

    }


}