package Server;


import Server.persistance.ConnectionManager;
import Server.persistance.dao.GroupDao;
import model.UtilityClass;
import Server.network.RMIConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Server.business.model.group.Group;
import model.group.GroupEntity;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class Main extends Application {
    Connection connection;
    Server.network.RMIConnection rmiConnection;

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(UtilityClass.isNull());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("JChat Server!");


//        UserDto userDto = new UserDto(
//                "02211014",
//                "mohamed mahmoud",
//                "mohamed99@gmail",
//                "picture str path",
//                "1234password",
//                Gender.MALE,
//                "egypt",
//                "1999-02-20",
//                "software engineer",
//                UserStatus.AVAILABLE
//        );
//        //TODO map from domain user to user entity
//        UserDao userDao = new UserDao();
//        UserMapper userMapper = new UseMapperImpl();
//        UserEntity res = userDao.save(userMapper.domainToEntity(userDto));
//        System.out.println(res.getStatus().name());
//        try{
//            UserFriendDao userFriendDao = new UserFriendDao();
//            userFriendDao.getFriendList("01112175312").forEach(friendEntity -> {
//                System.out.println(friendEntity.getName());
//                System.out.println(friendEntity.getMobile());
////                System.out.println(friendEntity.getStatus());
//                System.out.println(friendEntity.getUserPhoto());
//            });
//
//        }catch (Exception ex){}

//        try {
//            //Test List Group
//            GroupDao dao = new GroupDao();
//            List<GroupEntity> groups = dao.getUserGroups(5);
//            System.out.println(groups.size());
//            for (GroupEntity group : groups) {
//                System.out.println("Name : " + group.getName() +
//                        "\nDescription : " + group.getDescription() +
//                        "\nDate : " + group.getCreatedAt() +
//                        "\nCreated By : " + group.getOwner_id());
//            }
//            // Test Create Group
//            Group group = new Group("Fridens Group" , "Welcome My Friends",1);
//            dao.save(group);
//        } catch (Exception exception) {
//        }

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() throws Exception {
        rmiConnection = new RMIConnection();
        rmiConnection.startServices();
        rmiConnection.connected();
        ConnectionManager.getInstance().getConnection();
    }

    @Override
    public void stop() throws Exception {
        rmiConnection.disconnect();
        ConnectionManager.getInstance().close();
    }
}