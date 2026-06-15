package com.spboot.app.utils;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBRecover {

    /**
     * 设置数据库名称
     */
    private Connection connection;

    private String backupPath = "";

    public DBRecover(Connection connection) {
        super();
        this.connection = connection;
    }

    public boolean backup() throws IOException, InterruptedException, SQLException {
        String backupFile = backupPath + "backup-" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".sql";
        File file = new File(backupPath);
        if (file.exists()) {
            file.mkdirs();
        }
        if (connection.toString().indexOf("com.mysql") != -1) {
            return backupMysqlTable(backupFile);
        } else if (connection.toString().indexOf("com.microsoft") != -1) {
            return backupSqlServerTable(backupFile);
        } else {
            return false;
        }
    }

    protected boolean backupSqlServerTable(String backupFilePath) throws SQLException {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        String database; // = databaseMetaData.getDatabaseProductName();

        String url = databaseMetaData.getURL().toLowerCase();
        String key = "databasename=";
        int index = url.indexOf(key);
        int lastindex = url.indexOf(";", index);
        database = url.substring(index + key.length(), lastindex);

        String sql = "Backup Database " + database + " To disk='" + backupFilePath + "' WITH FORMAT";
        System.out.println("备份：" + sql);

        execute(sql);

        return true;
    }

    /**
     * 执行sql 语句
     * @param sql
     * @return
     */
    public long execute(String sql) {
        System.out.println(sql);
        long autoInsertId = -1;
        Connection conn = connection;
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = st.getGeneratedKeys();
            while (rs.next()) {
                autoInsertId = rs.getLong(1);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            int code = e.getErrorCode();
            String message = e.getMessage();
            System.err.println("SQL execute Error");
            System.err.println("code:" + code);
            System.err.println("Message:" + message);
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return autoInsertId;
    }

    /**
     * 备份数据库中的特定表
     * @param backupFilePath 备份文件路径
     * @return 是否备份成功
     */
    protected boolean backupMysqlTable(String backupFilePath) {
        if (connection == null) {
            System.out.println("数据库连接对象为空，备份失败！");
            return false;
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SHOW TABLES");

            PrintWriter printWriter = new PrintWriter(new FileWriter(backupFilePath));
            printWriter.println("-- 数据库备份");
            printWriter.println("-- 生成时间：" + java.time.LocalDateTime.now());
            printWriter.println();

            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                printWriter.println("DROP TABLE IF EXISTS " + tableName + ";");

                Statement statement1 = connection.createStatement();
                ResultSet createTableResultSet = statement1.executeQuery("SHOW CREATE TABLE " + tableName);
                if (createTableResultSet.next()) {
                    String createTableSQL = createTableResultSet.getString(2) + ";";
                    printWriter.println(createTableSQL);
                }
                createTableResultSet.close();
                statement1.close();

                Statement statement2 = connection.createStatement();
                ResultSet dataResultSet = statement2.executeQuery("SELECT * FROM " + tableName);
                while (dataResultSet.next()) {
                    int numColumns = dataResultSet.getMetaData().getColumnCount();
                    StringBuilder insertSQL = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
                    for (int i = 1; i <= numColumns; i++) {
                        String content = formatString(dataResultSet.getString(i));
                        insertSQL.append("'").append(content).append("'");
                        if (i < numColumns) {
                            insertSQL.append(", ");
                        }
                    }
                    insertSQL.append(");");
                    printWriter.println(insertSQL.toString());
                }
                dataResultSet.close();
                statement2.close();
            }

            printWriter.close();
            statement.close();
            System.out.println("数据库备份成功！文件：" + backupFilePath);
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.out.println("数据库备份失败：" + e.getMessage());
            return false;
        }
    }

    /**
     * 还原数据库
     * @param backupFilePath 备份文件路径
     * @return 是否还原成功
     */
    public boolean restoreDatabase(String backupFilePath) {
        if (connection == null) {
            System.out.println("数据库连接对象为空，还原失败！");
            return false;
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(backupFilePath));
            Statement statement = connection.createStatement();

            String line;
            StringBuilder command = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.startsWith("--")) {
                    command.append(line);
                    if (line.endsWith(";")) {
                        statement.executeUpdate(command.toString());
                        command.setLength(0); // 清空字符串构建器
                    }
                }
            }

            bufferedReader.close();
            statement.close();
            System.out.println("数据库还原成功！");
            return true;
        } catch (IOException | SQLException e) {
            System.out.println("数据库还原失败：" + e.getMessage());
            return false;
        }
    }

    public boolean restoreSqlServerDatabase(String backupFilePath) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();

        String url = metaData.getURL().toLowerCase();
        String key = "databasename=";
        int index = url.indexOf(key);
        int lastindex = url.indexOf(";", index);
        String database = url.substring(index + key.length(), lastindex);

        String file = backupFilePath;
        String sql = "ALTER DATABASE " + database + "  SET OFFLINE WITH ROLLBACK IMMEDIATE;";
        sql += "RESTORE DATABASE " + database + " FROM DISK='" + file + "' WITH REPLACE,recovery;";
        sql += "ALTER  DATABASE  " + database + "  set  online;";

        System.out.println("+++++++++++++++++++++++++++还原文件：" + file);
        execute(sql);
        System.out.println("还原成功!");
        return true;
    }

    private String formatString(String s) {
        return s.replaceAll("'", "\\\\'");
    }

    public List getBackupList() {
        File dir = new File(backupPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 正则表达式
        List result = new ArrayList();

        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile() && getFileExt(file).toLowerCase().equals(".sql")) {
                result.add(new DBRecoverPojo(file));
            }
        }
        return result;
    }

    public static String getPrintSize(long size) {
        String[] unit = new String[] { "B", "KB", "M", "G", "T" };

        Double result = Double.valueOf(size);
        int i = 0;

        while (result > 1024) {
            result /= 1024;
            i++;
        }
        return String.format("%.2f", result) + " " + unit[i];
    }

    private String getFileExt(File file) {
        if (file.isDirectory()) {
            return "";
        }
        //获取文件的原始名称
        String originalFilename = file.getName(); //
        //获取最后一个.的位置
        int lastIndexOf = originalFilename.lastIndexOf(".");
        //获取文件的后缀名 .jpg
        if (lastIndexOf == -1) {
            return "";
        }
        String suffix = originalFilename.substring(lastIndexOf);
        return suffix;
    }

    public boolean restore(String restoreFile) throws Exception {
        String file = backupPath + restoreFile;
        boolean res = false;
        if (connection.toString().indexOf("com.mysql") != -1) {
            res = restoreDatabase(file);
        } else if (connection.toString().indexOf("com.microsoft") != -1) {
            res = restoreSqlServerDatabase(file);
        }
        if (res) {
            System.out.println("还原成功!文件：" + file);
        } else {
            System.out.println("还原失败!文件：" + file);
        }
        return res;
    }

    public String getBackupPath() {
        return backupPath;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
    }
}
