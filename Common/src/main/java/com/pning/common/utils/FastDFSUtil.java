package com.pning.common.utils;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.IOException;
import java.util.Arrays;

/**
 * @Author Pning
 * @Date 2021/12/14 17:01
 **/
public class FastDFSUtil {

    public static TrackerClient tc;
    public static TrackerServer ts;
    public static StorageServer ss;
    public static StorageClient sc;

    static {
        try {
            //读取FastDFS的配置文件:读取所有的tracker的所有ip地址
            ClientGlobal.init("fastdfs.conf");
            tc = new TrackerClient();
            ts = tc.getTrackerServer();
            ss = tc.getStoreStorage(ts);
            //定义一个Storage客户端,需要使用这个对象实现具体的文件上传、下载和删除
            sc = new StorageClient(ts,ss);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param local_filename    本地文件路径
     * @param file_ext_name     上传文件的拓展名
     * @param meta_list         文件的属性文件一般不上传
     * @return
     */
    public static String[] upload(String local_filename, String file_ext_name, NameValuePair[] meta_list){
        String[] rs = null;
        try {
            //本地上传：upload_appender_file(本地文件路径,上传文件的拓展名,文件的属性文件一般不上传)
            rs = sc.upload_appender_file(local_filename,file_ext_name,meta_list);
            //返回字符串数组为组名和存储路径，需要保存到数据库中
            System.out.println(Arrays.toString(rs));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
            return rs;
        }
    }

    /**
     * @param group_name        `组名
     * @param remote_filename    存储路径
     * @param local_filename     本地文件保存路径
     * @return
     */
    public static int download(String group_name, String remote_filename, String local_filename){
        int rs = 0;
        try {
            //本地上传：download_file(组名,存储路径,本地文件保存路径)
            rs  = sc.download_file(group_name,remote_filename,local_filename);
            //0为成功，其他失败
            System.out.println(rs);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
            return rs;
        }
    }

    /**
     * @param group_name       组名
     * @param remote_filename 存储路径
     * @returns
     */
    public static int delete(String group_name, String remote_filename){
        int rs = 0;
        try {
            rs = sc.delete_file(group_name, remote_filename);
            //0为成功，其他失败
            System.out.println(rs);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
            return rs;
        }
    }
}
