package me.enderluca.verium.util;

import java.io.File;

public class FileUtil {

    /**
     * Checks recursively if a folder and all its contents are deletable
     * @param folder Folder to check
     * @return True: Folder and its content are deletable<br>False: At least on file/directory are not deletable
     */
    public static boolean isFolderDeletable(File folder){
        File[] files = folder.listFiles();
        if(files == null){
            return folder.canWrite();
        }

        boolean filesWriteable = true;
        //Break early if not writeable file/directory was found
        for(File file : files){
            if(file.isDirectory()) {
                filesWriteable = file.canWrite();
                if(!filesWriteable)
                    break; //We already know the directory is not writeable, so no need to check the childs

                filesWriteable = isFolderDeletable(file);
            }
            else
                filesWriteable = file.canWrite();

            if(!filesWriteable)
                break; //We already know there is at least on file not writeable
        }

        return filesWriteable;
    }

    /**
     * Recursively deletes the specified folder
     * @return If folder could be deleted
     */
    public static boolean deleteFolder(File folder){
        if(!isFolderDeletable(folder))
            return false;

        //We already know all the files are deletable, so no reason
        //to check the return values of delete()
        File[] files = folder.listFiles();
        if(files == null){
            folder.delete();
            return true;
        }

        for(File file : files){
            if(file.isDirectory())
                deleteFolder(file);
            else
                file.delete();
        }

        return true;
    }
}
