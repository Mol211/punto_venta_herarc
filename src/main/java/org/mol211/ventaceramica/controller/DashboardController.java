package org.mol211.ventaceramica.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mol211.ventaceramica.model.Category;
import org.mol211.ventaceramica.model.Product;
import org.mol211.ventaceramica.service.CategoryService;
import org.mol211.ventaceramica.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.mol211.ventaceramica.util.FXUtils.enableDrag;
import static org.mol211.ventaceramica.util.FXUtils.path;

public class DashboardController implements Initializable {

  private Logger logger = LoggerFactory.getLogger(DashboardController.class);

  @FXML
  private Button btn_addProduct_import;

  @FXML
  private Button btn_addProducts_add;

  @FXML
  private Button btn_addProducts_delete;

  @FXML
  private Button btn_addProducts_reset;

  @FXML
  private Button btn_addProducts_signOut;

  @FXML
  private Button btn_addProducts_update;

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
  private Button btn_orders_pay;

  @FXML
  private Button btn_orders_receipt;

  @FXML
  private Button btn_orders_reset;


  @FXML
  private Button btn_orders_add;



  @FXML
  private ComboBox<?> cbox_orders_category;

  @FXML
  private ComboBox<?> cbox_orders_codProd;

  @FXML
  private ComboBox<?> cbox_orders_name;

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
  private Label lbl_orders_balance;

  @FXML
  private Label lbl_orders_total;

  @FXML
  private AnchorPane panel_addProducts;

  @FXML
  private AnchorPane panel_home;

  @FXML
  private AnchorPane panel_main;

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

  private Image image;

  private ObservableList<Product> addProductList;

  //Variable para nueva imagen
  private String newImagePath = null;


  public DashboardController() {
  }

  public void setServices(IProductService productService, CategoryService categoryService) {
    this.productService = productService;
    this.categoryService = categoryService;
  }


  //METODOS DE APLICACION / VENTANA
  //Minimizar ventana
  public void minimize() {
    Stage stage = (Stage) panel_home.getScene().getWindow();
    stage.setIconified(true);
  }

  //Cerar aplicación
  public void close() {
    System.exit(0);
  }

