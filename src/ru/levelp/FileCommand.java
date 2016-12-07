package ru.levelp;

import java.io.File;

public class FileCommand {
    private static String indent = "";

    public static void recursionList(File f) {
        System.out.print(indent);
        System.out.println(f.getName());
        String[] list = f.list();
        for (int i = 0; i < list.length; i++) {
            File temp = new File(f.getPath() + "/" + list[i]);
            if (temp.isDirectory() && temp.canRead()) {
                indent = indent + "  ";
                recursionList(temp);
            }
        }
        if (indent.length() > 1) {
            indent = indent.substring(0, indent.length() - 2);
        }
    }

}

