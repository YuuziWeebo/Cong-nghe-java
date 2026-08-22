package vn.edu.eaut.lab8.repository;

import vn.edu.eaut.lab8.model.Sach;
import vn.edu.eaut.lab8.util.DBConnection;
import java.sql.*;
import java.util.*;

public class SachRepository {
    public void add(Sach s) throws SQLException {
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(
                "INSERT INTO sach(ten_sach,tac_gia,nam_xuat_ban) VALUES(?,?,?)")) {
            p.setString(1,s.getTenSach()); p.setString(2,s.getTacGia()); p.setInt(3,s.getNamXuatBan()); p.executeUpdate();
        }
    }
    public List<Sach> findAll() throws SQLException {
        List<Sach> out=new ArrayList<>();
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement("SELECT * FROM sach ORDER BY id");
            ResultSet r=p.executeQuery()) {
            while(r.next()) out.add(new Sach(r.getInt("id"),r.getString("ten_sach"),r.getString("tac_gia"),r.getInt("nam_xuat_ban")));
        }
        return out;
    }
}
