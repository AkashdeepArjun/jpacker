package home;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import javax.swing.WindowConstants;
// import main.ManifestCreator;
import javax.swing.text.DefaultStyledDocument.ElementSpec;
import java.lang.*;
import java.lang.Thread.State;
import java.net.*;
public class JarPackUi extends Panel implements ActionListener{

                //constants

                public final static DateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            

            private ProcessBuilder process_builder=new ProcessBuilder();
            Process process;
            GridBagLayout root_layout;
            GridBagConstraints constraints;
            Frame frame_root;

            //Sample label for testing 
            final Label welcome_message = new Label("JAR FILE MAKER ");
    

                // ui widgets 
                Checkbox is_app_modular;
                Label label_compiled_files_dir,label_manifest_file,label_output_file_name,label_output_file_dir,label_main_module_directory,
                label_main_module_dir,label_status,status;
                TextField tf_compiled_files,tf_manifest_file,tf_output_file_name,tf_output_file_dir,
                tf_main_module_dir;
                Button button_get_mf,button_generate_mf,button_compiled_classes,
                button_get_output_directory,generate_jar,button_main_module_dir,button_test_jar;
                


                        // file dialogs

                FileDialog file_and_directory_select_dialog=new FileDialog(frame_root,"title");

                JFileChooser j_file_and_dir_select_dialog;
                    // manifest creator 

                    String main_class_mf,cp;
                    Manifest manifest;
                    JarFile jarFile;

                    //Object 
                    Object instance;

                    // classs of external jars
                    Class<?> class_to_be_loaded;

            public JarPackUi(Frame root_container){
                    this.frame_root=root_container;
                    root_layout=new GridBagLayout();
                    constraints= new GridBagConstraints();
                    setLayout(root_layout);
                    setBackground(Color.decode("#d8e2dc"));
                    is_app_modular = new Checkbox("modular application");
                    label_compiled_files_dir=new Label("Compiled Files Dir");
                    tf_compiled_files=new TextField(20);
                    tf_manifest_file=new TextField(20);
                    button_compiled_classes = new Button("set compiled path dir");
                    label_manifest_file=new Label("manifest file");
                    button_get_mf=new Button("Get Manifest file");
                    button_generate_mf=new Button("Generate Manifest file");

                                //output file setup 
                                label_output_file_name=new Label("Output file name");
                                tf_output_file_name=new TextField(15);
                                label_output_file_dir=new Label("Output directory");
                                tf_output_file_dir=new TextField(15);
                                button_get_output_directory=new Button("Get Output Directory");
                                generate_jar=new Button("Generate Jar");

                                // main module directory setup
                                label_main_module_dir=new Label("Main Module directory");
                                tf_main_module_dir=new TextField(10);
                                button_main_module_dir=new Button("Set Main Module directory");

                                //test jar button setup
                                button_test_jar =new Button("Test Jar");

                                //file dialog setup
                                j_file_and_dir_select_dialog=new JFileChooser();

                                // status
                                label_status= new Label("STATUS:");
                                status = new Label("");

                    arrange(is_app_modular, constraints, 0, 0, 1, 1, 0,0);
                    arrange(label_compiled_files_dir,constraints,0,1,1,1,0,0);
                    arrange(tf_compiled_files,constraints,1,1,1,1,0,0);
                    arrange(button_compiled_classes, constraints, 2, 1, 1, 1,0,0);
                    arrange(label_manifest_file,constraints,0,2,1,1,0,0);
                    arrange(tf_manifest_file,constraints,1,2,1,1,0,0);
                    arrange(button_get_mf,constraints,2,2,1,1,0,0);
                    arrange(button_generate_mf, constraints, 3, 2, 1, 1,0, 0);

                    arrange(label_output_file_name, constraints, 0, 3, 1, 1, 0, 0);
                    arrange(tf_output_file_name,constraints,1,3,1,1,0,0);
                    arrange(label_output_file_dir,constraints,2,3,1,1,0,0);
                    arrange(tf_output_file_dir,constraints,3,3,1,1,0,0);
                    arrange(button_get_output_directory, constraints, 4, 3,1, 1, 0,0);
                    arrange(generate_jar,constraints,2,6,1,1,0,0);
                    arrange(button_test_jar,constraints,3,6,1,1,0,0);

                    arrange(label_main_module_dir,constraints,3,1,1,1,0,0);
                    arrange(tf_main_module_dir,constraints,4,1,1,1,0,0);
                    arrange(button_main_module_dir,constraints,5,1,1,1,0,0);

                    arrange(label_status,constraints,4,7,1,1,0,0);
                    arrange(status,constraints,5,7,1,1,0,0);
                        
                    initialize();
                    
                    is_app_modular.addItemListener(new ItemListener(){
                        public void itemStateChanged(ItemEvent e){
                            if(e.getStateChange()==1){
                                label_main_module_dir.setEnabled(true);
                                tf_main_module_dir.setEnabled(true);
                                button_main_module_dir.setEnabled(true);
                                
                                label_compiled_files_dir.setEnabled(false);
                                tf_compiled_files.setEnabled(false);
                                button_compiled_classes.setEnabled(false);

                            }else{

                                label_main_module_dir.setEnabled(false);
                                tf_main_module_dir.setEnabled(false);
                                button_main_module_dir.setEnabled(false);


                                label_compiled_files_dir.setEnabled(true);
                                tf_compiled_files.setEnabled(true);
                                button_compiled_classes.setEnabled(true);


                            }
                        }
                    });
                    setUpListeners();

                }


