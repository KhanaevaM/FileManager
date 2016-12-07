package ru.levelp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class FileManager {
    public static void main(String[] args) throws Exception {
        File file = new File("/");
        while (true) {

            System.out.println(file.getPath());

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String command = reader.readLine();

            if (command.startsWith("ls")) {

                if (command.equals("ls")) {
                    String[] list = file.list();
                    for (int i = 0; i < list.length; i++) {
                        System.out.println(list[i]);
                    }
                } else if (command.equals("ls -r")) {
                    FileCommand.recursionList(file);
                    System.out.println();
                } else if (command.startsWith("ls -l")) {
                    File temp;
                    if (command.contains("/")) {
                        temp = new File(command.substring(6));
                    } else {
                        temp = new File(file.getPath() + "/" + command.substring(5));
                    }
                    if (temp.exists()) {

                    String[] list = temp.list();
                    for (int i = 0; i < list.length; i++) {
                        File tempForList = new File(temp.getPath() + "/" + list[i]);
                        Path p = tempForList.toPath();
                        BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);
                        String type = "is Other";
                        if (attr.isDirectory()) {
                            type = "is Directory";
                        } else if (attr.isRegularFile()) {
                            type = "is Regular File";
                        } else if (attr.isSymbolicLink()) {
                            type = "is SymbolicLink";
                        }
                        System.out.println(list[i] + " " + attr.creationTime() + " " + attr.lastAccessTime() + " " + attr.lastModifiedTime() + " " + type + " " + attr.size());
                    }

                    }
                    else{
                        System.out.println("No such file or directory : " + command.substring(5));
                    }
                } else if (command.startsWith("ls ")) {
                    File temp;
                    if (command.contains("/")) {
                        temp = new File(command.substring(3));
                    } else {
                        temp = new File(file.getPath() + "/" + command.substring(3));
                    }

                    if (temp.exists()) {
                        String[] list = temp.list();
                        for (int i = 0; i < list.length; i++) {
                            System.out.println(list[i]);
                        }
                    } else {
                        System.out.println("No such file or directory : " + command.substring(3));
                    }
                }
            } else if (command.startsWith("cd ")) {
                if (command.equals("cd ..")) {
                    file = file.getParentFile();
                } else {
                    File temp;
                    if (command.contains("/")) {
                        temp = new File(command.substring(3));
                    } else {
                        temp = new File(file.getPath() + "/" + command.substring(3));
                    }

                    if (temp.exists()) {
                        if (temp.isDirectory()) {
                            file = temp;
                        } else {
                            System.out.println(command.substring(3) + " is not a directory");
                        }
                    } else {
                        System.out.println("No such file or directory : " + command.substring(3));
                    }
                }
            }

            else if (command.startsWith("mkdir ")){
                File temp;
                if (command.contains("/")) {
                    temp = new File(command.substring(6));
                } else {
                    temp = new File(file.getPath() + "/" + command.substring(5));
                }

                if (temp.exists()) {
                        System.out.println(command.substring(5) + " already exists");

                } else {
                    if (temp.getParentFile().canWrite()) {
                        temp.mkdir();
                        System.out.println(temp.getPath() + " is created");
                    }
                    else{
                        System.out.println("can't create a directory in " + temp.getParentFile().getPath());
                    }
                }
            }

            else if (command.startsWith("touch ")){
                File temp;
                if (command.contains("/")) {
                    temp = new File(command.substring(6));
                } else {
                    temp = new File(file.getPath() + "/" + command.substring(5));
                }

                if (temp.exists()) {
                    System.out.println(command.substring(5) + " already exists");

                } else {
                    if (temp.getParentFile().canWrite()) {
                        temp.createNewFile();
                        System.out.println(temp.getPath() + " is created");
                    }
                    else{
                        System.out.println("can't create a directory in " + temp.getParentFile().getPath());
                    }
                }
            }

            else if (command.equals("quit")) {
                break;
            }
        }
    }
}