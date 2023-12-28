package classe;

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
        return null;
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
