package com.example.myapplication.helper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import com.example.myapplication.constant.ProjectSettings;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecurityHelper {
    //region statics
    private static SecurityHelper sInstance;

    public static SecurityHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SecurityHelper(context);
        }
        return sInstance;
    }

    public static String toSHA256(String text) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes("UTF-8"));
            byte[] digest = md.digest();
            String base64 = Base64.encodeToString(digest, Base64.DEFAULT);
            if (base64.endsWith("\n")) {
                base64 = base64.substring(0, base64.length() - 1);
            }
            return base64;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    //endregion

    //region class variables
    private Context mContext;
    private String mCryptoTransformationType = ProjectSettings.CRYPTO_TRANSFORMATION_TYPE;
    private String mCryptoAlgorithm = ProjectSettings.CRYPTO_ALGORITHM;
    private String mCertificateAlgorithm = ProjectSettings.CRYPTO_CERTIFICATE_ALGORITHM;
    private String mCertificateFactoryType = ProjectSettings.CRYPTO_CERTIFICATE_FACTORY_TYPE;

    private byte[] mKey;
    //endregion

    private SecurityHelper(Context context) {
        mContext = context;
        mKey = getCertificateSha1Fingerprint().replace(":", "").substring(0, 16).getBytes();
    }

    //region methods
    public String encrypt(String text) {
        return encrypt(text, mKey);
    }

    public String encrypt(String text, byte[] key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(mKey, mCryptoAlgorithm);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(key);
            Cipher cipher = Cipher.getInstance(mCryptoTransformationType);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
            byte[] bytes = cipher.doFinal(text.getBytes("UTF-8"));
            return Base64.encodeToString(bytes, Base64.DEFAULT).replace("\n", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String text) {
        return decrypt(text, mKey);
    }

    public String decrypt(String text, byte[] key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(mKey, mCryptoAlgorithm);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(key);
            Cipher cipher = Cipher.getInstance(mCryptoTransformationType);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] decoded = Base64.decode(text, Base64.DEFAULT);
            byte[] bytes = cipher.doFinal(decoded);
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getCertificateSha1Fingerprint() {
        PackageManager pm = mContext.getPackageManager();
        String packageName = mContext.getPackageName();
        int flags = PackageManager.GET_SIGNATURES;
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();
        InputStream input = new ByteArrayInputStream(cert);
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance(mCertificateFactoryType);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        X509Certificate c = null;
        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        String hexString = null;
        try {
            MessageDigest md = MessageDigest.getInstance(mCertificateAlgorithm);
            byte[] publicKey = md.digest(c.getEncoded());
            hexString = byte2HexFormatted(publicKey);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    private String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1) h = "0" + h;
            if (l > 2) h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1)) str.append(':');
        }
        return str.toString();
    }
    //endregion
}
