package me.ryzen.jrjava1;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 * Hello world!
 */
public class App {
    private static final Logger logger = LogManager.getLogger("App");
    private static SessionFactory factory;
    private static JFrame frame;
    private static String filter = "";
    public static void main(String[] args) {
        initDatabase();
        // TODO: dummy data
        initFrame();
        frame.setVisible(true);
    }

    private static void initDatabase(){
        try{
            logger.debug("Setting up hibernate...");
            factory = new Configuration().configure().buildSessionFactory();
            factory.openSession();
            logger.debug("Successfully set up hibernate");
        } catch (Exception e){
            logger.error("Exception setting up database", e);
        }
    }

    private static void initFrame(){
        frame = new JFrame();
        JTextField textField = new JTextField(5);

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e){
                logger.debug("textfield updated");
                filter = textField.getText();
            }
            @Override
            public void insertUpdate(DocumentEvent e){
                logger.debug("textfield insert update");
                filter = textField.getText();
            }
            @Override
            public void removeUpdate(DocumentEvent e){
                logger.debug("textfield remove update");
                String text = textField.getText();
                if (text == null){
                    filter = "";
                } else{
                    filter = text;
                }
            }
        });

        JTable table = new JTable();

        frame.add(textField);
        frame.add(table);

        // center
        frame.pack();
        frame.setLocationRelativeTo(null);
        // TODO: variable frame size
        frame.setSize(300, 300);
    }
}
