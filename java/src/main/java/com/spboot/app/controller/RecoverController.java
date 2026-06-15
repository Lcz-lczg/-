package com.spboot.app.controller;

import com.spboot.app.config.Configure;
import com.spboot.app.utils.DBRecover;
import com.spboot.app.utils.R;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recover")
public class RecoverController {

    @Resource
    private DataSource dataSource;

    @GetMapping("/backuplist")
    public R backuplist() throws URISyntaxException, SQLException {
        DBRecover recover = getRecover();
        List backupList = recover.getBackupList();
        return R.success(backupList);
    }

    @GetMapping("/backup")
    public R backup() {
        DBRecover recover;
        try {
            recover = getRecover();
            recover.backup();
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("写入文件出错");
        } catch (InterruptedException e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        } catch (URISyntaxException | SQLException e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }

        List backupList = recover.getBackupList();
        return R.success(backupList);
    }

    @RequestMapping("/restore")
    public R<Object> restore(@RequestParam("filename") String filename) {
        try {
            DBRecover recover = getRecover();
            String path = filename;
            recover.restore(path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return R.error("恢复备份出错");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("恢复备份出错");
        }
        return R.success("恢复成功");
    }

    @GetMapping("/backupdelete")
    public R<List> backupdelete(@Autowired HttpServletRequest request) throws URISyntaxException, SQLException {
        DBRecover recover = getRecover();

        String filename = request.getParameter("filename");
        File file = new File(recover.getBackupPath() + filename);
        file.delete();

        List backupList = recover.getBackupList();
        return R.success(backupList);
    }

    private DBRecover getRecover() throws URISyntaxException, SQLException {
        String backupPath = Configure.ROOT_DIR + "/backup/"; //request.getServletContext().getRealPath("/backup/");

        DBRecover recover = new DBRecover(dataSource.getConnection());
        recover.setBackupPath(backupPath);
        return recover;
    }
}
