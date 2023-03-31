/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ar;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author USER
 */
public class Ar implements ActionListener {
    
    String email, tbl_pword;
    JTable t0;
    JTree t;
    int sel;
    JScrollPane u, tp, p;
    JLabel l = new JLabel();
    JFrame j = new JFrame();
    JButton ca = new JButton("(+) TRANS.");
    JLabel amount = new JLabel("amount: $");
    JTextField amt = new JTextField();
    JLabel amount0 = new JLabel("note: ");
    JTextField amt0 = new JTextField();
    JButton add = new JButton("create");
    JButton can = new JButton("cancel");
    ButtonGroup bg = new ButtonGroup();
    JRadioButton bgs = new JRadioButton();
    JRadioButton bge = new JRadioButton();
    //ApArTableModel apar=new ApArTableModel();

    static class ApArTableModel extends DefaultTableModel {
        
        ArrayList<Color> rowColours = (ArrayList) Arrays.asList(
                Color.RED,
                Color.GREEN,
                Color.CYAN
        );
        
        public void setRowColour(int row, Color c) {
            rowColours.set(row, c);
            fireTableRowsUpdated(row, row);
        }
        
        public Color getRowColour(int row) {
            return rowColours.get(row);
        }
        
        @Override
        public int getRowCount() {
            return 3;
        }
        
        @Override
        public int getColumnCount() {
            return 3;
        }
        
        @Override
        public Object getValueAt(int row, int column) {
            return String.format("%d %d", row, column);
        }
    }

    /**
     * @author suhas, orwellophile
     *
     */
    private class CustomCellRenderer extends DefaultTableCellRenderer {

        /**
         * @see
         * javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(JTable,
         * Object, boolean, boolean, int, int)
         */
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            Component rendererComp = super.getTableCellRendererComponent(table, value,
                    isSelected, hasFocus, row, column);
            
            double an = Double.parseDouble(table.getValueAt(row, 0) + "");

            // Very important to handle selected items (render them inversely colored)
            if (row % 2 == 0) {
                rendererComp.setBackground(Color.lightGray);
                rendererComp.setForeground(Color.black);
            } else {
                rendererComp.setBackground(Color.PINK);
                rendererComp.setForeground(Color.black);
            }
            
            if (isSelected) {
                //rendererComp.setBackground(Color.YELLOW);
                rendererComp.setForeground(Color.GREEN);
                
            }
            
            if (an < 0) {
                rendererComp.setBackground(Color.red);
                rendererComp.setForeground(Color.black);
                
            }
            
            return rendererComp;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        j.remove(amount);
        j.remove(amt);
        j.remove(amount0);
        j.remove(amt0);
        j.remove(add);
        j.remove(can);
        j.remove(bgs);
        j.remove(bge);
        
