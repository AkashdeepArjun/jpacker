package home;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.security.auth.callback.TextInputCallback;
import java.util.*;
import java.util.List;

public class MsiPackUi extends Panel implements ActionListener{


    GridBagLayout root_layout;
    GridBagConstraints constraints;
    Frame frame_root;

    //      ui widgets
    private Checkbox is_app_modular;
     Label label_main_jar,label_dir_of_jar,label_app_name,label_vendor,label_icon,label_license,labe_copyright,label_status,status;
    TextField tf_main_jar,tf_main_jar_dir,tf_app_name,tf_vendor,tf_icon,tf_license,tf_copyrighTextField ;
    Button button_icon,button_license,button_generate_msi,button_set_main_jar;


    // File dialog to choose files

    FileDialog file_chooser;


    //Process Builder 

    private ProcessBuilder processBuilder;

    public MsiPackUi(Frame root_container){
            this.frame_root=root_container;
            root_layout=new GridBagLayout();
            constraints= new GridBagConstraints();
            setLayout(root_layout);
            setBackground(Color.decode("#fbf8cc"));
            initializeWidgets();
            arrangeWidgets();
            addListeners();
    }


    public void initializeWidgets(){
  
        label_main_jar= new Label("Select main jar file");
        tf_main_jar=new TextField(20);
        label_dir_of_jar=new Label("jar directory");
        tf_main_jar_dir=new TextField(20);
        label_app_name= new Label("set your app name");
        tf_app_name=new TextField(20);
        label_vendor=new Label("Vendor");
        tf_vendor=new TextField(20);
        label_icon=new Label("icon");
        tf_icon=new TextField(10);
        button_icon=new Button("Set icon");
        label_license=new Label("License");
        tf_license=new TextField(20);
        button_license=new Button("Set License");
        button_generate_msi=new Button("Generate msi file");
        labe_copyright=new Label("Copyright");
        tf_copyrighTextField=new TextField(20);
        button_set_main_jar=new Button("Set Main jar");
        label_status=new Label("STATUS:");
        status=new Label();
        processBuilder=new ProcessBuilder();
        file_chooser=new FileDialog(this.frame_root);

    }


    public void arrangeWidgets(){

        //first row
        arrange(label_main_jar,constraints,0,0,1,1,0,0);
        arrange(tf_main_jar,constraints,1,0,1,1,0,0);
        arrange(button_set_main_jar, constraints, 2,0,1,1,0,0);
        arrange(label_dir_of_jar,constraints,3,0,1,1,0,0);
        arrange(tf_main_jar_dir,constraints,4,0,1,1,0,0);
    
        //second row
        arrange(label_app_name,constraints,0,1,1,1,0,0);
        arrange(tf_app_name,constraints,1,1,1,1,0,0); 
        arrange(label_vendor, constraints, 3, 1,1,1,0,0);
        arrange(tf_vendor,constraints,4,1,1,1,0,0);
        
        
        //3rd row

        arrange(label_icon,constraints,0,2,1,1,0,0);
        arrange(tf_icon,constraints,1,2,1,1,0,0);
        arrange(button_icon,constraints,2,2,1,1,0,0);
        arrange(label_license,constraints,3,2,1,1,0,0);
        arrange(tf_license,constraints,4,2,1,1,0,0);
        arrange(button_license,constraints,5,2,1,1,0,0);


        //4th row
        arrange(labe_copyright,constraints,0,3,1,1,0,0);
        arrange(tf_copyrighTextField,constraints,1,3,1,1,0,0);


        //5th row

        arrange(button_generate_msi,constraints,3,5,1,1,0,0);
        arrange(label_status,constraints,4,5,1,1,0,0);
        arrange(status,constraints,5,5,1,1,0,0);
        status.setBackground(Color.WHITE);
    }





    public void arrange(Component comp,GridBagConstraints gc,int posx,int posy,int widthx,int widthy,int weightx,int weighty){
        gc.gridx=posx;
        gc.gridy=posy;
        gc.gridwidth=widthx;
        gc.gridheight=widthy;
        gc.weightx=weightx;
        gc.weighty=weighty;
        gc.fill=GridBagConstraints.BOTH;
        if(comp.getClass()==Label.class){
                Label.class.cast(comp).setAlignment(Label.RIGHT);
        }
        // gc.anchor=GridBagConstraints.EAST;
        gc.insets=new Insets(1,1,1,1);
        gc.ipady=10;
        gc.ipadx=5;
        add(comp,gc);
    }

