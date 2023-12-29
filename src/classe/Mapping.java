package classe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import types.TypeVariable;

public class Mapping {
    String fileTemplateM;
    String extension;
    String packageName;
    String importPackage;
    HashMap<Integer, TypeVariable> listeType=new HashMap<Integer, TypeVariable>();
    public Mapping(String fileTemplateM, String extension, String packageName, String importPackage,
            HashMap<Integer, TypeVariable> listeType) {
        this.fileTemplateM=fileTemplateM;
        this.extension = extension;
        this.packageName = packageName;
        this.importPackage = importPackage;
        this.listeType = listeType;
    }
    public Mapping() {
    }
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public String getImportPackage() {
        return importPackage;
    }
    public void setImportPackage(String importPackage) {
        this.importPackage = importPackage;
    }
    public HashMap<Integer, TypeVariable> getListeType() {
        return listeType;
    }
    public void setListeType(HashMap<Integer, TypeVariable> listeType) {
        this.listeType = listeType;
    }

    public TypeVariable[] getAllTypeVariables() {
        return null;
    }

    public HashMap<String, String> getListeWriteBeforeEnd() {
        HashMap<String, String> result=new HashMap<String, String>();
        try {
            BufferedReader lecteur = new BufferedReader(new FileReader(new File("templateM/"+this.getFileTemplateM())));
            String ligne;
            while ((ligne = lecteur.readLine()) != null) {
                if(ligne.length()!=0) {
                    if(ligne.indexOf("?")!=-1) {
                        String deb=ligne.substring(0, ligne.indexOf("?"));
                        if(deb.trim().compareToIgnoreCase("packageName")==0) {
                            result.put(this.getPackageName(), null);
                        }
                        if(deb.trim().compareToIgnoreCase("importPackage")==0) {
                            result.put(this.getImportPackage(), null);
                        }
                    } else {
                        result.put(ligne.trim(), null);
                    }
                }
            }
            lecteur.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }
    public String getFileTemplateM() {
        return fileTemplateM;
    }
    public void setFileTemplateM(String fileTemplateM) {
        this.fileTemplateM = fileTemplateM;
    }
}