                        //setting up listeners
                public void setUpListeners(){

                    button_compiled_classes.addActionListener(this);
                    button_get_mf.addActionListener(this);
                    button_get_output_directory.addActionListener(this);
                    generate_jar.addActionListener(this);
                    button_test_jar.addActionListener(this);
                    button_main_module_dir.addActionListener(this);
                    button_generate_mf.addActionListener(this);
                }
            
    public void arrange(Component comp,GridBagConstraints gc,int posx,int posy,int widthx,int widthy,int weightx,int weighty){
        gc.gridx=posx;
        gc.gridy=posy;
        gc.gridwidth=widthx;
        gc.gridheight=widthy;
        gc.weightx=weightx;
        gc.weighty=weighty;
        gc.fill=GridBagConstraints.BOTH;
        gc.anchor=GridBagConstraints.CENTER;
        gc.insets=new Insets(4,2,2,2);
        gc.ipady=10;
        add(comp,gc);
    }

    public void initialize(){
        is_app_modular.setState(false);

        label_main_module_dir.setEnabled(true);
        tf_main_module_dir.setEnabled(true);
        button_main_module_dir.setEnabled(true);
        
        label_main_module_dir.setEnabled(false);
        tf_main_module_dir.setEnabled(false);
        button_main_module_dir.setEnabled(false);
        
    }


    public void setCompiledFilesDirectory(){

        j_file_and_dir_select_dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // file_and_directory_select_dialog.setTitle("select compiled class directory");
        j_file_and_dir_select_dialog.setDialogTitle("select compiled class directory");
        j_file_and_dir_select_dialog.showOpenDialog(this);
        // file_and_directory_select_dialog.setVisible(true);

        File f= j_file_and_dir_select_dialog.getSelectedFile();
        // String directory=file_and_directory_select_dialog.getDirectory();
        String directory = f.getAbsolutePath();
        tf_compiled_files.setText(directory);
    }


    public void getManifestFile(){

        file_and_directory_select_dialog.setTitle("select manifest file");
        file_and_directory_select_dialog.setVisible(true);
        String file_name=file_and_directory_select_dialog.getDirectory()+"\\"+file_and_directory_select_dialog.getFile();
        tf_manifest_file.setText(file_name);
    }

