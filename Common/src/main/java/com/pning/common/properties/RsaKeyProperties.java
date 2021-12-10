package com.pning.common.properties;
import com.pning.common.constant.FilePathConstant;
import com.pning.common.utils.RsaUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Author Pning
 * @Date 2021/12/10 9:13
 **/

@Component
@ConfigurationProperties("rsa.key")
@PropertySource(value = { "classpath:auth.yml" })
public class RsaKeyProperties {


    private String pubKeyFile;
    private String priKeyFile;

    private PublicKey publicKey;
    private PrivateKey privateKey;


    @PostConstruct
    public void createRsaKey() throws Exception {
        publicKey = RsaUtils.getPublicKey(FilePathConstant.path+"/"+pubKeyFile);
        privateKey = RsaUtils.getPrivateKey(FilePathConstant.path+"/"+priKeyFile);
    }

    public String getPubKeyFile() {
        return pubKeyFile;
    }

    @Value("${pubKeyFile}")
    public void setPubKeyFile(String pubKeyFile) {
        this.pubKeyFile = pubKeyFile;
    }

    public String getPriKeyFile() {
        return priKeyFile;
    }

    @Value("${priKeyFile}")
    public void setPriKeyFile(String priKeyFile) {
        this.priKeyFile = priKeyFile;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
