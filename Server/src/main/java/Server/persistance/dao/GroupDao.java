package Server.persistance.dao;


import Server.business.services.ConnectedService;
import model.FriendEntity;
import model.Group;
import model.GroupMember;
import Server.persistance.ConnectionManager;
import Server.persistance.CRUDOperation;
import model.user.UserEntity;

import java.rmi.RemoteException;
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    Time createdAt = resultSet.getTime(4);
                    String owner_mobile = resultSet.getString(5);
                    Group group = new Group(name, description, owner_mobile);
                    groupList.add(group);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupList;
    }

    @Override
    public Optional<Group> findById(int id) {
        final String SQL = "SELECT * FROM jtalk.groups WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int gid = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    Time createdAt = resultSet.getTime(4);
                    String owner_mobile = resultSet.getString(5);
                    Group group = new Group(name, description, owner_mobile);
                    return Optional.of(group);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Group> update(Group entity, String id) {
        return Optional.empty();
    }

    @Override
    public Optional<Group> update(Group entity, int id) {
        final String SQL = "UPDATE jtalk.groups SET name = ? , descrition = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setInt(3, id);
            int n = preparedStatement.executeUpdate();
            if (n > 0) {
                return Optional.of(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Group save(Group entity) {
        final String SQL = "INSERT INTO jtalk.groups (name, description, owner_mobile, picture_g) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setString(3, entity.getOwner_mobile());
            preparedStatement.setBytes(4, entity.getPicture());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    // add the auto-generated ID to the class.
                    int id = generatedKeys.getInt(1);
                    entity.setId(id);
                    GroupMemberDao groupMemberDao = new GroupMemberDao();
                    // Save Me as group member in DB.
                    groupMemberDao.save(new GroupMember(entity.getOwner_mobile(), id));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public int delete(int id) {
        final String SQL = "DELETE FROM jtalk.groups WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<GroupMember> getUsersInGroup(int id) {
        List<GroupMember> groupList = new ArrayList<>();
//        final String SQL = "select jtalk.groups.*\n" +
//                "from jtalk.users , jtalk.group_members , jtalk.groups\n" +
//                "where jtalk.users.id = jtalk.group_members.user_id and jtalk.groups.id = jtalk.group_members.group_id\n" +
//                "and jtalk.users.id = ? \n" +
//                "group by jtalk.group_members.group_id , jtalk.groups.id;";
        final String SQL = "select * FROM jtalk.group_members , jtalk.users " +
                "WHERE user_mobile = mobile AND group_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String mobile = resultSet.getString("mobile");
                    GroupMember group = new GroupMember(mobile, id);
                    groupList.add(group);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupList;
    }

    public List<Group> getAllMyGroups(String mobile) {
        UserDao userDao = new UserDao();
        System.out.println("Current mob:: " + mobile);
        final String SQL = "SELECT * FROM jtalk.groups , jtalk.group_members where id = group_id AND user_mobile = ?" ;
        List<Group> listGroups = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, mobile);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int gid = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    Time createdAt = resultSet.getTime(4);
                    String owner_mobile = resultSet.getString(5);
                    byte[] picture = resultSet.getBytes("picture_g");
                    Group group = new Group(name, description, owner_mobile);
                    group.setPicture(picture);

                    // For each group I want to add his members
                    List<GroupMember> members = getUsersInGroup(gid);
                    List<FriendEntity> currentGroupMembers = new ArrayList<>();
                    members.forEach(member-> {
                        Optional<UserEntity> currentMember = userDao.findByMobile(member.getUserMobile());
                        currentGroupMembers.add(new FriendEntity(currentMember.get().getMobile(), currentMember.get().getName(), currentMember.get().getBio(), currentMember.get().getStatus(), currentMember.get().getPicture()));
                    });
                    group.setListMembers(currentGroupMembers);
                    listGroups.add(group);
                }
//            preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listGroups;
    }
}

