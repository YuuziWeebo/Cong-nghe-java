package vn.edu.eaut.lab10.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable=false, length=30) private String isbn;
    @Column(nullable=false, length=150) private String title;
    @Column(nullable=false, length=100) private String author;
    @Column(nullable=false) private int quantity;

    public Book() {}
    public Book(String isbn,String title,String author,int quantity){
        this.isbn=isbn;this.title=title;this.author=author;this.quantity=quantity;
    }
    public Integer getId(){return id;}
    public String getIsbn(){return isbn;} public void setIsbn(String v){isbn=v;}
    public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getAuthor(){return author;} public void setAuthor(String v){author=v;}
    public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;}
}