    public void getOutputDirectory(){
        j_file_and_dir_select_dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // file_and_directory_select_dialog.setTitle("select directory you wanna store jar file");
        j_file_and_dir_select_dialog.setDialogTitle("Select directory where you wanna store file");
        // file_and_directory_select_dialog.setVisible(true);
        j_file_and_dir_select_dialog.showOpenDialog(this);
        File output_dir=j_file_and_dir_select_dialog.getSelectedFile();
        // String output_directory=file_and_directory_select_dialog.getDirectory();
        String output_directory=output_dir.getAbsolutePath();
        tf_output_file_dir.setText(output_directory);
    }

    public void getMainModuleDirectory(){

        j_file_and_dir_select_dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // file_and_directory_select_dialog.setTitle("select main module directory");
        j_file_and_dir_select_dialog.setDialogTitle("select main module directory");
        // file_and_directory_select_dialog.setVisible(true);
        j_file_and_dir_select_dialog.showOpenDialog(this);
        File module_dir=j_file_and_dir_select_dialog.getSelectedFile();
        // String main_module_director=file_and_directory_select_dialog.getDirectory();
        String main_module_directory=module_dir.getAbsolutePath();
        tf_main_module_dir.setText(main_module_directory);
        
    }

    public Class<?> loadClassFromJar(File f,String class_name) throws Exception{
       
        if(this.class_to_be_loaded==null){
            final URLClassLoader loader=new URLClassLoader(new URL[]{f.toURI().toURL()},ClassLoader.getSystemClassLoader());
         this.class_to_be_loaded=Class.forName(class_name,true,loader);
        }
        
        return this.class_to_be_loaded;
    }

