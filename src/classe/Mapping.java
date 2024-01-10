package classe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import types.TypeVariable;

public class Mapping {
    String fileTemplateM;
    String extension;
    String packageName;
    String importPackage;
    LinkedHashMap<Integer, TypeVariable> listeType=new LinkedHashMap<Integer, TypeVariable>();
    public Mapping(String fileTemplateM, String extension, String packageName, String importPackage,
            LinkedHashMap<Integer, TypeVariable> listeType) {
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
    public LinkedHashMap<Integer, TypeVariable> getListeType() {
        return listeType;
    }
    public void setListeType(LinkedHashMap<Integer, TypeVariable> listeType) {
        this.listeType = listeType;
    }

    public TypeVariable[] getAllTypeVariables() {
        return null;
    }

    public LinkedHashMap<String, String> getListeWriteBeforeEnd()
    throws Exception {
        LinkedHashMap<String, String> result=new LinkedHashMap<String, String>();
        try {
            BufferedReader lecteur = new BufferedReader(new FileReader(new File("templateM/"+this.getFileTemplateM())));
            String ligne;
            int i=0;
            while ((ligne = lecteur.readLine()) != null) {
                if(ligne.length()!=0) {
                    if(ligne.indexOf("<v>")!=-1) {
                        String deb=ligne.substring(0, ligne.indexOf("<v>"));
                        if(deb.trim().compareToIgnoreCase("packageName")==0) {
                            if(deb.startsWith(" ")) {
                                result.put("\t"+this.getPackageName(), null);
                            } else {
                                result.put(this.getPackageName(), null);
                            }
                        } else if(deb.trim().compareToIgnoreCase("importPackage")==0) {
                            if(deb.startsWith(" ")) {
                                result.put("\t"+this.getImportPackage(), null);
                            } else {
                                result.put(this.getImportPackage(), null);
                            }
                        } else {
                            result.put(ligne, null);
                        }
                    } else {
                        result.put(ligne, null);
                    }
                } else {
                    result.put("vide"+i, null);
                    i++;
                }
            }
            lecteur.close();

        } catch (IOException e) {
            throw e;
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

    public TypeVariable[] getListeVariableWrite() {
        TypeVariable[] result=new TypeVariable[this.getListeType().size()];
        int i=0;
        for (Map.Entry<Integer, TypeVariable> entry : this.getListeType().entrySet()) {
            Integer cle = entry.getKey();
            TypeVariable valeur = entry.getValue();
            System.out.println(valeur.getValue());
            if(valeur.getOrdre()!=0) {
                result[i]=valeur;
                i++;
            }
        }
        return TypeVariable.trierParOrdre(result);
    }
}
