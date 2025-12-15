package org.mol211.ventaceramica.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mol211.ventaceramica.service.CategoryService;
import org.mol211.ventaceramica.service.IProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mol211.ventaceramica.util.FXUtils.enableDrag;

public class DashboardController {
  @FXML
  private AnchorPane aPane_center;

  @FXML
  private Button btn_addProducts_signOut;

  @FXML
  private Button btn_close;

  @FXML
  private Button btn_minimize;

  @FXML
  private Button btn_navbar_addProducts;

  @FXML
  private Button btn_navbar_home;

  @FXML
  private Button btn_navbar_orders;

  @FXML
  private AnchorPane panel_main;

  private IProductService productService;
  private CategoryService categoryService;

  private Map<String, AnchorPane> panels = new HashMap<>();

  private final String HOME_PANEL = "/org/mol211/ventaceramica/home.fxml";
  private final String PRODUCT_PANEL = "/org/mol211/ventaceramica/product.fxml";
  private final String ORDER_PANEL = "/org/mol211/ventaceramica/order.fxml";


  //Cerar aplicación
  public void close() {
    System.exit(0);
  }
  //Cerrar sesión y volver al panel login
  public void logout() {
    try {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Confirmation Message");
      alert.setHeaderText(null);
      alert.setContentText("Are you sure you want to logout?");
      Optional<ButtonType> option = alert.showAndWait();
      if (option.isPresent()) {
        if (option.get().equals(ButtonType.OK)) {
          btn_addProducts_signOut.getScene().getWindow().hide();
          Parent root = FXMLLoader.load(getClass().getResource("/org/mol211/ventaceramica/login.fxml"));
          Stage stage = new Stage();
          Scene scene = new Scene(root);
          enableDrag(root, stage);
          stage.initStyle(StageStyle.TRANSPARENT);
          stage.setScene(scene);
          stage.show();

        } else return;
      } else throw new RuntimeException("Init Stage not found");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void minimize() {
    Stage stage = (Stage) panel_main.getScene().getWindow();
    stage.setIconified(true);
  }
  void loadPanel(String FXMLPath){
    try{
      FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLPath));
      AnchorPane pane = loader.load();

      //Obtener el controlador del panel
      Object controller = loader.getController();

      //Pasamos los servicios (de momento category y product)
      if(controller instanceof ProductController prdCtrl){
        prdCtrl.setServices(productService, categoryService);
        prdCtrl.postInit();
      } else if (controller instanceof  HomeController homctrl) {
        homctrl.setServices(productService, categoryService);
      } else if (controller instanceof OrderController orderctrl) {
        orderctrl.setServices(productService, categoryService);
      }
      panels.put(FXMLPath, pane);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  void showPanel(String FXMLPath){
    aPane_center.getChildren().clear();
    AnchorPane pane = panels.get(FXMLPath);
    if(pane != null){
      aPane_center.getChildren().setAll(pane);
    }

  }
  public void showProduct(){
    showPanel(PRODUCT_PANEL);
    focusBtn(btn_navbar_addProducts);
  }
  public void showHome(){
    showPanel(HOME_PANEL);
    focusBtn(btn_navbar_home);

  }
  public void showOrder(){
    showPanel(ORDER_PANEL);
    focusBtn(btn_navbar_orders);
  }
  void postInit(){
    loadPanel(PRODUCT_PANEL);
    loadPanel(ORDER_PANEL);
    loadPanel(HOME_PANEL);
    showHome();
  }
  public void focusBtn(Button btn) {
    if (btn == btn_navbar_addProducts){
      btn_navbar_addProducts.setStyle("-fx-background-color: linear-gradient(to bottom right, #25a473, #89892b)");
      btn_navbar_orders.setStyle("-fx-background-color: transparent");
      btn_navbar_home.setStyle("-fx-background-color: transparent");
    } else if (btn == btn_navbar_home){
      btn_navbar_home.setStyle("-fx-background-color: linear-gradient(to bottom right, #25a473, #89892b)");
      btn_navbar_orders.setStyle("-fx-background-color: transparent");
      btn_navbar_addProducts.setStyle("-fx-background-color: transparent");
    }else if (btn == btn_navbar_orders){
      btn_navbar_orders.setStyle("-fx-background-color: linear-gradient(to bottom right, #25a473, #89892b)");
      btn_navbar_home.setStyle("-fx-background-color: transparent");
      btn_navbar_addProducts.setStyle("-fx-background-color: transparent");
    }

  }


  public void setServices(IProductService productService, CategoryService categoryService) {
    this.productService = productService;
    this.categoryService = categoryService;
  }
}
