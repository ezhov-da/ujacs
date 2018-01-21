package ru.ezhov.ujatools;

import java.io.File;

import ru.ezhov.ujatools.exception.DeleteFileException;

/**
 * Мини утилитки для удаления файлов
 */
public class FilesUtil {
    /**
     * заблокирован ли файл
     */
    public boolean isLockFile(File file) {
        return isLock(file);
    }

    private boolean isLock(File file) {
        return !file.renameTo(file);
    }

    /**
     * удаление файла
     */
    public void delete(File file) throws DeleteFileException {
        del(file, true);
    }

    public void checkDelete(File file) throws DeleteFileException {
        del(file, false);
    }

    private void del(File file, boolean delete) throws DeleteFileException {
        if (file.exists()) {
            if (!isLock(file)) {
                if (file.isDirectory()) {
                    File[] fm = file.listFiles();
                    for (File f : fm) {
                        del(f, delete);
                    }
                }
                if (delete) {
                    file.delete();
                }
            } else {
                throw new DeleteFileException(file.getAbsolutePath());
            }
        }
    }
}
