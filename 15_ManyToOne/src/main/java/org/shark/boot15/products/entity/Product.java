package org.shark.boot15.products.entity;



import org.shark.boot15.categories.entity.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")

@Getter
@Setter
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="product_id")
  private Long productId;
  
  @Column(name="product_name")
  private String productName;
  
  private Double price;
  
  private Integer stock;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;
  
  public static Product creatProduct(String productName, Double price, Integer stock, Category category) {
    Product product = new Product();
    product.productName = productName;
    product.price = price;
    product.stock = stock;
    product.category =category;
    return product;
  }
  public void changeCategory(Category category) {
    this.category = category;
    
  }
  public void updateCategory(Double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Product [productId=" + productId + ", productName=" + productName + ", price=" + price + ", stock=" + stock
        + ", category=" + category + "]";
  }
  
  
}
