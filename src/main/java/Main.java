import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        GameProgress save1 = new GameProgress(99, 2, 10, 1234567);
        GameProgress save2 = new GameProgress(50, 5, 21, 1331231231);
        GameProgress save3 = new GameProgress(31, 1, 55, 312313121);

        saveGame("D://games/savegames/save01.sav", save1);
        saveGame("D://games/savegames/save02.sav", save2);
        saveGame("D://games/savegames/save03.sav", save3);

        StringBuilder fileList = new StringBuilder();
        File dir = new File("D://games/savegames");
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                if (!item.isDirectory()) {
                    fileList.append(item.getAbsolutePath());
                    break;
                    }
                }
            }

//        try (FileOutputStream fos = new FileOutputStream("D://games/savegames/fileList.txt")) {
//            byte[] bytes = fileList.toString().getBytes();
//            fos.write(bytes,0, bytes.length);
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
        String list = fileList.toString();
        zipFiles("D://games/savegames/zipsave.zip", list);
    }


    public static void saveGame(String saveName, GameProgress save) {
        try (FileOutputStream fos = new FileOutputStream(saveName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            byte[] bytes = save.toString().getBytes();
            fos.write(bytes,0, bytes.length);
            oos.writeObject(save);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String zipName, String zipTarget) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipName));
            FileInputStream fis = new FileInputStream(zipTarget)) {

            ZipEntry enty = new ZipEntry("packed_save.sav");
            zout.putNextEntry(enty);

            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);

            zout.write(buffer);
            zout.closeEntry();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}