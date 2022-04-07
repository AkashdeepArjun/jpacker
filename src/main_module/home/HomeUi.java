package home;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.BoxLayout;
import home.UiPackingLib;
import home.UiPackingApp;


public class HomeUi extends Frame {
    
        // ui WIDGETS 

    Panel packing_options_panel,packing_process_panel;
    Label packing_options_label;
    CheckboxGroup packing_options;
    Checkbox option_jar,option_app;
    UiPackingLib ui_packing_lib;
    UiPackingApp ui_packing_app;



    //LAYOUTS 

    FlowLayout packing_options_panel_layout;
    BorderLayout root_layout;
    CardLayout packing_process_layout;
    
    public HomeUi(String title,int width,int height){

                    //root layout

          root_layout = new BorderLayout();


            //PACKING OPTIONS 

            packing_options_panel=new Panel();
            packing_options_panel_layout = new FlowLayout();
            packing_options_panel.setLayout(packing_options_panel_layout);
            packing_options_panel.setBackground(Color.LIGHT_GRAY);
            packing_options = new CheckboxGroup();
            packing_options_label=new Label("packing as");
            option_jar=new Checkbox("library",true,packing_options);
            option_app = new Checkbox("application",false,packing_options);
            packing_options_panel.add(packing_options_label);
            packing_options_panel.add(option_jar);
            packing_options_panel.add(option_app);


            // PACKING PROCESS PANEL
            packing_process_panel = new Panel();
            packing_process_layout=new CardLayout();
            packing_process_panel.setLayout(packing_process_layout);
            ui_packing_app = new UiPackingApp(this);
            ui_packing_lib=new UiPackingLib(this);
            packing_process_panel.add(ui_packing_lib,"lib");
            packing_process_panel.add(ui_packing_app,"app");

            setLayout(root_layout);
            option_jar.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent e){
                    if(e.getStateChange()==1){
                        packing_process_layout.show(packing_process_panel, "lib");
                    }else{
                        // packing_process_layout.show(packing_process_panel, "app");
                    }
                }
            });
            option_app.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent e){
                    if(e.getStateChange()==1){
                        packing_process_layout.show(packing_process_panel, "app");
                    }else{
                        // packing_process_layout.show(packing_process_panel, "app");
                    }
                }
            });

        
        add(packing_options_panel,BorderLayout.PAGE_START);  
        add(packing_process_panel,BorderLayout.CENTER);
        setTitle(title);
        
        setSize(new Dimension(width,height));

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){System.exit(0);}
        });
        
        setResizable(false);
        setVisible(true);


    }

    
}
