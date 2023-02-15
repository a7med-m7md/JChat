package Client.network;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.Socket;

public class ClientThreadHandled implements Runnable {
    Socket clientSocket;
    long userId;
    DataOutputStream dataOutputStream = null;
    DataInputStream dataInputStream = null;

    public ClientThreadHandled(Socket clientSocket, long userId) {
        this.clientSocket = clientSocket;
        this.userId = userId;
        try {
            dataInputStream = new DataInputStream(
                    clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(
                    clientSocket.getOutputStream());
            dataOutputStream.writeLong(userId);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            closeResources();
        }

    }


    @Override
    public void run() {
        while (clientSocket.isConnected()) {
            try {
                if (dataInputStream == null)
                    System.out.println("data inout stream is null in client");
                boolean isStop = dataInputStream.readBoolean();
                System.out.println("is Stop in client is -> "+isStop);
                if (isStop) {
                    closeResources();
                    break;
                }
                int fileNameLength = dataInputStream.readInt();
                if (fileNameLength > 0) {
                    byte[] fileNameBytes = new byte[fileNameLength];
                    dataInputStream.readFully(fileNameBytes, 0, fileNameBytes.length);
                    String fileName = new String(fileNameBytes);
                    long fileContentLength = dataInputStream.readLong();
                    if (fileContentLength > 0) {
                        int fileId = 10;
                        //TODO -> create path for the received file in client
                        String path = "F:\\testt" + "\\" + fileName + "." + getFileExtension(fileName);
                        File receivedLocalFile = new File(path);
                        receivedLocalFile.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(receivedLocalFile);
                        //TODO -> with unlimited size
                        int readBytes = 0;
                        byte[] buffer = new byte[4 * 1024];
                        while (fileContentLength > 0
                                && (readBytes = dataInputStream.read(
                                buffer, 0,
                                (int) Math.min(buffer.length, fileContentLength)))
                                != -1) {
                            // Here we write the file using write method
                            fileOutputStream.write(buffer, 0, readBytes);
                            fileContentLength -= readBytes;
                            //fileOutputStream.flush();
                        }
                        fileOutputStream.close();
                        System.out.println("success save local file");
                        Platform.runLater(
                                () -> {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.initStyle(StageStyle.UTILITY);
                                    alert.setTitle("Message");
                                    alert.setHeaderText("Success");
                                    alert.setContentText(
                                            "You received a file in your downloads folder...");
                                    ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
                                    alert.show();
                                }
                        );
                        System.out.println("after panel");
                        /*
                        FileEntity fileEntity = new FileEntity(fileId, fileName, fileContentBytes, getFileExtension(fileName));
                        System.out.println("the received file name -> " + fileEntity.getName());*/
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
                closeResources();
                break;
            }
        }
    }

    public void stopClient() {
        try {
            System.out.println("start close the client from stop client innnnn thread");
            this.dataOutputStream.writeBoolean(true);
            this.dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            //closeResources();
        }
        //closeResources();
    }

    public static String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i + 1);
        } else {
            return "No extension found.";
        }
    }

    public void sendFile(File fileToSend, long receiverId) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileToSend);
            String fileName = fileToSend.getName();
            byte[] fileNameBytes = fileName.getBytes();
            dataOutputStream.writeBoolean(false);
            dataOutputStream.writeLong(receiverId);
            dataOutputStream.writeInt(fileNameBytes.length);
            dataOutputStream.write(fileNameBytes);
            dataOutputStream.writeLong(fileToSend.length());
            System.out.println("send operation in client side with fileName ->" + fileName);
            int readBytes = 0;
            byte[] buffer = new byte[4 * 1024];
            while ((readBytes = fileInputStream.read(buffer))
                    != -1) {
                dataOutputStream.write(buffer, 0, readBytes);
                //dataOutputStream.flush();
            }
            dataOutputStream.flush();
            System.out.println("in client -> finish send file to server fileName ->" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            closeResources();
        }

    }

    public void closeResources() {
        try {
            //this.socket.close();
            if (clientSocket != null)
                clientSocket.close();
            if (dataInputStream != null)
                this.dataInputStream.close();
            if (dataOutputStream != null)
                this.dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
