package campusconnect.app;
import campusconnect.dao.*;
import campusconnect.service.*;
import campusconnect.model.*;
import javax.swing.*;
import java.awt.*;
public final class CampusConnectTsa4App {
    public static void main(String[]a) {
        SwingUtilities.invokeLater(()-> {
            try {
                CampusConnectDao d=new CampusConnectDao("jdbc:sqlite:campusconnect.db");d.initialize();new Window(new JdbcCampusConnectService(d)).setVisible(true);
            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(null,e.getMessage(),"Startup error",JOptionPane.ERROR_MESSAGE);
            }
        }
        );
    }
}
final class Window extends JFrame {
    final JdbcCampusConnectService s;
    final JTextField search=new JTextField();
    final JComboBox<String>status=new JComboBox<>(new String[] {
        "","NEW","IN_PROGRESS","RESOLVED","CANCELLED"
    }
    );
    final JTable table=new JTable();
    final JLabel message=new JLabel("Ready");
    Window(JdbcCampusConnectService s) {
        super("CampusConnect TSA4");
        this.s=s;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000,600);
        setLocationRelativeTo(null);
        JPanel top=new JPanel(new GridLayout(2,4,5,5));
        top.add(new JLabel("Search"));
        top.add(search);
        top.add(new JLabel("Status"));
        top.add(status);
        JButton refresh=new JButton("Refresh");
        JButton add=new JButton("Add");
        JButton update=new JButton("Update Status");
        JButton delete=new JButton("Delete");
        top.add(refresh);
        top.add(add);
        top.add(update);
        top.add(delete);
        add(top,BorderLayout.NORTH);
        add(new JScrollPane(table),BorderLayout.CENTER);
        add(message,BorderLayout.SOUTH);
        refresh.addActionListener(e->load());
        search.addActionListener(e->load());
        status.addActionListener(e->load());
        add.addActionListener(e->addRequest());
        update.addActionListener(e->update());
        delete.addActionListener(e->delete());
        load();
    }
    void busy(boolean b) {
        setCursor(b?Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR):Cursor.getDefaultCursor());
    }
    void load() {
        busy(true);
        new SwingWorker<java.util.List<CampusConnectDao.RequestRow>,Void>() {
            protected java.util.List<CampusConnectDao.RequestRow>doInBackground()throws Exception {
                return s.dao().find(search.getText(),(String)status.getSelectedItem());
            }
            protected void done() {
                try {
                    var rows=get();
                    String[]cols= {
                        "Ticket","Student","Name","Category","Priority","Description","Days","Status"
                    }
                    ;
                    Object[][]data=rows.stream().map(r->new Object[] {
                        r.ticketId(),r.studentId(),r.studentName(),r.category(),r.priority(),r.description(),r.estimatedDays(),r.status()
                    }
                    ).toArray(Object[][]::new);
                    table.setModel(new javax.swing.table.DefaultTableModel(data,cols));
                    message.setText(rows.size()+" request(s) loaded");
                }
                catch(Exception e) {
                    message.setText(e.getMessage());
                } finally {
                    busy(false);
                }
            }
        }
        .execute();
    }
    void addRequest() {
        JTextField id=new JTextField(),sid=new JTextField(),name=new JTextField(),desc=new JTextField();
        JComboBox<ServiceCategory>cat=new JComboBox<>(ServiceCategory.values());
        JSpinner pri=new JSpinner(new SpinnerNumberModel(3,1,5,1));
        JPanel p=new JPanel(new GridLayout(0,2));
        p.add(new JLabel("Ticket ID"));
        p.add(id);
        p.add(new JLabel("Student ID"));
        p.add(sid);
        p.add(new JLabel("Student name"));
        p.add(name);
        p.add(new JLabel("Category"));
        p.add(cat);
        p.add(new JLabel("Priority"));
        p.add(pri);
        p.add(new JLabel("Description"));
        p.add(desc);
        if(JOptionPane.showConfirmDialog(this,p,"Add request",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)try {
            s.add(id.getText(),sid.getText(),name.getText(),(ServiceCategory)cat.getSelectedItem(),(int)pri.getValue(),desc.getText());
            message.setText("Request added");
            load();
        }
        catch(Exception e) {
            message.setText(e.getMessage());
        }
    }
    void update() {
        int r=table.getSelectedRow();
        if(r<0)return;
        String id=String.valueOf(table.getValueAt(r,0));
        String x=(String)JOptionPane.showInputDialog(this,"Status","Update",JOptionPane.PLAIN_MESSAGE,null,new String[] {
            "IN_PROGRESS","RESOLVED","CANCELLED"
        }
        ,"IN_PROGRESS");
        if(x!=null)try {
            s.updateStatus(id,RequestStatus.valueOf(x));
            load();
        }
        catch(Exception e) {
            message.setText(e.getMessage());
        }
    }
    void delete() {
        int r=table.getSelectedRow();
        if(r<0)return;
        if(JOptionPane.showConfirmDialog(this,"Delete selected request?","Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)try {
            s.delete(String.valueOf(table.getValueAt(r,0)));
            load();
        }
        catch(Exception e) {
            message.setText(e.getMessage());
        }
    }
}