        try {
            int a = -1;
            if (bgs.isSelected()) {
                a = 1;
            } else {
                a = -1;
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql3000.mochahost.com/himwepro_a", "himwepro_a", "yyyty");
            Statement st = conn.createStatement();
            //st.executeUpdate("create table ar (amount text,dat datetime, numb text)");
            //    st.executeUpdate("delete from ar_"+email.replace(".","_")+"");
            if (ins == 1) {
                st.executeUpdate("insert into ar_" + email.replace(".", "_") + " select '" + a * Double.parseDouble(amt.getText()) + "',now(),'" + amt0.getText() + "'");
                ins = 0;
                System.out.println("|");
            }
            ResultSet r0 = st.executeQuery("select amount, dat as daet, numb as invoice from ar_" + email.replace(".", "_") + " order by dat desc");
            Vector theRow = new Vector();
            Vector columns = null;
            double rt = 0.0;
            while (r0.next()) {
                ResultSetMetaData rsmd = r0.getMetaData();
                Vector currentRow = new Vector();
                columns = new Vector();
                // This gets the names of every column and stores in the columns vector
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    columns.addElement(rsmd.getColumnName(i));
                    currentRow.addElement(r0.getString(i));
                    if (rsmd.getColumnName(i).contains("amount")) {
                        if (r0.getString(i).equals("")) {
                            rt += 0;
                        } else {
                            rt += Double.parseDouble(r0.getString(i));
                        }
                    }
                } // end of for loop
// the data vector currentRow is inserted into another vector theRow ready for output.
                theRow.addElement(currentRow);

// Table created using the constructor JTable(data vector, column vector)
            }
            j.setTitle("total wealth: $" + rt);
            
            final CustomCellRenderer renderer = new CustomCellRenderer();
            t0 = new JTable(theRow, columns) {
                
                @Override
                public TableCellRenderer getCellRenderer(int row, int column) {
                    return renderer;
                }
                
            };

            //t0.setModel(apar);
            t0.setBounds(100, 100, 500, 350);
            p = new JScrollPane(t0);
            p.setBounds(100, 100, 500, 350);
            j.getContentPane().add(p, BorderLayout.EAST);
            t = new JTree(new String[]{"ALL A/R", "wealth: $" + rt, "Savings (AR)", "Expenses (AP)"});
            t.setBounds(0, 50, 100, 500);
            t.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                    TreeSelectionModel in = t.getSelectionModel();
                    boolean ann = in.isRowSelected(2);
                    boolean anne = in.isRowSelected(3);
                    
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection("jdbc:mysql://mysql3000.mochahost.com/himwepro_a", "himwepro_a", "yyyty");
                        Statement st = conn.createStatement();
                        //st.executeUpdate("create table ar (amount text,dat datetime, numb text)");
                        //st.executeUpdate("delete from ar_"+email.replace(".","_")+"");
                        String sq = "select amount, dat as daet, numb as invoice from ar_" + email.replace(".", "_") + "";
                        if (ann) {
                            sq = "select amount, dat as daet, numb as invoice from ar_" + email.replace(".", "_") + " where amount > 0";
                        }
                        if (anne) {
                            sq = "select amount, dat as daet, numb as invoice from ar_" + email.replace(".", "_") + " where amount < 0";
                        }
                        ResultSet r0 = st.executeQuery(sq);
                        Vector theRow = new Vector();
                        Vector columns = null;
                        double rt = 0.0;
                        while (r0.next()) {
                            ResultSetMetaData rsmd = r0.getMetaData();
                            Vector currentRow = new Vector();
                            columns = new Vector();
                            // This gets the names of every column and stores in the columns vector
                            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                                columns.addElement(rsmd.getColumnName(i));
                                currentRow.addElement(r0.getString(i));
                                if (rsmd.getColumnName(i).contains("amount")) {
                                    if (r0.getString(i).equals("")) {
                                        rt += 0;
                                    } else {
                                        rt += Double.parseDouble(r0.getString(i));
                                    }
                                }
                            } // end of for loop
// the data vector currentRow is inserted into another vector theRow ready for output.
                            theRow.addElement(currentRow);

// Table created using the constructor JTable(data vector, column vector)
                        }
                        j.remove(p);
                        t0 = new JTable(theRow, columns);
                        //t0.setBounds(120, 100, 500, 350);
                        p = new JScrollPane(t0);
                        //p.setBounds(200, 80, 420, 350);
                        j.getContentPane().add(p, BorderLayout.WEST);
                        
