package org.mol211.ventaceramica.service;

import org.mol211.ventaceramica.persistence.UserDAO;

public class UserService {
  private final UserDAO uDAO;

  public UserService(UserDAO uDAO) {
    this.uDAO = uDAO;
  }
  public boolean login(String username, String password){
    return uDAO.login(username, password);
  }
}
