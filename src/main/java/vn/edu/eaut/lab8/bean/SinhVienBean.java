package vn.edu.eaut.lab8.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import vn.edu.eaut.lab8.model.SinhVien;
import vn.edu.eaut.lab8.repository.SinhVienRepository;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Named("sinhVienBean")
@SessionScoped
public class SinhVienBean implements Serializable {
    private SinhVien sinhVien = new SinhVien();
    private final SinhVienRepository repo = new SinhVienRepository();
    private String keyword = "";

    public String save() {
        try {
            if (sinhVien.getId() == 0) {
                repo.add(sinhVien);
                msg(FacesMessage.SEVERITY_INFO,"Thành công","Đã thêm sinh viên");
            } else {
                repo.update(sinhVien);
                msg(FacesMessage.SEVERITY_INFO,"Thành công","Đã cập nhật sinh viên");
            }
            sinhVien = new SinhVien();
        } catch (SQLException e) {
            msg(FacesMessage.SEVERITY_ERROR,"Lỗi","Không thể lưu: " + e.getMessage());
        }
        return null;
    }

    public String edit(SinhVien sv) {
        sinhVien = new SinhVien(sv.getId(),sv.getMaSinhVien(),sv.getHoTen(),sv.getEmail(),sv.getLop());
        return "/sinhvien-form?faces-redirect=true";
    }

    public void delete(int id) {
        try { repo.delete(id); msg(FacesMessage.SEVERITY_INFO,"Thành công","Đã xóa sinh viên"); }
        catch(SQLException e) { msg(FacesMessage.SEVERITY_ERROR,"Lỗi","Không thể xóa: "+e.getMessage()); }
    }

    public List<SinhVien> getDsSinhVien() {
        try { return repo.find(keyword); }
        catch(SQLException e) { msg(FacesMessage.SEVERITY_ERROR,"Lỗi DB",e.getMessage()); return List.of(); }
    }

    public String getKeyword(){return keyword;}
    public void setKeyword(String v){keyword=v;}
    public SinhVien getSinhVien(){return sinhVien;}
    public void setSinhVien(SinhVien v){sinhVien=v;}
    private void msg(FacesMessage.Severity s,String a,String b){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(s,a,b));
    }
}
