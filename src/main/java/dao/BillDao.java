package dao;

import model.Bill;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDao {
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/last";
    private static final String SELECT_BILL = "select * from bills where bill_id = ?;";
    private static final String IF_ISSET_BILL = "select count(bill_id) as res from bills where bill_id = ?;";
    private static final String SELECT_ALL_BILLS = "select * from bills;";
    private static final String INSERT_BILL = "insert into bills values (null,?,?);";
    private static final String GIVE_MONEY = "update bills set money = ? where user_id = ?;";


    public Bill getBillById (long id){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(SELECT_BILL);
            statement.setString(1, Long.toString(id));
            ResultSet result = statement.executeQuery();
            result.next();
            return convertToBill(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean ifIssetBill (long id){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(IF_ISSET_BILL);
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

    private Bill convertToBill(ResultSet result) throws SQLException{
        long id = result.getLong("bill_id");
        long user_id = result.getLong("user_id");
        BigDecimal money = result.getBigDecimal("money");

        return new Bill(id, user_id, money);
    }

    public void createBill (Bill bill){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(INSERT_BILL);
            statement.setLong(1, bill.getUser_id());
            statement.setBigDecimal(2, bill.getMoney());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void giveMoney (long user_id, BigDecimal money){
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                PreparedStatement statement = connection.prepareStatement(GIVE_MONEY);
                statement.setBigDecimal(1, money);
                statement.setLong(2, user_id);

                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    public List<Bill> getAllBills(){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ALL_BILLS);

            List<Bill> bills = new ArrayList<>();
            while (result.next()){
                bills.add(convertToBill(result));
            }


            return bills;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
