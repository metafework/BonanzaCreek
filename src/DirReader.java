import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.net.URI;
import java.net.URL;

public class DirReader {

    private File WD; //WD stands for Working directory
    private File [] contentsOfWD;
    private int fileCount;
    private int dirCount;
    private ArrayList<File> dirsInWD;
    private ArrayList<File> filesInWD;

    public DirReader(){
        this.WD= new File(System.getProperty("user.dir"));
        this.contentsOfWD=new File [(int)WD.length()];
        this.contentsOfWD=WD.listFiles();
        this.dirsInWD=new ArrayList<>();
        this.filesInWD=new ArrayList<>();
        this.fileCount=0;
        this.dirCount=0;
        for(File file : contentsOfWD){
            if (file.isFile()){
                fileCount++;
                this.filesInWD.add(file);
            }else if (file.isDirectory()){
                dirCount++;
                this.dirsInWD.add(file);
            }
        }
    }
    public File getWD() {
        return WD;
    }

    public int getFileCount() {
        return fileCount;
    }

    public int getDirCount() {
        return dirCount;
    }

    public ArrayList<String> getFileNames(){
        ArrayList <String> fileNames=new ArrayList<>();
        for(File file:filesInWD){
            fileNames.add(file.getName());
        }
        return fileNames;
    }
    public ArrayList<String> getDirNames(){
        ArrayList <String> fileNames=new ArrayList<>();
        for(File file:dirsInWD){
            fileNames.add(file.getName());
        }
        return fileNames;
    }



    public static void main(String [] args) {
        DirReader test=new DirReader();
        System.out.print("Path to CWD: "+test.getWD().getAbsolutePath()+"\n");
        System.out.print("# of files: "+test.getFileCount()+"\n");
        System.out.print("# of dirs: "+test.getDirCount()+"\n");
        System.out.print("Names of Dirs: "+test.getDirNames()+"\n");
        System.out.print("Names of files: "+test.getFileNames()+"\n");
        System.out.print("URL/URI: ");
    }

}