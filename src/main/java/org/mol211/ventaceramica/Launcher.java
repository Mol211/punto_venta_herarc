package org.mol211.ventaceramica;

import javafx.application.Application;
import org.mol211.ventaceramica.model.Category;
import org.mol211.ventaceramica.model.Product;
import org.mol211.ventaceramica.persistence.CategoryDAO;
import org.mol211.ventaceramica.persistence.ProductDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Launcher {

    private static final Logger log = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        log.info("Iniciando Launcher");
        Application.launch(HelloApplication.class, args);
        CategoryDAO cDAO = new CategoryDAO();
        Category c = new Category();
        c.setName("Vasos");
        c.setDescription("Recipientes más grandes que las tazas, ideales para un gazpacho bien fresquito en verano");
//        Category category = cDAO.save(c);
//        log.info(category.toString());
        ProductDAO dao = new ProductDAO();
        Product p = new Product();
//        p.setCode("VA_001");
//        p.setName("Vaso Setas");
//        p.setCategoryId(1L);
//        p.setDescription("Magnifica vaso de la colección Setas. De tamaño medio destaca por su calidez y comodidad");
//        p.setPrice(12.00);
//        p.setStock(5);
//        p = dao.save(p);
//        log.info(p.toString());
        List<Category> categories = cDAO.findAll();
        for(Category cat: categories) {
            log.info(cat.toString());
        }
    }
}