                        j.pack();
                        j.update(j.getGraphics());
                    } catch (Exception lp) {
                    }
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            u = new JScrollPane(t);
            u.setBounds(0, 0, 100, 500);
            j.getContentPane().add(u, BorderLayout.CENTER);
            t.setSelectionRow(0);
            ca.setBounds(0, 0, 100, 30);
            ca.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    j.remove(p);
                    j.remove(u);
                    j.remove(ca);
                    can.setBounds(50, 340, 300, 70);
                    j.getContentPane().add(can, BorderLayout.CENTER);
                    amount0.setBounds(50, 140, 300, 70);
                    j.getContentPane().add(amount0, BorderLayout.CENTER);
                    amt0.setBounds(190, 140, 100, 20);
                    j.getContentPane().add(amt0, BorderLayout.EAST);
                    amount.setBounds(300, 140, 300, 70);
                    j.getContentPane().add(amount, BorderLayout.CENTER);
                    amt.setBounds(410, 140, 100, 20);
                    j.getContentPane().add(amt, BorderLayout.EAST);
                    add.setBounds(410, 170, 100, 30);
                    add.addActionListener(Ar.this);
                    j.getContentPane().add(add, BorderLayout.SOUTH);
                    j.update(j.getGraphics());
                }
            });
            j.getContentPane().add(ca, BorderLayout.CENTER);
            
        } catch (Exception mm) {
            mm.printStackTrace();
        }
        j.show();
        j.update(j.getGraphics());
    }
    int ins = 0;
    
    public Ar(String mail, String pass) {
        email = mail;
        tbl_pword = pass;
        j.setLayout(new BorderLayout());
        l.setBounds(0, 0, 1000, 50);
        j.setBounds(0, 0, 1000, 500);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
            File pathToFile = new File("him.png");
            Image image = ImageIO.read(pathToFile);
            l.setIcon(new ImageIcon(image));
            //j.getContentPane().add(l, BorderLayout.NORTH);

        } catch (Exception u) {
            u.printStackTrace();
        }
        
        ca.setBounds(0, 0, 100, 30);
        j.getContentPane().add(ca, BorderLayout.CENTER);
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql3000.mochahost.com/himwepro_a", "himwepro_a", "yyyty");
            Statement st = conn.createStatement();
            //st.executeUpdate("create table ar (amount text,dat datetime, numb text)");
            //st.executeUpdate("delete from ar_"+email.replace(".","_")+"");
            ResultSet r0 = st.executeQuery("select amount, dat as daet, numb as invoice from ar_" + email.replace(".", "_").replace("@", "_at_") + " order by dat desc");
            Vector theRow = new Vector();
            Vector columns = null;
            double rt = 0.0;
            while (r0.next()) {
                ResultSetMetaData rsmd = r0.getMetaData();
                Vector currentRow = new Vector();
                columns = new Vector();
                // This gets the names of every column and stores in the columns vector
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    columns.addElement(rsmd.getColumnName(i));
                    currentRow.addElement(r0.getString(i));
                    if (rsmd.getColumnName(i).contains("amount")) {
                        if (r0.getString(i).equals("")) {
                            rt += 0;
                        } else {
                            rt += Double.parseDouble(r0.getString(i));
                        }
                    }
                } // end of for loop
// the data vector currentRow is inserted into another vector theRow ready for output.
                theRow.addElement(currentRow);

