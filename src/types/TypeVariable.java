package types;

import java.sql.Types;

public class TypeVariable {
    String packageName;
    String typeName;
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
        return this.getPackageName()+"."+this.getTypeName();
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
}