  //Cambiar entre paneles
  public void switchForm(ActionEvent event) {

    Object source = event.getSource();

    if (source == btn_navbar_home) {
      panel_home.setVisible(true);
      panel_orders.setVisible(false);
      panel_addProducts.setVisible(false);
      btn_navbar_home.setStyle("-fx-background-color: linear-gradient(to bottom right, #25a473, #89892b)");
      btn_navbar_orders.setStyle("-fx-background-color: transparent");
      btn_navbar_addProducts.setStyle("-fx-background-color: transparent");

    } else if (source == btn_navbar_addProducts) {
      panel_home.setVisible(false);
      panel_orders.setVisible(false);
      panel_addProducts.setVisible(true);
      btn_navbar_addProducts.setStyle("-fx-background-color: linear-gradient(to bottom right, #25a473, #89892b)");
      btn_navbar_orders.setStyle("-fx-background-color: transparent");
      btn_navbar_home.setStyle("-fx-background-color: transparent");
      addProductShowListData();
      loadCboxCategories();


    } else if (source == btn_navbar_orders) {
      panel_home.setVisible(false);
      panel_orders.setVisible(true);
      panel_addProducts.setVisible(false);
      btn_navbar_orders.setStyle("-fx-background-color: linear-gradient(to bottom right, #25a473, #89892b)");
      btn_navbar_home.setStyle("-fx-background-color: transparent");
      btn_navbar_addProducts.setStyle("-fx-background-color: transparent");
    }
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


  //METODOS DE INICIO -> Carga de datos
  //1.Metodo que carga las categorias en el combobox al cargar el panel
  //Cargar el cbox de categories al aparecer el panel
  public void loadCboxCategories() {
    cbox_addProduct_category.setItems(FXCollections.observableArrayList(categoryService.findAllCategories()));
  }

  //2. Metodo que agrega todos los productos al tblview
  public void addProductShowListData() {
    addProductList = FXCollections.observableArrayList(productService.findAll());
    tbcol_addProducts_codProd.setCellValueFactory(new PropertyValueFactory<>("code"));
    tbcol_addProducts_category.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
    tbcol_addProducts_nameProd.setCellValueFactory(new PropertyValueFactory<>("name"));
    tbcol_addProducts_description.setCellValueFactory(new PropertyValueFactory<>("description"));
    tbcol_addProducts_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    tbcol_addProducts_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
    tbView_addProducts.setItems(addProductList);

  }


  //Obtiene un optional del producto seleccionado
  public Optional<Product> getProductSelected() {
    Product p = tbView_addProducts.getSelectionModel().getSelectedItem();
    if (p == null) {
      return Optional.empty();
    }
    logger.info(p.getName());
    return Optional.of(p);
  }

  //Muestra el producto seleccionado
  public void showProductsSelected() {

    Optional<Product> optionalProduct = getProductSelected();
    if (optionalProduct.isPresent()) {
      final Product p = optionalProduct.get();
      txt_addProduct_codProd.setText(p.getCode());
      txta_addProduct_description.setText(p.getDescription());
      txt_addProduct_nameProd.setText(p.getName());
      txt_addProduct_Price.setText(Double.toString(p.getPrice()));
      txt_addProduct_stock.setText(Integer.toString(p.getStock()));

      String uri = "file:" + p.getImage();
      image = new Image(uri, 115, 128, false, true);
      imView_addProduct.setImage(image);
      path = p.getImage();

      Category cat = cbox_addProduct_category.getItems().stream()
              .filter(c -> c.getId().equals(p.getCategoryId()))
              .findFirst()
              .orElse(null);
      cbox_addProduct_category.getSelectionModel().select(cat);
    }

  }

  //Limpiar campos en inputs de AddProduct
  public void resetNewProduct() {
    txt_addProduct_Price.setText("");
    txt_addProduct_codProd.setText("");
    txt_addProduct_nameProd.setText("");
    txta_addProduct_description.setText("");
    txt_addProduct_stock.setText("");
    cbox_addProduct_category.getSelectionModel().clearSelection();
    imView_addProduct.setImage(null);
  }

  public void deleteProduct() {
    Optional<Product> optU = getProductSelected();
    if (optU.isPresent()) {
      //Obtenemos el id del producto seleccionado
      Long id = optU.get().getId();

      //Mostramos alert de confirmación
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Delete Product");
      alert.setHeaderText(null);
      alert.setContentText("Are you sure you want to delete this product?");
      //Si usuario confirma, eliminamos producto
      alert.showAndWait().ifPresent(respo -> {
        if (respo == ButtonType.OK) {
          productService.delete(id);
          addProductShowListData();
          resetNewProduct();
        }
      });
    }

  }

  public boolean validateAddProductInput() {

    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error Message");
    alert.setHeaderText(null);

    String code = txt_addProduct_codProd.getText();
    String name = txt_addProduct_nameProd.getText();
    String desc = txta_addProduct_description.getText();
    String priceText = txt_addProduct_Price.getText();
    String stockText = txt_addProduct_stock.getText();
    boolean cboxSelected = cbox_addProduct_category.getSelectionModel().getSelectedItem() == null;

    //Validar campos vacios
    if (code.isBlank() || name.isBlank() || desc.isBlank() || priceText.isBlank() || cboxSelected || stockText.isBlank()) {
      alert.setContentText("Please fill all blank fields");
      alert.showAndWait();
      return false;
    }
    if(newImagePath == null){
      alert.setContentText("Please select a valid picture");
      alert.showAndWait();
      return false;
    }
    try {
      Double.valueOf(txt_addProduct_Price.getText());
      Long.valueOf(cbox_addProduct_category.getValue().getId());
      Integer.valueOf(txt_addProduct_stock.getText());
    } catch (NumberFormatException e) {
      alert.setContentText("Please introduce number in the price and stock inputs");
      alert.showAndWait();
      return false;
    }
    return true;
  }

  //Coger datos de campos en inputs de AddProduct y crear un product
  public void createNewProduct() {
    if (!validateAddProductInput()) return;

    String code = txt_addProduct_codProd.getText();
    String name = txt_addProduct_nameProd.getText();
    String desc = txta_addProduct_description.getText();
    double price = Double.valueOf(txt_addProduct_Price.getText());
    Long categoryId = Long.valueOf(cbox_addProduct_category.getValue().getId());
    int stock = Integer.valueOf(txt_addProduct_stock.getText());

    String uri = newImagePath;
    uri = uri.replace("\\", "\\\\");
    Product p = new Product();
    p.setName(name);
    p.setCode(code);
    p.setDescription(desc);
    p.setPrice(price);
    p.setCategoryId(categoryId);
    p.setImage(uri);
    p.setStock(stock);
    p = productService.save(p);
    if (p.getId() != null) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Confirmation Message");
      alert.setHeaderText(null);
      alert.setContentText("The product has been created correctly");
      alert.showAndWait();
      resetNewProduct();
      addProductShowListData();
    }



    //If uri is null exception not controlled
}

//Seleccionar una imagen del explorador del equipo y guardarla en variable static "path"
public void addProductsImportImage() {
  FileChooser open = new FileChooser();
  open.setTitle("Open Image File");
  open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png"));

  File file = open.showOpenDialog(panel_main.getScene().getWindow());
  if (file != null) {
    newImagePath = file.getPath();
    image = new Image(file.toURI().toString(), 115, 128, false, true);
    imView_addProduct.setImage(image);
  }
}

//Update image
public void updateProduct() {
    //Actualmente el metodo validate no funciona porque cuando lo invoco al hacer un update product me peta la aplicacion
  if(validateAddProductInput()) return;
  String code = txt_addProduct_codProd.getText();
  String name = txt_addProduct_nameProd.getText();
  String desc = txta_addProduct_description.getText();
  double price = Double.valueOf(txt_addProduct_Price.getText());
  Long categoryId = Long.valueOf(cbox_addProduct_category.getValue().getId());
  int stock = Integer.valueOf(txt_addProduct_stock.getText());

  String uri = newImagePath;
  uri = uri.replace("\\", "\\\\");
  Product p = new Product();
  p.setName(name);
  p.setCode(code);
  p.setDescription(desc);
  p.setPrice(price);
  p.setCategoryId(categoryId);
  p.setImage(uri);
  p.setStock(stock);
  boolean updated = productService.update(p);
  Alert alert;
  if (updated) {
    alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Confirmation Message");
    alert.setHeaderText(null);
    alert.setContentText("The product has been updated correctly");
    alert.showAndWait();
    resetNewProduct();
    addProductShowListData();
  }else{
    alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error Message");
    alert.setHeaderText(null);
    alert.setContentText("Something went wrong");
    alert.showAndWait();
  }
}


@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
}

public void postInit() {
  addProductShowListData();
  loadCboxCategories();

  //setupTblViewSelectionLstnr();
  //Este será el lstnr para obtener los datos de la tabla.
}


}
