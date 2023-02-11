//package Client.network.services;
//
//import exceptions.UserNotFoundException;
//import services.ChatService;
//
//import java.net.MalformedURLException;
//import java.rmi.Naming;
//import java.rmi.NotBoundException;
//import java.rmi.RemoteException;
//import java.rmi.server.UnicastRemoteObject;
//
//public class ClientCommunicationServices extends UnicastRemoteObject implements ChatService {
//    ChatService chatService;
//    public ClientCommunicationServices() throws RemoteException {
//    }
//
//    @Override
//    public void friendRequest(String sender, String receiver) throws RemoteException{
//
//        try {
//            chatService = (ChatService) Naming.lookup("rmi://localhost:2233/friendRequest");
//        } catch (NotBoundException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        chatService.friendRequest(sender, receiver);
//        System.out.println("Request send from : " + sender + " to : " + receiver);
//    }
//}
