package JDBCTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Properties;


public class DBHelp {

//    static {
//        System.out.println("asasasasas");
//        // 获得属性文件输入流
//        InputStream in = DBHelp.class.getResourceAsStream("jdbc.properties");
////        in = DBHelp.class.getClassLoader().getResourceAsStream("jdbc.properties");
//        String FILE_PATH = DBHelp.class.getClassLoader().getResource("jdbc.properties").getPath();
//        System.out.println(FILE_PATH);
////        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
//        System.out.println(in);
//
//        try {
//            // 从属性文件读取到变量info
//            info.load(in);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public static Connection getConnection() throws SQLException, ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/store";
        String user = "root";
        String password = "990518Chz";


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("驱动程序加载成功");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("加载驱动程序失败");

        }

        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("数据库连接成功:" + connection);
        return connection;
    }
}
