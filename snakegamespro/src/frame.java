import javax.swing.JFrame;

public class frame extends JFrame {
    frame(){
        // set the title of project
        this.setTitle("snake");
        // adding the panel to the frame
        this.add(new panel());
        // if its true , it will make frame/window visible because its false by default that's why we write true
        this.setVisible(true);
        this.setResizable(false);
        this.pack();
    }
}
