package org.mol211.ventaceramica.persistence;

public class CustomerDAO {
    //Metodos CRUD
    //FindAll
    //FindById
    //Save
    //Update
    //Delete
    //Metodos espec
    //findByEmail
    //findByName -> Lista con mismo name
    //emailExists(mail) -> boolean
    private final String SQL_FIND_ALL = "SELECT id, name, mail, " +
            "phone, created_at FROM customers ORDER BY name";
    private final String SQL_FIND_BY_ID = "SELECT id, name, mail, phone, created_at FROM customers WHERE id = ?";
    private final String SQL_FIND_BY_NAME ="SELECT id, name, mail, phone, created_at FROM customers WHERE name ILIKE ? ORDER BY name";
    private final String SQL_FIND_BY_MAIL = "SELECT id, name, mail, phone, created_at FROM cutomers WHERE mail = ?";
    private final String SQL_SAVE = "INSERT INTO costumers (name, mail, phone, created_at) VALUES (?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE costumers SET name = ?, mail = ?, phone = ?";
    private final String SQL_DELETE = "DELETE FROM costumers WHERE id = ?";
    private final String SQL_EMAIL_EXISTS = "SELECT COUNT(*) FROM costumers WHERE mail = ?";



}
