package Server.persistance.dao;

import Server.business.model.user.Gender;
import Server.business.model.user.UserStatus;
import Server.network.interfaces.LoginEntity;
import Server.persistance.ConnectionManager;
import Server.persistance.CRUDOperation;
import Server.persistance.entities.UserEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements CRUDOperation<UserEntity> {
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private final Connection connection = connectionManager.getConnection();
    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> userList = new ArrayList<>();
        final String SQL = "SELECT * FROM jtalk.users";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    String mobile = resultSet.getString(2);
                    String name = resultSet.getString(3);
                    String email = resultSet.getString(4);
                    String picture = resultSet.getString(5);
                    String password = resultSet.getString(6);
                    Gender gender = Gender.valueOf(resultSet.getString(7));
                    String country = resultSet.getString(8);
                    String dateOfBirth = resultSet.getString(9);
                    String bio = resultSet.getString(10);
                    UserStatus userStatus = UserStatus.valueOf(resultSet.getString(11));
                    UserEntity newUser = new UserEntity(mobile,name,email, picture,password,gender,country,dateOfBirth,bio,userStatus);
                    userList.add(newUser);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connectionManager.close();
        }
        return userList;
    }

    @Override
    public Optional<UserEntity> findById(int id) {
        final String SQL = "SELECT * FROM jtalk.users WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    String mobile = resultSet.getString(2);
                    String name = resultSet.getString(3);
                    String email = resultSet.getString(4);
                    String picture = resultSet.getString(5);
                    String password = resultSet.getString(6);
                    Gender gender = Gender.valueOf(resultSet.getString(7));
                    String country = resultSet.getString(8);
                    String dateOfBirth = resultSet.getString(9);
                    String bio = resultSet.getString(10);
                    UserStatus userStatus = UserStatus.valueOf(resultSet.getString(11));
                    UserEntity newUser = new UserEntity(mobile,name,email, picture,password,gender,country,dateOfBirth,bio,userStatus);
                    return Optional.of(newUser);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connectionManager.close();
        }
        return Optional.empty();
    }


    public Optional<UserEntity> userLogin(LoginEntity loginEntity) {
        final String SQL = "SELECT * FROM jtalk.users WHERE mobile = ? AND password = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setString(1, loginEntity.getMobile());
            preparedStatement.setString(2, loginEntity.getPassword());
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    String mobile = resultSet.getString(2);
                    String name = resultSet.getString(3);
                    String email = resultSet.getString(4);
                    String picture = resultSet.getString(5);
                    String password = resultSet.getString(6);
                    Gender gender = Gender.valueOf(resultSet.getString(7).toUpperCase());
                    String country = resultSet.getString(8);
                    String dateOfBirth = resultSet.getString(9);
                    String bio = resultSet.getString(10);
                    UserStatus userStatus = UserStatus.valueOf(resultSet.getString(11).toUpperCase());
                    UserEntity newUser = new UserEntity(mobile,name,email, picture,password,gender,country,dateOfBirth,bio,userStatus);
                    return Optional.of(newUser);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connectionManager.close();
        }
        return Optional.empty();
    }


    @Override
    public Optional<UserEntity> update(UserEntity entity, int id) {
        final String SQL = "UPDATE jtalk.users SET" +
                "mobile = ?," +
                "name = ?," +
                "email=?," +
                "picture=?," +
                "password=?," +
                "gender=?," +
                "country=?," +
                "dateOfBirth=?," +
                "bio=?," +
                "status=?," +
                " WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setString(1,entity.getMobile());
            preparedStatement.setString(2,entity.getName());
            preparedStatement.setString(3,entity.getEmail());
            preparedStatement.setString(4,entity.getPicture());
            preparedStatement.setString(5,entity.getPassword());
            preparedStatement.setString(6,entity.getGender().name());
            preparedStatement.setString(7,entity.getCountry());
            preparedStatement.setString(8,entity.getDateOfBirth());
            preparedStatement.setString(9,entity.getBio());
            preparedStatement.setString(10,entity.getStatus().name());
            preparedStatement.setInt(11,id);
            int n = preparedStatement.executeUpdate();
            if(n > 0){
                return Optional.of(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connectionManager.close();
        }
        return Optional.empty();
    }

    @Override
    public UserEntity save(UserEntity entity) {
        final String SQL = "INSERT INTO jtalk.users (mobile,name,email,picture,password,gender,country,dateOfBirth,bio,status) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setString(1,entity.getMobile());
            preparedStatement.setString(2,entity.getName());
            preparedStatement.setString(3,entity.getEmail());
            preparedStatement.setString(4,entity.getPicture());
            preparedStatement.setString(5,entity.getPassword());
            preparedStatement.setString(6,entity.getGender().name());
            preparedStatement.setString(7,entity.getCountry());
            preparedStatement.setString(8,entity.getDateOfBirth());
            preparedStatement.setString(9,entity.getBio());
            preparedStatement.setString(10,entity.getStatus().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connectionManager.close();
        }
        return entity;
    }

    @Override
    public void delete(int id) {
        final String SQL = "DELETE FROM jtalk.users WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connectionManager.close();
        }
    }
}
