package home;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.text.LabelView;
import home.OutputArea;

public class UiPackingLib extends Panel{

    Frame root;
                // UI WIDGETSS VARIABLES NEEEDED 
    GridBagLayout root_layout;
    GridBagConstraints constraints;

    CheckboxGroup modular_lib_check;
    Checkbox modular,non_modular;
    Label class_path_label,package_path_label,main_class_label,class_name_hint_label,output_dir_label,output_file_name;
    TextField class_path_field,package_path_field,main_class_field,output_dir_field,output_file_name_field;
    Button  class_path_button,package_path_button,output_dir_button,generate_jar_button;
    Checkbox cb_lib_modular;

            // flag varibles
    boolean is_lib_modular=true;

            // file opening dialogs
    FileDialog select_compiled_class_dialog;

                //output area

                OutputArea output_area;

                //label output area;

                Label label_output_area;

                    //command
                    String command;

    public UiPackingLib(Frame root){
        this.root=root;
        setBackground(Color.decode("#ffcccc"));

                        // root container
        root_layout = new GridBagLayout();
        constraints=new GridBagConstraints();
        setLayout(root_layout);

       
            // ui widgets 
        class_path_label=new Label("Compiled files directory");
        class_path_field = new TextField(20);
        class_path_button = new Button("set compiled files directory");

        package_path_label= new Label("Module Name");
        package_path_field=new TextField(20);
        // package_path_button=new Button("set package ");

            
            //hints

            class_name_hint_label=new Label("eg.module_name/package_name.ClassName");


         //options to choose if lib is modular or non modular
         cb_lib_modular = new Checkbox("modular");
         cb_lib_modular.setState(true);
         cb_lib_modular.addItemListener(new ItemListener(){
             public void itemStateChanged(ItemEvent e){
                 if(e.getStateChange()==1){
                     is_lib_modular=true;
                    package_path_label.setEnabled(true);
                    package_path_field.setEnabled(true);
                    class_name_hint_label.setEnabled(true);
                      
                 }else{
                    is_lib_modular=false;
                    package_path_label.setEnabled(false);
                    package_path_field.setEnabled(false);
                    class_name_hint_label.setEnabled(false);
                 }
 
             }
         });
 
                    //output directory set up
                    output_dir_label = new Label("Output directory");
                    output_dir_field= new TextField(20);
                    output_dir_button=new Button("Set Output directory");

                    //output file name setup

                    output_file_name=new Label("Output file Name");
                    output_file_name_field = new TextField(20);

                    //generate jar 
                    generate_jar_button=new Button("Generate Jar");

                    //file dialogs

                    select_compiled_class_dialog = new FileDialog(this.root,"select class folder");


                    output_area = new OutputArea();

                // setting up listeners

                class_path_button.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                            select_compiled_class_dialog.setVisible(true);
                            String dir_name= select_compiled_class_dialog.getDirectory();
                            class_path_field.setText(dir_name);
                            // class_path_field.setText(directory_selected);
                    }
                });
                output_dir_button.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        select_compiled_class_dialog.setVisible(true);
                        String output_dir_name=select_compiled_class_dialog.getDirectory();
                        output_dir_field.setText(output_dir_name);
                    }
                });

                        //generate jar listener
                        output_area.setEnabled(false);
                        generate_jar_button.addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent e){
                                output_area.setEnabled(true);
                                generate_jar_button.setEnabled(false);
                                if(is_lib_modular){
                                    command="jar -c -v -f "+ output_dir_field.getText()+"\\"+output_file_name_field.getText()+".jar -C "+class_path_field.getText()+"\\"+package_path_field.getText()+" .";
                                }else{
                                command ="jar -c -v -f "+ output_dir_field.getText()+"\\" + output_file_name_field.getText()+".jar "+"-C "+class_path_field.getText()+" .";}
                                try{
                                Process make_jar_process=Runtime.getRuntime().exec(command);
                                BufferedReader output=new BufferedReader(new InputStreamReader(make_jar_process.getInputStream()));
                                BufferedReader error_stream=new BufferedReader(new InputStreamReader(make_jar_process.getErrorStream()));
                                while(output.readLine()!=null){
                                    output_area.output_area.append(output.readLine());
                                    output_area.output_area.append("\n");
                                }
                                }catch(Exception ex){
                                        System.out.println(ex);
                                }


                                try{

                                    // while(error_stream.readLine()!=null){
                                    //     output_area.output_area.append(error.readLine());
                                    // }
                                }catch(Exception exx){
                                    System.out.println(e);
                                }
                                
                            }

                        });


                        label_output_area=new Label("Output");
        

        //setting class fields 
        arrange(cb_lib_modular, constraints, 0, 0,1,1,0,0);
        arrange(class_path_label,constraints,0,1,1,1,0,0);
        arrange(class_path_field,constraints,1,1,1,1,0,0);
        arrange(class_path_button,constraints,2,1,1,1,0,0);

        arrange(package_path_label,constraints,0,2,1,1,0,0);
        arrange(package_path_field,constraints,1,2,1,1,0,0);
        arrange(class_name_hint_label, constraints,2 ,2, 1, 1, 0,0);

        arrange(output_dir_label,constraints,0,3,1,1,0,0);
        arrange(output_dir_field,constraints, 1,3,1,1,0,0);
        arrange(output_dir_button,constraints,2,3,2,1,0,0);
        
         arrange(output_file_name,constraints,0,4,1,1,0,0);
         arrange(output_file_name_field,constraints,1,4,1,1,0,0);

         arrange(generate_jar_button,constraints,1,5,1,1,0,0);
        
         arrange(output_area,constraints,1,6,1,1,0,0);

         arrange(label_output_area,constraints,0,6,1,1,0,0);
                    
        }

    public void arrange(Component comp,GridBagConstraints gc,int posx,int posy,int widthx,int widthy,int weightx,int weighty){
        gc.gridx=posx;
        gc.gridy=posy;
        gc.gridwidth=widthx;
        gc.gridheight=widthy;
        gc.weightx=weightx;
        gc.weighty=weighty;
        gc.fill=GridBagConstraints.BOTH;
        gc.insets=new Insets(2,2,2,2);
        add(comp,gc);
    }

    
}
