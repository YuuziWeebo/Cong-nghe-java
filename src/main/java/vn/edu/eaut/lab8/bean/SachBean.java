package vn.edu.eaut.lab8.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import vn.edu.eaut.lab8.model.Sach;
import vn.edu.eaut.lab8.repository.SachRepository;
import java.sql.SQLException;
import java.util.List;

@Named("sachBean")
@RequestScoped
public class SachBean {
    private Sach sach = new Sach();
    private final SachRepository repo = new SachRepository();

    public void save() {
        try { repo.add(sach); sach=new Sach(); message("Đã thêm sách"); }
        catch(SQLException e){ message("Lỗi DB: "+e.getMessage()); }
    }
    public List<Sach> getDsSach(){
        try{return repo.findAll();}catch(SQLException e){return List.of();}
    }
    public Sach getSach(){return sach;}
    public void setSach(Sach v){sach=v;}
    private void message(String text){FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO,"Thông báo",text));}
}
