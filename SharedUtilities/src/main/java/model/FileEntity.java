package model;

import java.io.File;

public class FileEntity {
    private int id;
    private String name;
    private byte[] content;
    private File file;
    private String fileExtension;

    public FileEntity(int id, String name, byte[] content, String fileExtension) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.fileExtension = fileExtension;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