// Table created using the constructor JTable(data vector, column vector)
            }
            j.setTitle("total wealth: $" + rt);
            
            final CustomCellRenderer renderer = new CustomCellRenderer();
            t0 = new JTable(theRow, columns) {
                
                @Override
                public TableCellRenderer getCellRenderer(int row, int column) {
                    return renderer;
                }
                
            };
            
            t0.setBounds(100, 100, 500, 350);
            try {
                j.remove(p);
            } catch (Exception kj) {
            }
            /*            for(int ii=0;ii<apa.getRowCount();ii++){
                for(int yy=0;yy<apa.getColumnCount();yy++){
                    try{
                        double ant=(double)apa.getValueAt(ii, yy);
                        if(ant<0)
                            apa.setRowColour(ii, Color.red);
                        else
                            apa.setRowColour(ii, Color.green);
                    }catch(Exception n){n.printStackTrace();}
                }
            }*/
            p = new JScrollPane(t0);
            p.setBounds(100, 100, 500, 350);
            j.getContentPane().add(p, BorderLayout.EAST);
            t = new JTree(new String[]{"A/R", "wealth: $" + rt, "Savings (AR)", "Expenses (AP)"});
            t.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                    TreeSelectionModel in = t.getSelectionModel();
                    boolean ann = in.isRowSelected(2);
                    boolean anne = in.isRowSelected(3);
                    
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection("jdbc:mysql://mysql3000.mochahost.com/himwepro_a", "himwepro_a", "yyyty");
                        Statement st = conn.createStatement();
                        //st.executeUpdate("create table ar (amount text,dat datetime, numb text)");
                        //st.executeUpdate("delete from ar_"+email.replace(".","_")+"");
                        String sq = "select amount, dat as daet, numb as invoice from ar_" + email.replace(".", "_") + "";
                        if (ann) {
                            sq = "select amount, dat as daet, numb as invoice from ar_" + email.replace(".", "_") + " where amount > 0";
                        }
                        if (anne) {
                            sq = "select amount, dat as daet, numb as invoice from ar_" + email.replace(".", "_") + " where amount < 0";
                        }
                        ResultSet r0 = st.executeQuery(sq);
                        Vector theRow = new Vector();
                        Vector columns = null;
                        double rt = 0.0;
                        while (r0.next()) {
                            ResultSetMetaData rsmd = r0.getMetaData();
                            Vector currentRow = new Vector();
                            columns = new Vector();
                            // This gets the names of every column and stores in the columns vector
                            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                                columns.addElement(rsmd.getColumnName(i));
                                currentRow.addElement(r0.getString(i));
                                if (rsmd.getColumnName(i).contains("amount")) {
                                    rt += Double.parseDouble(r0.getString(i));
                                }
                            } // end of for loop
// the data vector currentRow is inserted into another vector theRow ready for output.
                            theRow.addElement(currentRow);

