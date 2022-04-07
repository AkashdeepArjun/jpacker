package home;
import java.awt.*;
import java.awt.event.*;

public class AppPackOptions extends Panel{

    FlowLayout root_layout;
    CheckboxGroup app_pack_options;
    Checkbox option_platform_independant_jar,option_windows_exe;
    CardLayout container_reference;


    public AppPackOptions(CardLayout container_reference,Panel parent){
    this.container_reference=container_reference;
    root_layout = new FlowLayout();
    setLayout(root_layout);
    app_pack_options = new CheckboxGroup();
    option_platform_independant_jar=new Checkbox("Platform Independent(Jar file)",true,app_pack_options);
    option_windows_exe=new Checkbox("Windows Msi image(exe file)",false,app_pack_options);
    add(option_platform_independant_jar);
    add(option_windows_exe);

    option_platform_independant_jar.addItemListener(new ItemListener(){

        public void itemStateChanged(ItemEvent e){
            if(e.getStateChange()==1){
                container_reference.show(parent,"jar");
            }

        }
        

    });
    option_windows_exe.addItemListener(new ItemListener(){

        public void itemStateChanged(ItemEvent e){
            if(e.getStateChange()==1){
                container_reference.show(parent, "msi");
            }
        }

    });
    // setBackground(Color.LIGHT_GRAY);

}

}
