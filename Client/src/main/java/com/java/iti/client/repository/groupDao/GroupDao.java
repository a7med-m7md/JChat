package com.java.iti.client.repository.groupDao;

import com.java.iti.client.model.group.Group;
import com.java.iti.client.repository.CRUDOperation;
import com.java.iti.client.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupDao implements CRUDOperation<Group> {
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private final Connection connection = connectionManager.getConnection();

    @Override
    public List<Group> findAll() {
        List<Group> groupList = new ArrayList<>();
        final String SQL = "SELECT * FROM jtalk.groups";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    Time createdAt = resultSet.getTime(4);
                    long owner_id = resultSet.getLong(5);
                    Group group = new Group(name, description, owner_id);
                    groupList.add(group);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connectionManager.close();
        }
        return groupList;
    }

    @Override
    public Optional<Group> findById(int id) {
        final String SQL = "SELECT * FROM jtalk.groups WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    int gid = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    Time createdAt = resultSet.getTime(4);
                    long owner_id = resultSet.getLong(5);
                    Group group = new Group(name, description, owner_id);
                    return Optional.of(group);
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
    public Optional<Group> update(Group entity, int id) {
        final String SQL = "UPDATE jtalk.groups SET name = ? , descrition = ? WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setInt(3, id);
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
    public Group save(Group entity) {
        final String SQL = "INSERT INTO jtalk.groups (name, description, owner_id) VALUES (?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setLong(3, entity.getOwner_id());
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
        final String SQL = "DELETE FROM jtalk.groups WHERE id = ?";
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
