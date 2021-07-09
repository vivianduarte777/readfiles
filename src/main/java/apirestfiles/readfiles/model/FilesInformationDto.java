package apirestfiles.readfiles.model;

public class FilesInformationDto {
    private String extension = null;
    private int count = 0;
    private int lines = 0;
    private int bytes = 0;

    public FilesInformationDto(String extension, int count, int lines, int bytes) {
        this.extension = extension;
        this.count = count;
        this.lines = lines;
        this.bytes = bytes;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }
}