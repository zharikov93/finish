package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/last";
    private static final String SELECT_USER = "select * from users where user_id = ?;";
    private static final String IF_ISSET_USER = "select count(user_id) as res from users where user_id = ?;";
    private static final String SELECT_ALL_USERS = "select * from users;";
    private static final String INSERT_USER = "insert into users values (null,?,?,?);";


    public User getUserById (long id){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(SELECT_USER);
            statement.setString(1, Long.toString(id));
            ResultSet result = statement.executeQuery();
            result.next();
            return convertToUser(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean ifIssetUser (long id){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(IF_ISSET_USER);
            statement.setString(1, Long.toString(id));
            ResultSet result = statement.executeQuery();
            result.next();
            int isRes = result.getInt("res");
            if (isRes >= 1){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User convertToUser(ResultSet result) throws SQLException{
        long id = result.getLong("user_id");
        String fname = result.getString("fname");
        String lname = result.getString("lname");
        int age = result.getInt("age");

        return new User(id, fname, lname, age);
    }

    public void createUser (User user){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, user.getfName());
            statement.setString(2, user.getlName());
            statement.setInt(3, user.getAge());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers(){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ALL_USERS);

            List<User> users = new ArrayList<>();
            while (result.next()){
                users.add(convertToUser(result));
            }


            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