        public void UpdateStatus(Label status_reference,String text,Color bg_color,Color fg_color){
            status_reference.setBackground(bg_color);
            status_reference.setForeground(fg_color);
            status_reference.setText(text);
        }

        public void addListeners(){
            button_set_main_jar.addActionListener(this);
            button_icon.addActionListener(this);
            button_license.addActionListener(this);
            button_generate_msi.addActionListener(this);
        }

        public void setMainJar(){
                file_chooser.setTitle("choose jar file");
                file_chooser.setVisible(true);
                String file_name=file_chooser.getFile();
                tf_main_jar.setText(file_name);
                String file_directory=file_chooser.getDirectory();
                tf_main_jar_dir.setText(file_directory);
        }

        public void setIcon(){
            file_chooser.setTitle("select .ico file format");
            file_chooser.setVisible(true);
            String file_name=file_chooser.getFile();
            String file_dir=file_chooser.getDirectory();
            String file_with_path=file_dir+file_name;
            tf_icon.setText(file_with_path);

        }

        public void setLicense(){
                file_chooser.setTitle("select license.txt file");
                file_chooser.setVisible(true);
                String f_name=file_chooser.getFile();
                String f_dir=file_chooser.getDirectory();
                String file_with_path=f_dir+f_name;
                tf_license.setText(file_with_path);
        }

        public void generate_msi(){


            File output_file= new File("output_msi");
            if(!output_file.exists()){
                output_file.mkdir();
            }
            String path=output_file.getAbsolutePath();
            MyUtils.log("EVENT FOLDER CREATED",output_file.getAbsolutePath());
            List<String> command=new ArrayList<String>();
            String app_name=tf_app_name.getText();
            String app_name_with_path =path+File.separator+app_name;
            MyUtils.log("APP WITH FULL PATH",app_name_with_path);
            String main_jar_file_name=tf_main_jar.getText();
            String icon=tf_icon.getText();
            String vendor=tf_vendor.getText();
            String license=tf_license.getText();
            String main_jar_dir=tf_main_jar_dir.getText();
            String copyright=tf_copyrighTextField.getText();
            if(main_jar_file_name.isEmpty() || main_jar_dir.isEmpty() ){

                MyUtils.log("EVENT EMPTY IMPORTANT FIELD","check jar file name or directory textfield is not empty");
                UpdateStatus(status, "ERROR", Color.decode("#e63946"),Color.decode("#f8f9fa"));
                status.setAlignment(Label.CENTER);
            }
            command.add("jpackage");
            command.add("--name");
            command.add(app_name);
            command.add("--input");
            command.add(main_jar_dir);
            if(!icon.isEmpty() || !icon.isBlank()){
            command.add("--icon");
            command.add(icon);
            }
            if(!vendor.isEmpty() || !vendor.isBlank()){
                command.add("--vendor");
                command.add(vendor);
            }
            if(!license.isEmpty() || !license.isBlank()){
                command.add("--license");
                command.add(license);
            }
            
            command.add("--main-jar");
            command.add(main_jar_file_name);

            try{
                final Process process=processBuilder.command(command).start();
                UpdateStatus(status, "STARTED", Color.decode("#9bf6ff"),Color.BLACK);
                status.setAlignment(Label.CENTER);
                
                while(process.isAlive()){
                    button_generate_msi.setEnabled(false);
                UpdateStatus(status, "WAIT..", Color.decode("#faa307"),Color.BLACK);
                status.setAlignment(Label.CENTER);
                }
                if(!process.isAlive()){
                    button_generate_msi.setEnabled(true);
                  UpdateStatus(status, "DONE", Color.decode("#caffbf"),Color.BLACK);
                status.setAlignment(Label.CENTER);

                }
            }catch(Exception e){
                MyUtils.log("EXCEPTION", e.getMessage());
                // UpdateStatus(status, "ERROR", Color.decode(""),Color.BLACK);
                UpdateStatus(status, "ERROR", Color.decode("#e63946"),Color.decode("#f8f9fa"));
                status.setAlignment(Label.CENTER);
                
            }



        }


        @Override
        public void actionPerformed(ActionEvent e) {
            Object source=e.getSource();
            if(source==button_set_main_jar){
                setMainJar();
            }else if(source==button_icon){
                setIcon();
            }
            else if(source==button_license){
                setLicense();
            }
            else if(source==button_generate_msi){
                generate_msi();
            }
            else{

            }
            
        }
}