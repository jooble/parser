package IO;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandlerFilesImplement implements HandlerFiles {

    @Override
    public List<File> checkDirectory(File dir, String format) {
        //TODO настроить вывод ошибок
        if (!dir.isDirectory()) {
            System.out.println("Директории нет");
            return new ArrayList<>();
        } else {
            File[] files = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(format.toLowerCase());
                }
            });

            return Arrays.asList(files);
        }
    }

    @Override
    public void moveFile(File file, File dir) {
        //TODO настроить вывод ошибок
        if (dir.isDirectory()) {
            System.out.println("Директории нет");
        } else {
            file.renameTo(new File(dir + file.separator + file.getName()));
        }
    }

}
