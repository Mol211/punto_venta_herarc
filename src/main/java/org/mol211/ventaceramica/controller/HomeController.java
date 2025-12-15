package org.mol211.ventaceramica.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.mol211.ventaceramica.service.CategoryService;
import org.mol211.ventaceramica.service.IProductService;

public class HomeController {

  @FXML
  private AreaChart<?, ?> chart_home_income;

  @FXML
  private BarChart<?, ?> chart_home_orders;

  @FXML
  private Label lbl_home_avaialableProd;

  @FXML
  private Label lbl_home_numberOrder;

  @FXML
  private Label lbl_home_totalIncome;

  @FXML
  private AnchorPane panel_home;

  private IProductService productService;
  private CategoryService categoryService;


  public void setServices(IProductService productService, CategoryService categoryService) {
    this.productService = productService;
    this.categoryService = categoryService;
  }
}
