package classe.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import classe.Mapping;
import database.ConnexionBdd;
import types.TypeVariable;

public class Generator {
    String langage="C#";
    String base="Postgre";
    String user="postgres";
    String mdp="AnaTaf37";
    String database="baovola_s5";
    String tableName="bouquet";
    String packageName="bouquet";
    String className="Bouquet";
    int mappingOuController=1;
    ArrayList<String> listImport;
    public Generator() {
    }
    public Generator(String langage, String base, String user, String mdp, String database, String tableName,
            String packageName, String className, int mappingOuController) {
        this.langage = langage;
        this.base = base;
        this.user = user;
        this.mdp = mdp;
        this.database = database;
        this.tableName = tableName;
        this.packageName = packageName;
        this.className = className;
        this.mappingOuController = mappingOuController;
    }
    public String getLangage() {
        return langage;
    }
    public void setLangage(String langage) {
        this.langage = langage;
    }
    public String getBase() {
        return base;
    }
    public void setBase(String base) {
        this.base = base;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getMdp() {
        return mdp;
    }
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    public String getDatabase() {
        return database;
    }
    public void setDatabase(String database) {
        this.database = database;
    }
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public int getMappingOuController() {
        return mappingOuController;
    }
    public void setMappingOuController(int mappingOuController) {
        this.mappingOuController = mappingOuController;
    }
    public ArrayList<String> getListImport() {
        return listImport;
    }
    public void setListImport(ArrayList<String> listImport) {
        this.listImport = listImport;
    }

    public static LinkedHashMap<String, Mapping> geListeMappingJson() throws IOException {
        LinkedHashMap<String, Mapping> result=new LinkedHashMap<String, Mapping>();
        try {
            FileReader fileReader = new FileReader("configM.json");
            JsonArray jsonArray = new Gson().fromJson(fileReader, JsonArray.class);
            int[] listeSQLTypes=TypeVariable.getIntListeTypeSql();
            for (JsonElement element : jsonArray) {
                // Convertir chaque élément en objet JsonObject
                JsonObject jsonObject = element.getAsJsonObject();

                // Accéder aux propriétés de l'objet
                String langage = jsonObject.get("langage").getAsString();
                String fileTemplateM = jsonObject.get("fileTemplateM").getAsString();
                String extension = jsonObject.get("extension").getAsString();
                String packageName = jsonObject.get("packageName").getAsString();
                String importPackage = jsonObject.get("importPackage").getAsString();
                JsonArray listeType = jsonObject.get("types").getAsJsonArray();
                int j=0;
                LinkedHashMap<Integer, TypeVariable> listeTypes=new LinkedHashMap<Integer, TypeVariable>();
                for(JsonElement elementsTypes : listeType) {
                    JsonObject jsonObject2=elementsTypes.getAsJsonObject();
                    String packagePresence=jsonObject2.get("packagePresence").getAsString();
                    String typeName=jsonObject2.get("typeName").getAsString();
                    listeTypes.put(listeSQLTypes[j], new TypeVariable(packagePresence, typeName));
                    j++;
                }
                result.put(langage.trim(), new Mapping(fileTemplateM, extension, packageName, importPackage, listeTypes));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    public Mapping getCorrespondingMapping() throws IOException {
        Mapping result=null;
        try {
            result= Generator.geListeMappingJson().get(this.getLangage());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    public TypeVariable[] getListeColumnType(Connection con)
    throws Exception {
        TypeVariable[] result=null;
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionBdd(this.getBase(), this.getUser(), this.getMdp(), this.getDatabase());
        }
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        ResultSetMetaData resultSetMetaData=null;
        try {
            Mapping mapping=this.getCorrespondingMapping();
            String sql="select*from "+this.getTableName();
            preparedStatement=con.prepareStatement(sql);
            resultSet=preparedStatement.executeQuery();
            resultSetMetaData=resultSet.getMetaData();
            int count=resultSetMetaData.getColumnCount();
            result=new TypeVariable[count];
            for(int i=1; i<=count; i++) {
                result[i-1]=new TypeVariable(mapping.getListeType().get(resultSetMetaData.getColumnType(i)).getPackageName(), mapping.getListeType().get(resultSetMetaData.getColumnType(i)).getTypeName(), resultSetMetaData.getColumnName(i), i);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            preparedStatement.close();
            if(jAiOuvert) {
                con.close();
            }
        }
        return result;
    }

    public LinkedHashMap<String, String> getRealWrite(Connection con)
    throws Exception {
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionBdd(this.getBase(), this.getUser(), this.getMdp(), this.getDatabase());
        }
        LinkedHashMap<String, String> result=new LinkedHashMap<String, String>();
        try {
            Mapping mapping=this.getCorrespondingMapping();
            LinkedHashMap<String, String> listeWrite=mapping.getListeWriteBeforeEnd();
            for (Map.Entry<String, String> entry : listeWrite.entrySet()) {
                String cle = entry.getKey();
                String valeur = entry.getValue();
                if(cle.trim().compareTo(mapping.getPackageName())==0) {
                    result.put(cle, this.getPackageName()+";");
                } else if(cle.trim().contains("<v>")) {
                    String tmp=cle.substring(cle.indexOf("<v>"), cle.length());
                    String valeurVar=tmp.substring(tmp.indexOf(">")+1, tmp.indexOf(" "));
                    // System.out.println(valeurVar);
                    if(valeurVar.compareTo("class")==0) {
                        String oldCle=cle;
                        cle=cle.substring(0, cle.indexOf("<v>"))+this.getClassName()+" "+tmp.substring(tmp.indexOf(" "));
                        // cle=cle.substring(0, cle.indexOf("<"));
                        // valeurVar=this.getClassName();
                        // if(oldCle.startsWith(" ")) {
                            // result.put("\t"+cle, null);
                        // } else {
                            result.put(cle, null);
                        // }
                        // System.out.println(valeurVar);
                    } else {
                        result.put(cle, null);
                    }
                } else {
                    if(valeur!=null) {
                        result.put(cle, valeur+";");
                    } else {
                        result.put(cle, valeur);
                    }
                }
            }
            // System.out.println(result);    
        } catch (Exception e) {
            throw e;
        } finally {
            if(jAiOuvert) {
                con.close();
            }
        }
        return result;
    }

    public void writeLineInFile(File file, String contenu)
    throws Exception {
        FileWriter fileWriter=new FileWriter(file, true);
        BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
        bufferedWriter.write(contenu);
        bufferedWriter.newLine();
        bufferedWriter.close();
        fileWriter.close();
    }

    public void writeClass(Connection con)
    throws Exception {
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionBdd(this.getBase(), this.getUser(), this.getMdp(), this.getDatabase());
        }
        try {
            Mapping mapping=this.getCorrespondingMapping();
            TypeVariable[] listeTypeWrite=this.getListeColumnType(con);
            LinkedHashMap<String, String> listeWrite=this.getRealWrite(con);
            LinkedHashMap<String, String> fileContain=mapping.getListeWriteBeforeEnd();
            File file=new File(this.getClassName()+"."+mapping.getExtension());
            file.createNewFile();
            for (Map.Entry<String, String> entry : listeWrite.entrySet()) {
                String cle = entry.getKey();
                String valeur = entry.getValue();
                if(valeur!=null) {
                    this.writeLineInFile(file, cle+" "+valeur);
                } else {
                    if(!cle.contains("vide")) {
                        if(cle.compareTo(mapping.getImportPackage())==0) {
                            for(int i=0; i<listeTypeWrite.length; i++) {
                                if(listeTypeWrite[i]!=null&&listeTypeWrite[i].getImportName().length()!=0) {
                                    this.writeLineInFile(file, mapping.getImportPackage()+" "+listeTypeWrite[i].getImportName());
                                }
                            }
                        } else if(cle.contains("typeName")) {
                            String oldCle=cle;
                            for(int i=0; i<listeTypeWrite.length; i++) {
                                cle=oldCle;
                                if(listeTypeWrite[i]!=null) {
                                    cle=cle.replace("typeName", listeTypeWrite[i].getTypeName());
                                    cle=cle.replace("<v>typeValue", listeTypeWrite[i].getValue());
                                    this.writeLineInFile(file, cle);
                                }
                            }
                        } else {
                            this.writeLineInFile(file, cle);
                        }
                    } else {
                        this.writeLineInFile(file, "");
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(jAiOuvert) {
                con.close();
            }
        }
    }
}
