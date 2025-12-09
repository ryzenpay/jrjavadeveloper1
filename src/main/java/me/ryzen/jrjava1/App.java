package me.ryzen.jrjava1;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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
    private static JFrame frame = new JFrame();
    public static void main(String[] args) {
        initDatabase();
        dummyData();
        initFrame();
        frame.setVisible(true);
    }

    private static void initDatabase(){
        try{
            logger.debug("Setting up hibernate...");
            factory = new Configuration().configure().buildSessionFactory();
            logger.debug("Successfully set up hibernate");
        } catch (Exception e){
            logger.error("Exception setting up database", e);
        }
    }

    private static void dummyData(){
        Session session = factory.openSession();
        session.beginTransaction();
        Long userCount = (Long) session.createQuery("SELECT count(u) FROM User u").uniqueResult();

        if (userCount.longValue() == 0) {
            logger.info("No data found. Populating database with dummy data...");
            session.save(new User("Pim", "Pimling", 33, "Critter", false));
            session.save(new User("Charlie", "Dompler", 27, "Critter", false));
            session.save(new User("Alan", "Red", 24, "Critter", false));
            session.save(new User("Glep", "", 1696, "Critter", false));
            session.save(new User("Mr.", "Boss", -1, "Human", false));
            session.getTransaction().commit();
            logger.info("Dummy data has been inserted.");
        } else {
            logger.info("Database already contains data. Skipping dummy data");
        }
        session.close();
    }

    private static JTable getUserTable(){
        Session session = factory.openSession();
        session.beginTransaction();
        List<User> users = session.createQuery("FROM User").list();
        Model model = new Model(new ArrayList<>(users));
        session.close();
        return new JTable(model);
    }

    private static void initFrame(){
        JPanel textPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search:");
        JTextField textField = new JTextField();
        JTable table = getUserTable();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                logger.debug("textfield insert update");
                String text = textField.getText();
                if (text.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                logger.debug("textfield remove update");
                String text = textField.getText();
                if (text.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        
        textPanel.add(searchLabel, BorderLayout.WEST);
        textPanel.add(textField, BorderLayout.CENTER);
        frame.add(textPanel, BorderLayout.SOUTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // center
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        // TODO: variable frame size
        frame.setSize(300, 300);
    }
}
