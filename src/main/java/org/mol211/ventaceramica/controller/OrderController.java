package org.mol211.ventaceramica.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.mol211.ventaceramica.service.CategoryService;
import org.mol211.ventaceramica.service.IProductService;

public class OrderController {

  @FXML
  private Button btn_orders_add;

  @FXML
  private Button btn_orders_pay;

  @FXML
  private Button btn_orders_receipt;

  @FXML
  private Button btn_orders_reset;

  @FXML
  private ComboBox<?> cbox_orders_category;

  @FXML
  private ComboBox<?> cbox_orders_codProd;

  @FXML
  private ComboBox<?> cbox_orders_name;

  @FXML
  private Label lbl_orders_balance;

  @FXML
  private Label lbl_orders_total;

  @FXML
  private AnchorPane panel_orders;

  @FXML
  private Spinner<?> spin_orders_quantity;

  @FXML
  private TableView<?> tbView_addOrders;

  @FXML
  private TableColumn<?, ?> tbcol_orders_category;

  @FXML
  private TableColumn<?, ?> tbcol_orders_codProd;

  @FXML
  private TableColumn<?, ?> tbcol_orders_nameProd;

  @FXML
  private TableColumn<?, ?> tbcol_orders_quantity;

  @FXML
  private TableColumn<?, ?> tbcol_orders_total;

  @FXML
  private TextField txt_orders_amount;

  private IProductService productService;
  private CategoryService categoryService;


  public void setServices(IProductService productService, CategoryService categoryService) {
    this.productService = productService;
    this.categoryService = categoryService;
  }

}
