package home;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
public class MyUtils {
    
    public final static DateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    public static void log(String title,String message){
        Date date =Calendar.getInstance().getTime();
        
        String timestamp=dateformat.format(date);
        System.out.println(timestamp+" "+title+": "+message);
    }


}
