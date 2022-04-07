package home;
import java.awt.*;
import java.awt.event.*;
public class OutputArea extends Panel {
    
    FlowLayout root_layout;
    Scrollbar horizontal_scroll_bar,verticle_scroll_bar;
    TextArea output_area;

    public OutputArea(){
        root_layout=new FlowLayout();
        setLayout(root_layout);
        // verticle_scroll_bar=new Scrollbar(Scrollbar.VERTICAL);
        output_area=new TextArea(5,40);
        add(output_area);
        // add(verticle_scroll_bar);
    }

}
