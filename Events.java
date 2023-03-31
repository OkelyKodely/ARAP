/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author USER
 */
public class Events {

    JFrame j = new JFrame("Event");
    JButton backtn;
    String email, pword;

    public Events() {

        j.setLayout(null);

        ///j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setBounds(100, 10, 600, 700);

        JLabel k = new JLabel();
        j.getContentPane().add(k);
        k.setBounds(0, 0, 600, 80);

        JLabel k0 = new JLabel();
        j.getContentPane().add(k0);
        k0.setBounds(0, 90, 600, 200);

        JLabel lb = new JLabel("Accounting Sign Up");
        j.getContentPane().add(lb);
        lb.setBounds(40, 70, 500, 60);
        JLabel lbl0 = new JLabel("Accounting Access Sign Up");
        
        JLabel lbl = new JLabel("* showing.");
        JTextField emailTf = new JTextField();
        JLabel pwLblConfirm = new JLabel("Confirm password: ");
        JTextField pwTfConfirm = new JTextField();
        JLabel emailLbl = new JLabel("Put email: ");
        JLabel pwLbl = new JLabel("Write password: ");
        JTextField pwTf = new JTextField();

        JTextField phTf = new JTextField();
        JLabel phLbl = new JLabel("Phone number: ");
        JTextField addTf = new JTextField();
        JLabel addyLbl = new JLabel("Company Addr: ");

        JLabel zlbl = new JLabel("Enter Zip:");
        JTextField zTf = new JTextField();

        try {
            File pathToFile = new File("him.png");
            Image image = ImageIO.read(pathToFile);
            k.setIcon(new ImageIcon(image));
            
        } catch (Exception u) {
            u.printStackTrace();
        }

        JButton next0 = new JButton("NEXT");
        next0.setBounds(180, 500, 200, 40);
        next0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                email = emailTf.getText();
                pword = pwTfConfirm.getText();
                String p = pwTf.getText();
                lbl.setText("(Optional)");
                next0.setForeground(Color.blue);

                if (next0.getText().equals("NEXT")) {
                    if (email.equals("") || pword.equals("") || p.equals("") || !p.equals(pword)) {
                        new JFrame("Email, password, or retype missing or general error.").show();;
                        return;
                    }
                } else if (next0.getText().equals("Enroll")) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection("jdbc:mysql://mysql3000.mochahost.com/himwepro_a", "himwepro_a", "yyyty");
                        Statement st = conn.createStatement();
                        ResultSet rs = st.executeQuery("select email from enrolledforevent where email = '"+email+"'");
                        if(rs.next()){
                            j.setTitle("Account email exists, use another one.");
                            j.update(j.getGraphics());
                            return;
                        }
                        st.executeUpdate("insert into enrolledforevent (email, pword,addy, phone, z) values ('"+email+"','"+pword+"','"+addTf.getText()+"','"+phTf.getText()+"','"+zTf.getText()+"');");
                        st.executeUpdate("create table ar_"+email.replace(".", "_").replace("@", "_at_")+"(amount text, dat datetime, numb text);");
                        st.close();
                        conn.close();
                        j.setTitle("Enrolled SUCCESS!");
                        j.remove(backtn);
                        j.remove(next0);
                        lbl.setText("Enrolled.");
                        j.show();
                        j.update(j.getGraphics());
                    } catch (Exception i) {
                    }
                }

                next0.setText("Enroll");

                j.remove(k0);
                j.remove(lbl0);
                j.remove(lb);

                j.remove(emailLbl);
                j.remove(emailTf);
                j.remove(pwLbl);
                j.remove(pwTfConfirm);
                j.remove(pwLblConfirm);
                j.remove(pwTf);

                phLbl.setBounds(40, 100, 260, 40);
                phLbl.setFont(new Font("verdana", Font.PLAIN, 30) {
                });
                phTf.setBounds(340, 100, 200, 40);
                phTf.setFont(new Font("verdana", Font.PLAIN, 30) {
                });
                addyLbl.setBounds(40, 170, 260, 40);
                addyLbl.setFont(new Font("verdana", Font.PLAIN, 30) {
                });
                addTf.setBounds(340, 170, 200, 40);
                addTf.setFont(new Font("verdana", Font.PLAIN, 30) {
                });
                zlbl.setFont(new Font("verdana", Font.PLAIN, 30) {
                });
                zlbl.setBounds(40, 240, 200, 40);
                zTf.setBounds(340, 240, 200, 40);
                zTf.setFont(new Font("verdana", Font.PLAIN, 30) {
                });

                j.getContentPane().add(backtn);

                j.getContentPane().add(phLbl);
                j.getContentPane().add(phTf);

                j.getContentPane().add(addyLbl);
                j.getContentPane().add(addTf);

                j.getContentPane().add(zlbl);
                j.getContentPane().add(zTf);

                j.show();
                j.update(j.getGraphics());
            }
        });

        lbl0.setBounds(80, 170, 490, 80);
        lbl0.setFont(new Font("verdana", Font.PLAIN, 25) {
        });
        pwLbl.setBounds(120, 370, 130, 40);

        pwTf.setBounds(240, 370, 200, 40);
        pwTf.setFont(new Font("verdana", Font.PLAIN, 30) {
        });

        pwLblConfirm.setBounds(120, 440, 140, 40);

        pwTfConfirm.setBounds(240, 440, 200, 40);
        pwTfConfirm.setFont(new Font("verdana", Font.PLAIN, 30) {
        });

        lbl.setForeground(Color.red);
        lbl.setFont(new Font("verdana", Font.PLAIN, 15) {
        });
        lbl.setBounds(410, 570, 100, 30);

        emailLbl.setBounds(120, 300, 90, 40);

        emailTf.setBounds(240, 300, 200, 40);
        emailTf.setFont(new Font("verdana", Font.PLAIN, 30) {
        });

        j.getContentPane().add(emailLbl);
        j.getContentPane().add(emailTf);
        j.getContentPane().add(pwLblConfirm);
        j.getContentPane().add(pwTfConfirm);
        j.getContentPane().add(pwLbl);
        j.getContentPane().add(pwTf);
        j.getContentPane().add(next0);
        j.getContentPane().add(lbl);
        j.getContentPane().add(lbl0);

        backtn = new JButton("bACK");
        backtn.setBounds(20, 500, 100, 40);
        backtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	j.remove(addyLbl);
            	j.remove(addTf);            	
            	j.remove(phLbl);
            	j.remove(phTf);            	
            	j.remove(zlbl);
            	j.remove(zTf);            	            	
            	
            	j.getContentPane().add(lb);
               	j.getContentPane().add(lbl0);
                j.getContentPane().add(emailLbl);
                j.getContentPane().add(emailTf);
                j.getContentPane().add(pwLbl);
                j.getContentPane().add(pwTfConfirm);
                j.getContentPane().add(pwLblConfirm);
                j.getContentPane().add(pwTf);
                lbl.setText("* showing");
                next0.setText("NEXT");
                j.getContentPane().remove(backtn);
                email = "";
                pword = "";
                j.show();
                j.update(j.getGraphics());
            }
        });

        j.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//    	try {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection conn = DriverManager.getConnection("jdbc:mysql://mysql3000.mochahost.com/himwepro_a", "himwepro_a", "yyyty");
//        Statement st = conn.createStatement();
//        st.executeUpdate("alter table enrolledforevent add column z text;");
//        st.close();
//        conn.close();
//    	}catch(Exception u) {}
        new Events();
    }

}
