package vn.edu.eaut.lab8.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import vn.edu.eaut.lab8.util.DBConnection;
import java.sql.*;

@Named("loginBean")
@RequestScoped
public class LoginBean {
    private String username;
    private String password;

    public String login(){
        try(Connection c=DBConnection.getConnection();
            PreparedStatement p=c.prepareStatement("SELECT 1 FROM tai_khoan WHERE username=? AND password=?")){
            p.setString(1,username); p.setString(2,password);
            try(ResultSet r=p.executeQuery()){
                if(r.next()) return "/index?faces-redirect=true";
            }
        }catch(SQLException e){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,"Lỗi DB",e.getMessage()));
            return null;
        }
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(
                FacesMessage.SEVERITY_ERROR,"Đăng nhập thất bại","Sai tài khoản hoặc mật khẩu"));
        return null;
    }
    public String getUsername(){return username;}
    public void setUsername(String v){username=v;}
    public String getPassword(){return password;}
    public void setPassword(String v){password=v;}
}
