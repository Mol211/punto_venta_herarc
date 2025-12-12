package org.mol211.ventaceramica.service;

import org.mol211.ventaceramica.model.Category;
import org.mol211.ventaceramica.persistence.CategoryDAO;

import java.util.List;

public class CategoryService {
  private final CategoryDAO categoryDAO;
  public CategoryService(CategoryDAO dao){
    this.categoryDAO = dao;
  }
  public List<Category> findAllCategories() {
    return categoryDAO.findAll();
  }
}
