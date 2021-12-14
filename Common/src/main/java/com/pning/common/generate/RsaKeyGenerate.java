package com.pning.common.generate;

import com.pning.common.utils.RsaUtils;
import org.junit.Test;
import org.springframework.stereotype.Component;

/**
 * @Author Pning
 * @Date 2021/12/10 8:25
 **/
public class RsaKeyGenerate {

    /**
     * 获取路径，因为System.getProperty("user.dir")不同位置下得到的结果不同，所以不用constant包里的
     */
    private String path = System.getProperty("user.dir")+"/src/main/resources/encryption_key";

    /**
     * 私钥路径
     */
    private String privateFilePath = path + "/id_private";
    /**
     * 公钥路径
     */
    private String publicFilePath = path + "/id_public";

    /**
     * 生成公钥和私钥并存入指定路径的文件中
     * @throws Exception
     */
    @Test
    public void generateKey() throws Exception {
        RsaUtils.generateKey(publicFilePath,privateFilePath,"Pning",2048);
        //(公钥路径，私钥路径，盐，key的大小)
    }

    /**
     * 获取公钥路径中文件的内容
     * @throws Exception
     */
    @Test
    public void getPublicKey() throws Exception {
        System.out.println(RsaUtils.getPublicKey(publicFilePath));
    }

    /**
     * 获取私钥路径中文件的内容
     * @throws Exception
     */
    @Test
    public void getPrivateKey() throws Exception {
        System.out.println(RsaUtils.getPrivateKey(privateFilePath));
    }


}
