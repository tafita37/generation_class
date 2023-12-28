package classe.generator;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import classe.Mapping;
import types.TypeVariable;

public class Generator {
    String langage;
    String base;
    String user;
    String mdp;
    String database;
    String tableName;
    String packageName;
    String className;
    int mappingOuController;
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

    public static HashMap<String, Mapping> geListeMappingJson() throws IOException {
        HashMap<String, Mapping> result=new HashMap<String, Mapping>();
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
                HashMap<Integer, TypeVariable> listeTypes=new HashMap<Integer, TypeVariable>();
                for(JsonElement elementsTypes : listeType) {
                    JsonObject jsonObject2=elementsTypes.getAsJsonObject();
                    String packagePresence=jsonObject2.get("packagePresence").getAsString();
                    String typeName=jsonObject2.get("typeName").getAsString();
                    listeTypes.put(listeSQLTypes[j], new TypeVariable(packagePresence, typeName));
                    j++;
                }
                result.put(langage, new Mapping(fileTemplateM, extension, packageName, importPackage, listeTypes));
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

    public ResultSetMetaData getMetadata(Connection con) {
        return null;
    }

    public Mapping getCorrespondingClassMapping() {
        return null;
    }

    public void addListImportType(Connection con) {
    }

    public void writeMappingClass(Connection con) {
    }

}
