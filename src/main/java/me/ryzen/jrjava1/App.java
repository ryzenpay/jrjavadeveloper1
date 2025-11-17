package me.ryzen.jrjava1;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
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
            User user1 = new User();
            user1.setFirstName("Pim");
            user1.setLastName("Pimling");
            user1.setSpecies("Critter");
            session.save(user1);

            User user2 = new User();
            user2.setFirstName("Charlie");
            user2.setLastName("Dompler");
            user2.setSpecies("Critter");
            session.save(user2);

            User user3 = new User();
            user3.setFirstName("Alan");
            user3.setLastName("Red");
            user3.setSpecies("Critter");
            session.save(user3);

            User user4 = new User();
            user4.setFirstName("Glep");
            user4.setLastName("");
            user4.setSpecies("Critter");
            session.save(user4);

            User user5 = new User();
            user5.setFirstName("Mr.");
            user5.setLastName("Boss");
            user5.setSpecies("Human");
            session.save(user5);

            session.getTransaction().commit();
            logger.info("Dummy data has been inserted.");
        } else {
            logger.info("Database already contains data. Skipping dummy data insertion.");
        }
        session.close();
    }

    private static JTable getUserTable(){
        Session session = factory.openSession();
        session.beginTransaction();
        DefaultTableModel tableModel = new DefaultTableModel();
        String[] columns = {"First Name", "Last Name", "Species"};
        tableModel.setColumnIdentifiers(columns);
        List<User> users = session.createQuery("FROM User").list();
        for (User user:users){
            String[] row = {user.getFirstName(), user.getLastName(), user.getSpecies()};
            tableModel.addRow(row);
        }
        session.close();
        return new JTable(tableModel);
    }

    private static void initFrame(){
        JPanel textPanel = new JPanel(new BorderLayout());
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
        
        textPanel.add(textField);
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
