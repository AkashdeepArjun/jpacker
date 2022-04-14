package home_test;
import home.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class TestMsiUi {
    

    public static void main(String[] args){

        SpringLayout parent_layout=new SpringLayout();
        JFrame main_win = new JFrame();
        Container c= main_win.getContentPane();
        MsiPackUi msi_pack_ui = new MsiPackUi(main_win);
        
        c.setLayout(new BorderLayout());
        c.add(msi_pack_ui,BorderLayout.CENTER);
        main_win.setSize(1500,700);
        main_win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_win.setTitle("Test Msi build UI");
        main_win.setVisible(true);

    }
    
}
