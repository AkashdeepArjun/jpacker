package home_test;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import home.MsiPackUi;
public class TestMsipack {

    public static void main(String[] args){

        Frame test_container=new Frame();
        test_container.setLayout(new BorderLayout());
        MsiPackUi msiPackUi = new MsiPackUi(test_container);
        msiPackUi.setUpFields("poplu.jar", "\\desktop", "appname", "vendor", "iconpath", "license here", "copyright");
        test_container.add(msiPackUi,BorderLayout.CENTER);
        test_container.setSize(800,900);
        test_container.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){System.exit(0);}
        });

        test_container.setVisible(true);



    }
    
    
}