    public Object getInstanceFromJar(File file,String class_name){
        try{

            if(this.instance==null){
            
                Class<?> class_to_be_loaded=loadClassFromJar(file, class_name);
                this.instance=class_to_be_loaded.getDeclaredConstructor().newInstance();
              
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return this.instance;
        
    }
                    
    public void generate_manifest(){
                
        try{
                cp=System.getProperty("user.dir")+File.separator+"src"+File.separator+"libs"+File.separator+"lol.jar";
                // cp=System.getProperty("java.class.path")+File.separator+"my_mfc.jar";
                File file= new File(cp);
                Class<?> class_from_jar=loadClassFromJar(file, "main.ManifestCreator");
                
                 Object instance =getInstanceFromJar(file,"main.ManifestCreator");
                
                 Field field=instance.getClass().getDeclaredField("file_name");
                 field.setAccessible(true);
                 showDeclaredClassesFromClass(class_from_jar);
               
                 Frame f=Frame.class.cast(instance);
                 f.addWindowListener(new WindowAdapter(){
                     public  void windowClosed(WindowEvent e){
                         log("WINDOW EVENT","closed");
                         try{
                             String f_name=(String)field.get(instance);
                            log("WINDOW EVENT FILE IS ",f_name);
                            tf_manifest_file.setText(f_name);
                            JarPackUi.this.instance=null;
                         }catch(Exception es){
                             es.printStackTrace();
                         }
                        
                     }
                 });
                
        }catch(Exception r){
            
            log("Exception in class", r.getClass().toString());
            r.printStackTrace();} 

        // try{
        //         jarFile.close();
        // }catch(Exception e){
        //     e.printStackTrace();
        // }

        
                
    }


                        //function that generates jar

    public void generate_jar(boolean is_app_modular){
        List<String> my_command=new ArrayList<String>();
        String manifest_file,output_file_name,output_directory;
        String module_directory="";
        String compiled_file_directory="";
        output_directory=tf_output_file_dir.getText();
        output_file_name=tf_output_file_name.getText();
        manifest_file=tf_manifest_file.getText();
        my_command.add("jar");
        my_command.add("-c");
        my_command.add("-v");
        my_command.add("-f");
        my_command.add(output_directory+"\\"+output_file_name+".jar");
        my_command.add("-m");
        my_command.add(manifest_file);
        if(is_app_modular){
            module_directory=tf_main_module_dir.getText();
          
            my_command.add("-C");
            my_command.add(module_directory);
            my_command.add(".");
           
        }
        else{
            compiled_file_directory=tf_compiled_files.getText();
            my_command.add("-C");
            my_command.add(compiled_file_directory);
            my_command.add(".");
        }
        try{
            process=process_builder.command(my_command).start();            
            status.setForeground(Color.decode("#f4a261"));
            status.setBackground(Color.decode("#ffffff"));
            status.setText("STARTED");
    
            status.setText("UNDER PROCESS");
            while(process.isAlive()){
                generate_jar.setEnabled(false);
                status.setForeground(Color.decode("#f4a261"));
                status.setBackground(Color.decode("#ffffff"));

                status.setText("UNDER PROCESS");
            }
            generate_jar.setEnabled(true);
            status.setForeground(Color.decode("$b5e4ac"));
            status.setBackground(Color.decode("#ffffff"));
            status.setText("COMPLETE");
            

        }catch(IOException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            
            System.out.println(e.getMessage());
          

        }
        if(process.exitValue()==0){
            status.setForeground(Color.decode("#23047"));
            status.setBackground(Color.decode("#d9ed92"));
            status.setText("DONE");
        }
        if(process.exitValue()<0){
            status.setForeground(Color.decode("#23047"));
            status.setBackground(Color.decode("#d00000"));
            status.setText("DONE");
        }

    }

    public void testJar(){

        String program="C:\\Program Files\\Java\\jdk-16.0.1\\bin\\java";
        String output_file_n=tf_output_file_dir.getText()+"\\"+tf_output_file_name.getText()+".jar";
        List<String> my_test_command=new ArrayList<String>();
        my_test_command.add("java");
        my_test_command.add("-jar");
        my_test_command.add(output_file_n);

        try{
        Process p=process_builder.command(my_test_command).start();
        while(p.isAlive()){
            button_test_jar.setEnabled(false);
        }
        if(!p.isAlive()){
            button_test_jar.setEnabled(true);
        }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }


    public void actionPerformed(ActionEvent e){

            Object widget=e.getSource();


        if(widget==button_compiled_classes){
            setCompiledFilesDirectory();
        }
        else if(widget==button_get_mf){
            getManifestFile();
        }
        else if(widget==button_get_output_directory){
            getOutputDirectory();
        }
        else if(widget==generate_jar)
        {
            generate_jar(this.is_app_modular.getState());
        }
        else if(widget==button_test_jar){
                System.out.println("test button pressed");
                testJar();
         }
         else if(widget==button_main_module_dir){
             getMainModuleDirectory();
         }
        else if(widget==button_generate_mf){
            generate_manifest();
        }   
        else {

        }
    }

    

    public static void log(String title,String message){
        Date date =Calendar.getInstance().getTime();
        
        String timestamp=dateformat.format(date);
        System.out.println(timestamp+" "+title+": "+message);
    }



    public static void showMethodsOfClass(Class<?> target_class){

        Method[] methods =target_class.getMethods();
        for(Method p:methods){

            Class<?>[] parametes=p.getParameterTypes();
            String method_name=p.getName();
            log("method is",method_name);
            for(Class<?> param:parametes){
                
                log(method_name+" has parimeter ",param.getName());
                

            }

        

        }

    }

    public static void showInterfacesFromJarClass(Class<?> tClass){
        int count=0;
        Class<?>[] interrfaces = tClass.getInterfaces();
        for(Class<?> i:interrfaces){
            log("interface "+count,i.getName());
            count++;
        }
    }

    public static void showDeclaredClassesFromClass(Class<?> tClass){
        int count=0;
        Class<?>[] classes=tClass.getDeclaredClasses();
        for(Class<?> mClass:classes){
            log("class "+count,mClass.getName());
            count++;
        }

    }

    public void setMfFilePath(){
        File f=new File("mf.txt");
        if(f.exists()){
            tf_manifest_file.setText(f.getAbsolutePath());
        }
    }

}
