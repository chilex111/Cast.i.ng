package ng.i.cast.helper;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtility {
  private static String TAG ="HttpUtility";
  private static final String auth ="4d099e88364a851e587d2f4996673cf1af578881"; // this allows you connect to the server and receive responsse if there is any
  private static final String api_Key ="ba4c7713116a1574d47c9ae18554e83bf9ce4aad";
  // private static final String token_key ="NhUlOrZhHI7lovelzt5p6MS0VFUbHuS1qyK4ZuYN8LmXLOaqtHbIE/CtPtiAFJ4ZNFR0fAsUgL/Jn+uziQnWYUDq/20ifEYgD6jBYGe4o/Roq99HeFqjZriPHuH3MOH0";
  private static final String X_Api_Key = "ba4c7713116a1574d47c9ae18554e83bf9ce4aad";

  public static String postJson(String mUrl, JSONObject jsonParam) throws IOException {
    HttpsURLConnection conn = null;
    DataOutputStream printout;
    InputStream in = null;
    try{
      URL url = new URL (mUrl);
      conn = (HttpsURLConnection) url.openConnection();
      conn.setDoInput (true);
      conn.setDoOutput (true);
      conn.setUseCaches (false);
      conn.setReadTimeout(50000 /*milliseconds*/);
      conn.setConnectTimeout(40000 /*milliseconds*/);
      conn.setRequestProperty("Content-Type","application/json");
      conn.setRequestMethod("POST");
      conn.setRequestProperty("charset", "utf-8");


      byte[] postData = jsonParam.toString().getBytes(Charset.forName("UTF-8"));

      printout = new DataOutputStream(conn.getOutputStream());
      printout.write(postData);
      //printout.flush ();

      int responseCode = conn.getResponseCode();

      if (responseCode == HttpURLConnection.HTTP_OK) {
        in = new BufferedInputStream(conn.getInputStream());
        return readStream(in);
      }else {
        in = new BufferedInputStream(conn.getErrorStream());
        Log.i("TAG",in.toString());
        return readStream(in);

      }

    }finally {
      if (in != null) {
        in.close();
      }
      if(conn != null) {
        conn.disconnect();
      }
    }
  }
 public static String postJsonAuth(String mUrl, JSONObject jsonParam) throws IOException {
    HttpsURLConnection conn = null;
    DataOutputStream printout;
    InputStream in = null;
    try{
      URL url = new URL (mUrl);
      conn = (HttpsURLConnection) url.openConnection();
      conn.setDoInput (true);
      conn.setDoOutput (true);
      conn.setUseCaches (false);
      conn.setReadTimeout(50000 /*milliseconds*/);
      conn.setConnectTimeout(40000 /*milliseconds*/);
      conn.setRequestProperty("Content-Type","application/json");
      conn.setRequestProperty("Authorization","Bearer mb173GMEnbudsgt42PIX_a36cd8a0dff34536995e12afb4dd45ff");
      conn.setRequestMethod("POST");
      conn.setRequestProperty("charset", "utf-8");


      byte[] postData = jsonParam.toString().getBytes(Charset.forName("UTF-8"));

      printout = new DataOutputStream(conn.getOutputStream());
      printout.write(postData);
      //printout.flush ();

      int responseCode = conn.getResponseCode();

      if (responseCode == HttpURLConnection.HTTP_OK) {
        in = new BufferedInputStream(conn.getInputStream());
        return readStream(in);
      }else {
        in = new BufferedInputStream(conn.getErrorStream());
        Log.i("TAG",in.toString());
        return readStream(in);

      }

    }finally {
      if (in != null) {
        in.close();
      }
      if(conn != null) {
        conn.disconnect();
      }
    }
  }

  @NonNull
  public static String sendPostRequest(String mUrl, HashMap<String, Object> params) throws IOException {
    InputStream in = null;
    HttpURLConnection conn = null;
    String charset ="UTF-8";
    StringBuilder sbParams;
    String parameters;
    sbParams = new StringBuilder();
    try{
      int i = 0;
      for (String key : params.keySet()) {
        if (i != 0){
          sbParams.append("&");
        }
        sbParams.append(key).append("=")
                .append(URLEncoder.encode(String.valueOf(params.get(key)), charset));

        i++;
      }
      parameters = sbParams.toString();
      byte[] postData = parameters.getBytes(Charset.forName("UTF-8"));
      int postDataLength = postData.length;
      URL url = new URL(mUrl);
      conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput( true );
      conn.setFixedLengthStreamingMode(postDataLength);//conn.setChunkedStreamingMode(0); for unknown length
      conn.setReadTimeout(70000 /* milliseconds */);
      conn.setConnectTimeout(70000 /* milliseconds */);
      conn.setRequestMethod("POST");

      conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
      conn.setRequestProperty("charset","utf-8");
      conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
      /*conn.addRequestProperty("Authorization",auth);
      conn.addRequestProperty("X-Api-Key",api_Key);*/
      //conn.addRequestProperty("content-type"," multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");

      DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
      writer.write(postData);
      String response;
      int responseCode = conn.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        in = new BufferedInputStream(conn.getInputStream());
        response = readStream(in);
        // return readStream(in);

      }else {
        in = new BufferedInputStream(conn.getErrorStream());
        response = readStream(in);
        // return readStream(in);
      }

      Log.i("Response", response);
      return response;
    }finally {
      if (in != null) {
        in.close();
      }
      if(conn != null) {
        conn.disconnect();
      }
    }
  }

  public static String sendPutRequest(String mUrl, HashMap<String, String> params) throws IOException {
    InputStream in = null;
    HttpURLConnection conn = null;
    String charset ="UTF-8";
    StringBuilder sbParams;
    String parameters;
    sbParams = new StringBuilder();
    try{
      int i = 0;
      for (String key : params.keySet()) {
        if (i != 0){
          sbParams.append("&");
        }
        sbParams.append(key).append("=")
                .append(URLEncoder.encode(params.get(key), charset));

        i++;
      }
      parameters = sbParams.toString();
      byte[] postData = parameters.getBytes(Charset.forName("UTF-8"));
      int postDataLength = postData.length;
      URL url = new URL(mUrl);
      conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput( true );
      conn.setFixedLengthStreamingMode(postDataLength);//conn.setChunkedStreamingMode(0); for unknown length
      conn.setReadTimeout(30000 /* milliseconds */);
      conn.setConnectTimeout(15000 /* milliseconds */);
      conn.setRequestMethod("PUT");

      conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
      conn.setRequestProperty("charset","utf-8");
      conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
      conn.addRequestProperty("authorization",auth);
      conn.addRequestProperty("x-api-key",api_Key);
      conn.addRequestProperty("content-type"," multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");

      DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
      writer.write(postData);

      in = new BufferedInputStream(conn.getInputStream());
      return readStream(in);

    }finally {
      if (in != null) {
        in.close();
      }
      if(conn != null) {
        conn.disconnect();
      }
    }
  }

  private static String readStream(InputStream stream) throws IOException {
    Reader reader = new InputStreamReader(stream,"UTF-8");
    BufferedReader bReader = new BufferedReader(reader);
    StringBuilder out = new StringBuilder();
    String line;
    while ((line = bReader.readLine()) != null) {
      out.append(line);
    }
    Log.i(TAG,"HTTP RESPONSE"+ out.toString());
    return out.toString();
  }

  public static String getRequest(String myUrl) throws IOException {

    InputStream is = null;

    try {

      URL url = new URL(myUrl);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    //  conn.addRequestProperty("Authorization","Bearer "+token);
      conn.setReadTimeout(50000 /* milliseconds */);
      conn.setConnectTimeout(30000 /* milliseconds */);
      conn.setRequestMethod("GET");

      conn.setDoInput(true);
      // Starts the query
      conn.connect();
      int response = conn.getResponseCode();
      is = conn.getInputStream();

      // Convert the InputStream into a string
      return readStream(is);

      // Makes sure that the InputStream is closed after the app is
      // finished using it.
    } finally {
      if (is != null) {
        is.close();
      }
    }
  }

  public static String getRequestWithKey(String myUrl) throws IOException {

    InputStream is = null;

    try {

      URL url = new URL(myUrl);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

      conn.setReadTimeout(20000 /* milliseconds */);
      conn.setConnectTimeout(15000 /* milliseconds */);
      conn.addRequestProperty("authorization",auth);
      conn.addRequestProperty("x-api-key",api_Key);
      conn.setRequestMethod("GET");

      conn.setDoInput(true);
      // Starts the query
      conn.connect();
      int response = conn.getResponseCode();
      is = conn.getInputStream();

      // Convert the InputStream into a string
      return readStream(is);

      // Makes sure that the InputStream is closed after the app is
      // finished using it.
    } finally {
      if (is != null) {
        is.close();
      }
    }
  }

  public static String getPayStackRequest(String myUrl) throws IOException {

    InputStream is = null;

    try {

      URL url = new URL(myUrl);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

      conn.setReadTimeout(20000 /* milliseconds */);
      conn.setConnectTimeout(15000 /* milliseconds */);
      conn.setRequestMethod("GET");
      conn.addRequestProperty( "Authorization"," Bearer sk_test_e2f6457d481001ee77596094f6eebb5d8324676b");
      conn.setDoInput(true);
      // Starts the query
      conn.connect();
      int response = conn.getResponseCode();
      is = conn.getInputStream();
      if (response == HttpURLConnection.HTTP_OK) {
        is = new BufferedInputStream(conn.getInputStream());
        return readStream(is);
      }else {
        is = new BufferedInputStream(conn.getErrorStream());
        return readStream(is);
      }
      // Convert the InputStream into a string
      //return readStream(is);

      // Makes sure that the InputStream is closed after the app is
      // finished using it.
    } finally {
      if (is != null) {
        is.close();
      }
    }
  }

}