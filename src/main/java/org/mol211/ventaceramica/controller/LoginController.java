package org.mol211.ventaceramica.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mol211.ventaceramica.persistence.CategoryDAO;
import org.mol211.ventaceramica.persistence.ProductDAO;
import org.mol211.ventaceramica.persistence.UserDAO;
import org.mol211.ventaceramica.service.CategoryService;
import org.mol211.ventaceramica.service.IProductService;
import org.mol211.ventaceramica.service.ProductServiceImpl;
import org.mol211.ventaceramica.service.UserService;
import org.mol211.ventaceramica.util.FXUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @FXML
    private Button btn_close;

    @FXML
    private Button btn_login;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField txt_password;

    @FXML
    private TextField txt_username;

    private UserService userService;
    private IProductService productService;
    private CategoryService categoryService;

    public LoginController(){
        UserDAO uDAO = new UserDAO();
        ProductDAO pDAO = new ProductDAO();
        CategoryDAO cDAO = new CategoryDAO();
        this.userService = new UserService(uDAO);
        this.productService = new ProductServiceImpl(pDAO);
        this.categoryService = new CategoryService(cDAO);
    }
    public void login() throws IOException {
        Alert alert;
        String pass = txt_password.getText();
        String name = txt_username.getText();
        boolean logined = userService.login(name, pass);
        logger.info("Reultado del login: "+logined);
        if(pass.isEmpty() || name.isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }else{
            if(logined){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Login!!");
                alert.showAndWait();

                btn_login.getScene().getWindow().hide();


                FXMLLoader loader =  new FXMLLoader(LoginController.class.getResource("/org/mol211/ventaceramica/dashboard.fxml"));
                Parent root = loader.load();
                DashboardController controller = loader.getController();
                controller.setServices(productService,categoryService);
                controller.postInit();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                FXUtils.enableDrag(root,stage);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Credentials");
                alert.showAndWait();
            }
        }

    }

    public void close() {
        System.exit(0);
    }

}
