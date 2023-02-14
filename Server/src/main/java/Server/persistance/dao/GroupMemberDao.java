package Server.persistance.dao;

import Server.business.services.ConnectedService;
import model.GroupMember;
import Server.persistance.ConnectionManager;
import Server.persistance.CRUDOperation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupMemberDao implements CRUDOperation<GroupMember> {
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private final Connection connection = connectionManager.getConnection();

    @Override
    public List<GroupMember> findAll() {
        List<GroupMember> groupMembersList = new ArrayList<>();
        final String SQL = "SELECT * FROM jtalk.group_members";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    String user_mobile = resultSet.getString(1);
                    long group_id = resultSet.getLong(2);
                    GroupMember groupMember = new GroupMember(user_mobile, group_id);
                    groupMembersList.add(groupMember);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupMembersList;
    }

    @Override
    public Optional<GroupMember> findById(int id) {
        final String SQL = "SELECT * FROM jtalk.group_members WHERE user_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    String user_mobile = resultSet.getString(1);
                    long group_id = resultSet.getLong(2);
                    GroupMember groupMember = new GroupMember(user_mobile, group_id);
                    return Optional.of(groupMember);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<GroupMember> update(GroupMember entity, String id) {
        return Optional.empty();
    }

    @Override
    public Optional<GroupMember> update(GroupMember entity, int id) {
//        final String SQL = "UPDATE jtalk.group_members SET user_id = ? , group_id = ? WHERE id = ?";
//        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
//            preparedStatement.setString(1, entity.getName());
//            preparedStatement.setString(2, entity.getDescription());
//            preparedStatement.setInt(3, id);
//            int n = preparedStatement.executeUpdate();
//            if(n > 0){
//                return Optional.of(entity);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            connectionManager.close();
//        }
        return Optional.empty();
    }

    @Override
    public GroupMember save(GroupMember entity) {
        final String SQL = "INSERT INTO jtalk.group_members (user_mobile, group_id) VALUES (?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setString(1, entity.getUserMobile());
            preparedStatement.setLong(2, entity.getGroupId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public int delete(int id) {
        final String SQL = "DELETE FROM jtalk.group_members WHERE user_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

