package vn.edu.eaut.lab8.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import vn.edu.eaut.lab8.model.Product;
import vn.edu.eaut.lab8.repository.ProductRepository;
import java.sql.SQLException;
import java.util.List;

@Named("productBean")
@RequestScoped
public class ProductBean {
    private Product product = new Product();
    private final ProductRepository repo = new ProductRepository();

    public void save(){
        try{repo.add(product);product=new Product();msg("Đã thêm sản phẩm");}
        catch(SQLException e){msg("Lỗi DB: "+e.getMessage());}
    }
    public List<Product> getDsProduct(){
        try{return repo.findAll();}catch(SQLException e){return List.of();}
    }
    public Product getProduct(){return product;}
    public void setProduct(Product v){product=v;}
    private void msg(String s){FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO,"Thông báo",s));}
}
