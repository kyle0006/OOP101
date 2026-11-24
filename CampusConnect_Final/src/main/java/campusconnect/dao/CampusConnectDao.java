package campusconnect.dao;
import java.sql.*;
import java.util.*;
public final class CampusConnectDao implements AutoCloseable {
    private final Connection c;
    public CampusConnectDao(String url)throws SQLException {
        c=DriverManager.getConnection(url);
        try(Statement s=c.createStatement()) {
            s.execute("PRAGMA foreign_keys=ON");
        }
    }
    public void initialize()throws SQLException {
        try(Statement s=c.createStatement()) {
            s.executeUpdate("CREATE TABLE IF NOT EXISTS service_request(ticket_id TEXT PRIMARY KEY,student_id TEXT NOT NULL,student_name TEXT NOT NULL,category TEXT NOT NULL,priority INTEGER NOT NULL,description TEXT NOT NULL,estimated_days INTEGER NOT NULL,status TEXT NOT NULL,created_at TEXT NOT NULL,updated_at TEXT NOT NULL)");
        }
    }
    public void close()throws SQLException {
        c.close();
    }
    public List<RequestRow> find(String q,String status)throws SQLException {
        try(PreparedStatement p=c.prepareStatement("SELECT ticket_id,student_id,student_name,category,priority,description,estimated_days,status FROM service_request WHERE (lower(ticket_id)||' '||lower(student_id)||' '||lower(student_name)||' '||lower(description)) LIKE ? AND (?='' OR status=?) ORDER BY priority DESC,estimated_days,ticket_id")) {
            p.setString(1,"%"+(q==null?"":q.toLowerCase())+"%");
            p.setString(2,status==null?"":status);
            p.setString(3,status==null?"":status);
            try(ResultSet r=p.executeQuery()) {
                List<RequestRow>x=new ArrayList<>();
                while(r.next())x.add(new RequestRow(r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getInt(5),r.getString(6),r.getInt(7),r.getString(8)));
                return x;
            }
        }
    }
    public void insert(String id,String sid,String name,String cat,int priority,String desc,int days)throws SQLException {
        String now=java.time.LocalDateTime.now().toString();
        try(PreparedStatement p=c.prepareStatement("INSERT INTO service_request(ticket_id,student_id,student_name,category,priority,description,estimated_days,status,created_at,updated_at) VALUES(?,?,?,?,?,?,?,'NEW',?,?)")) {
            p.setString(1,id);
            p.setString(2,sid);
            p.setString(3,name);
            p.setString(4,cat);
            p.setInt(5,priority);
            p.setString(6,desc);
            p.setInt(7,days);
            p.setString(8,now);
            p.setString(9,now);
            p.executeUpdate();
        }
    }
    public void updateStatus(String id,String status)throws SQLException {
        try(PreparedStatement p=c.prepareStatement("UPDATE service_request SET status=?,updated_at=? WHERE ticket_id=?")) {
            p.setString(1,status);
            p.setString(2,java.time.LocalDateTime.now().toString());
            p.setString(3,id);
            if(p.executeUpdate()!=1)throw new SQLException("Request not found");
        }
    }
    public void delete(String id)throws SQLException {
        try(PreparedStatement p=c.prepareStatement("DELETE FROM service_request WHERE ticket_id=?")) {
            p.setString(1,id);
            if(p.executeUpdate()!=1)throw new SQLException("Request not found");
        }
    }
    public Optional<RequestRow> findById(String id)throws SQLException {
        return find(id,"").stream().filter(x->x.ticketId().equals(id)).findFirst();
    }
    public Optional<RequestRow> next()throws SQLException {
        return find("","NEW").stream().findFirst();
    }
    public Map<String,Integer> summary()throws SQLException {
        try(Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT status,COUNT(*) FROM service_request GROUP BY status")) {
            Map<String,Integer>m=new LinkedHashMap<>();
            while(r.next())m.put(r.getString(1),r.getInt(2));
            return m;
        }
    }
    public record RequestRow(String ticketId,String studentId,String studentName,String category,int priority,String description,int estimatedDays,String status) {
    }
}
