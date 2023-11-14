package com.example.WatchShop.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Configuration
public class VnPayConfig {
  public static String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
  public static String vnp_TmnCode = "6N6AD5QM";
  public static String vnp_HashSecret = "WKUCNGIJVFLJSNNCSKLLIZSCJWSMRUAD";
  public static String vnp_apiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";
  public static String vnp_Version = "2.1.0";
  public static String vnp_Command = "pay";

  public static String Sha256(String message) {
    String digest = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder(2 * hash.length);
      for (byte b : hash) {
        sb.append(String.format("%02x", b & 0xff));
      }
      digest = sb.toString();
    } catch (NoSuchAlgorithmException ex) {
      digest = "";
    }
    return digest;
  }

  //Util for VNPAY
  public static String hashAllFields(Map<String, String> fields) {
    List<String> fieldNames = new ArrayList<>(fields.keySet());
    Collections.sort(fieldNames);
    StringBuilder sb = new StringBuilder();
    Iterator<String> itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = itr.next();
      String fieldValue = fields.get(fieldName);
      if ((fieldValue != null) && (!fieldValue.isEmpty())) {
        sb.append(fieldName);
        sb.append("=");
        sb.append(fieldValue);
      }
      if (itr.hasNext()) {
        sb.append("&");
      }
    }
    return hmacSHA512(vnp_HashSecret, sb.toString());
  }

  public static String hmacSHA512(final String key, final String data) {
    try {
      if (key == null || data == null) {
        throw new NullPointerException();
      }
      final Mac hmac512 = Mac.getInstance("HmacSHA512");
      byte[] hmacKeyBytes = key.getBytes();
      final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
      hmac512.init(secretKey);
      byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
      byte[] result = hmac512.doFinal(dataBytes);
      StringBuilder sb = new StringBuilder(2 * result.length);
      for (byte b : result) {
        sb.append(String.format("%02x", b & 0xff));
      }
      return sb.toString();
    } catch (Exception ex) {
      return "";
    }
  }

  public static String getIpAddress(HttpServletRequest request) {
    String ipAdress;
    try {
      ipAdress = request.getHeader("X-FORWARDED-FOR");
      if (ipAdress == null) {
        ipAdress = request.getLocalAddr();
      }
    } catch (Exception e) {
      ipAdress = "Invalid IP:" + e.getMessage();
    }
    return ipAdress;
  }

  public static String getRandomNumber(int len) {
    Random rnd = new Random();
    String chars = "0123456789";
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      sb.append(chars.charAt(rnd.nextInt(chars.length())));
    }
    return sb.toString();
  }
}
