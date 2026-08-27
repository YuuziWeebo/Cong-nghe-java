package vn.edu.eaut.lab10.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable=false, length=30) private String code;
    @Column(nullable=false, length=150) private String name;
    @Column(nullable=false) private double price;
    @Column(nullable=false) private int quantity;

    public Product() {}
    public Product(String code,String name,double price,int quantity){
        this.code=code;this.name=name;this.price=price;this.quantity=quantity;
    }
    public Integer getId(){return id;}
    public String getCode(){return code;} public void setCode(String v){code=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public double getPrice(){return price;} public void setPrice(double v){price=v;}
    public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;}
}
