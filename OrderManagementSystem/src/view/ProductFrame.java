package view;

import business.ProductController;
import core.Helper;
import entity.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductFrame extends JFrame {
    private JPanel mainPanel;
    private JLabel lbl_title;
    private JTextField fld_product_name;
    private JTextField fld_product_code;
    private JTextField fld_product_price;
    private JTextField fld_product_stock;
    private JButton btn_product;
    private JLabel lbl_product_name;
    private JLabel lbl_product_code;
    private JLabel lbl_product_price;
    private JLabel lbl_product_stock;
    private Product product;
    private ProductController productController;

    public ProductFrame(Product product) {
        this.product = product;
        this.productController = new ProductController();
        this.add(mainPanel);
        this.setTitle("Add / Edit Product");
        this.setSize(300,350);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        if (this.product.getId() == 0)
            this.lbl_title.setText("Add Product");
        else {
            this.lbl_title.setText("Edit Product");
            this.fld_product_name.setText(this.product.getName());
            this.fld_product_code.setText(this.product.getCode());
            this.fld_product_price.setText(String.valueOf(this.product.getPrice()));
            this.fld_product_stock.setText(String.valueOf(this.product.getStock()));
        }

        btn_product.addActionListener(e -> {
            JTextField[] checkList = {
                    this.fld_product_name,
                    this.fld_product_code,
                    this.fld_product_price,
                    this.fld_product_stock
            };
            if (Helper.isFieldListEmpty(checkList))
                Helper.showMsg("fill");
            else {
                boolean result = false;
                this.product.setName(this.fld_product_name.getText());
                this.product.setCode(this.fld_product_code.getText());
                this.product.setPrice(Integer.parseInt(this.fld_product_price.getText()));
                this.product.setStock(Integer.parseInt(this.fld_product_stock.getText()));

                if (this.product.getId() == 0)
                    result = this.productController.save(product);
                else
                    result = this.productController.update(product);

                if (result) {
                    Helper.showMsg("done");
                    this.dispose();
                } else {
                    Helper.showMsg("error");
                }
            }

        });
    }


}
