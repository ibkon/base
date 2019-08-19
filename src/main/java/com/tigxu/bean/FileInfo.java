package com.tigxu.bean;

public class FileInfo {
    private String  hash;
    private String  relativePath;
    private String  absolutePath;
    private boolean isFile;
    public FileInfo(){}
    public FileInfo(String hash, String relativePath, String absolutePath, boolean isFile) {
        this.hash = hash;
        this.relativePath = relativePath;
        this.absolutePath = absolutePath;
        this.isFile = isFile;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    @Override
    public String toString() {
        StringBuilder   stringBuilder   = new StringBuilder();
        stringBuilder.append("FileInfo{");
        if(hash!=null)stringBuilder.append("hash='" + hash + "\',");
        if(relativePath!=null)stringBuilder.append(" relativePath='" + relativePath + "\',");
        if(absolutePath!=null)stringBuilder.append(" absolutePath='" + absolutePath + "\',");
        stringBuilder.append(" isFile=" + isFile +'}');
        return stringBuilder.toString();
    }
}
