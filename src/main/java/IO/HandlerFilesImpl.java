package IO;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandlerFilesImpl implements HandlerFiles {

    @Override
    public List<File> checkDirectory(File dir, String... formats) {
        //TODO настроить вывод ошибок
        if (!dir.isDirectory()) {
            System.out.println("Директории нет");
            return new ArrayList<>();
        } else {
            File[] files = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    for (String format : formats) {
                        return name.toLowerCase().endsWith(format.toLowerCase());
                    }
                    return false;
                }
            });

            return Arrays.asList(files);
        }
    }

    @Override
    public void moveFile(File file, File dir) {
        //TODO настроить вывод ошибок
        if (!dir.isDirectory()) {
            System.out.println("Директории нет");
        } else {
            file.renameTo(new File(dir + file.separator + file.getName()));
        }
    }

}
