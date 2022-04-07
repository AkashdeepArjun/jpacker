package home;

import java.awt.*;
import java.awt.event.*;

import javax.swing.BoxLayout;
import home.AppPackOptions;
import home.JarPackUi;
import home.MsiPackUi;

public class UiPackingApp extends Panel{
    
    Frame parent_window;
    BoxLayout root_layout;
    CardLayout pack_process_layout;
    Label demo_label;
    AppPackOptions appPackOptions;

                // uis 

    Panel container_pack_process;

    JarPackUi jarPackUi;
    MsiPackUi msiPackUi;

    public UiPackingApp(Frame parent_window){
        this.parent_window=parent_window;
        setBackground(Color.decode("#ccccff"));
        root_layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(root_layout);
        container_pack_process=new Panel();
        pack_process_layout=new CardLayout();
        container_pack_process.setLayout(pack_process_layout);
        jarPackUi = new JarPackUi(parent_window);
        msiPackUi = new MsiPackUi(parent_window);
        container_pack_process.add(jarPackUi,"jar");
        container_pack_process.add(msiPackUi,"msi");
        appPackOptions = new AppPackOptions(pack_process_layout,container_pack_process);
        add(appPackOptions);
        add(container_pack_process);
    }


}
