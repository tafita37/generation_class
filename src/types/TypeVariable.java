package types;

import java.sql.Types;

public class TypeVariable {
    String packageName;
    String typeName;
    String value;
    int ordre;

    public TypeVariable(String packageName, String typeName, String value, int ordre) {
        this.packageName = packageName;
        this.typeName = typeName;
        this.value = value;
        this.ordre=ordre;
    }
    public TypeVariable(String packageName, String typeName) {
        this.packageName = packageName;
        this.typeName = typeName;
    }
    public TypeVariable() {
    }
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImportName() {
        if(this.getPackageName()==null||this.getPackageName().length()==0) {
            return "";
        }
        return this.getPackageName()+"."+this.getTypeName()+";";
    }

    public String getFieldDeclaration() {
        return this.getTypeName()+" "+this.getValue()+";";
    }

    public static int[] getIntListeTypeSql() {
        int[] result=new int[18];
        result[0]=Types.INTEGER;
        result[1]=Types.SMALLINT;
        result[2]=Types.BIGINT;
        result[3]=Types.FLOAT;
        result[4]=Types.REAL;
        result[5]=Types.DOUBLE;
        result[6]=Types.NUMERIC;
        result[7]=Types.DECIMAL;
        result[8]=Types.CHAR;
        result[9]=Types.NCHAR;
        result[10]=Types.VARCHAR;
        result[11]=Types.NVARCHAR;
        result[12]=Types.BOOLEAN;
        result[13]=Types.DATE;
        result[14]=Types.TIME;
        result[15]=Types.TIMESTAMP;
        result[16]=Types.BLOB;
        result[17]=Types.CLOB;
        return result;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public int getOrdre() {
        return ordre;
    }
    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public static TypeVariable[] trierParOrdre(TypeVariable[] listeTypeVariable) {
        for(int i=0; i<listeTypeVariable.length-1; i++) {
            for(int j=i+1; j<listeTypeVariable.length; j++) {
                if(listeTypeVariable[i]!=null&&listeTypeVariable[j]!=null&&listeTypeVariable[i].getOrdre()>listeTypeVariable[j].getOrdre()) {
                    TypeVariable typeVariable=listeTypeVariable[i];
                    listeTypeVariable[i]=listeTypeVariable[j];
                    listeTypeVariable[j]=typeVariable;
                }
            }
        }
        return listeTypeVariable;
    }
}