// Table created using the constructor JTable(data vector, column vector)
                        }
                        j.remove(p);
                        final CustomCellRenderer renderer = new CustomCellRenderer();
                        t0 = new JTable(theRow, columns) {
                            
                            @Override
                            public TableCellRenderer getCellRenderer(int row, int column) {
                                return renderer;
                            }
                            
                        };
                        
                        t0.setBounds(100, 0, 500, 350);
                        p = new JScrollPane(t0);
                        p.setBounds(200, 0, 420, 350);
                        j.getContentPane().add(p, BorderLayout.EAST);
                        j.update(j.getGraphics());
                    } catch (Exception lp) {
                    }
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            t.setBounds(0, 50, 100, 500);
            u = new JScrollPane(t);
            u.setBounds(0, 0, 100, 500);
            j.getContentPane().add(u, BorderLayout.WEST);
            t.setSelectionRow(0);
            
            ca.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        ins = 1;
                        //j.remove(p);
                        //j.remove(u);
                        //j.remove(ca);
                    } catch (Exception lj) {
                    }
                    JFrame q = new JFrame();
                    q.setLayout(null);
                    q.setBounds(100, 100, 500, 400);
                    JLabel him2 = new JLabel();
                    him2.setBounds(0, 0, 500, 70);
                    q.getContentPane().add(him2);
                    try {
                        File pathToFile = new File("acc.jpg");
                        
                        Image image = ImageIO.read(pathToFile);
                        him2.setIcon(new ImageIcon(image));
                        
                    } catch (Exception uk) {
                        uk.printStackTrace();
                    }
                    
                    can.setBounds(150, 240, 300, 70);
                    can.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            ins = 0;
                            q.dispose();
                            //Ar.this.actionPerformed(e);
                        }
                    });
                    
                    bgs.setText("Savings");
                    bgs.setBounds(50, 310, 100, 30);
                    bgs.setSelected(true);
                    
                    bge.setText("Expenses");
                    bge.setBounds(160, 310, 100, 30);
                    
                    q.getContentPane().add(bgs, BorderLayout.CENTER);
                    q.getContentPane().add(bge, BorderLayout.CENTER);
                    q.getContentPane().add(can, BorderLayout.CENTER);
                    bg.add(bgs);
                    bg.add(bge);
                    
                    amount0.setBounds(150, 140, 300, 70);
                    q.getContentPane().add(amount0, BorderLayout.CENTER);
                    amt0.setBounds(190, 170, 100, 20);
                    q.getContentPane().add(amt0, BorderLayout.EAST);
                    amount.setBounds(300, 140, 300, 70);
                    q.getContentPane().add(amount, BorderLayout.CENTER);
                    amt.setBounds(410, 140, 100, 20);
                    q.getContentPane().add(amt, BorderLayout.EAST);
                    add.setBounds(410, 170, 100, 30);
                    add.addActionListener(Ar.this);
                    q.getContentPane().add(add, BorderLayout.SOUTH);
                    ///q.update(j.getGraphics());
                    q.show();
                }
            });
            j.pack();
            j.setVisible(true);
            st.close();
            r0.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        j.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JFrame login2 = new JFrame();
        login2.setBounds(100, 100, 500, 500);
        login2.setLayout(new GridBagLayout());
        
        JLabel him2 = new JLabel();
        login2.getContentPane().add(him2);
        try {
            File pathToFile = new File("acc.jpg");
            
            Image image = ImageIO.read(pathToFile);
            him2.setIcon(new ImageIcon(image));
            
            login2.pack();
            login2.show();
            Thread.sleep(5000);
            login2.dispose();
        } catch (Exception uk) {
            uk.printStackTrace();
        }
        
        JFrame login = new JFrame();
        login.setBounds(100, 100, 500, 500);
        login.setLayout(new GridBagLayout());
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        login.setTitle("Transactions login form");
        
        JLabel him = new JLabel();
        login.getContentPane().add(him);
        try {
            File pathToFile = new File("him.png");
            
            Image image = ImageIO.read(pathToFile);
            him.setIcon(new ImageIcon(image));
            
        } catch (Exception u) {
            u.printStackTrace();
        }
        
        JButton acc = new JButton("Forgot Access?");
        login.getContentPane().add(acc);
        
        JLabel fr = new JLabel("emergency email:");
        JFrame fg = new JFrame();
        fg.setBounds(100, 100, 500, 500);
        fg.setLayout(new GridBagLayout());
        JTextField d = new JTextField();
        d.setText("        ");
        d.setSize(400, 40);
        JButton w = new JButton("Send Access");
        fg.getContentPane().add(fr);
        fg.getContentPane().add(d);
        fg.getContentPane().add(w);
        JButton w2 = new JButton("FREE Sign Up");
        fg.getContentPane().add(w2);
        w2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Events();
            }
        });
        fg.pack();
        acc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                fg.show();
            }
        });
        
        JLabel ei = new JLabel("        email:    ");
        login.getContentPane().add(ei);
        JTextField em = new JTextField();
        login.getContentPane().add(em);
        em.setText("Email              ");
        em.setSize(400, 20);
        em.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                em.setText("");
                login.update(login.getGraphics());
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                return;
            }
        });
        
        JLabel p = new JLabel("      password:   ");
        login.getContentPane().add(p);
        JTextField pw = new JTextField();
        login.getContentPane().add(pw);
        pw.setText("Password                  ");
        pw.setSize(100, 20);
        pw.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                pw.setText("");
                login.update(login.getGraphics());
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
        
        JButton li = new JButton("login");
        login.getContentPane().add(li);
        login.pack();
        FlatLightLaf.setup();
        login.show();
        
        li.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://mysql3000.mochahost.com/himwepro_a", "himwepro_a", "yyyty");
                    Statement st = conn.createStatement();
                    //st.executeUpdate("create table ar (amount text,dat datetime, numb text)");
                    //st.executeUpdate("delete from ar_"+email.replace(".","_")+"");
                    String sq = "select pword from enrolledforevent where pword='" + pw.getText() + "' and email='" + em.getText() + "'";
                    ResultSet rs = st.executeQuery(sq);
                    if (rs.next()) {
                        login.dispose();
                        new Ar(em.getText(), pw.getText());
                    }
                } catch (Exception p) {
                }
            }
        });
        
    }
    
}
