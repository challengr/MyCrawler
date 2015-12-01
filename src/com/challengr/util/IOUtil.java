package com.challengr.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

/**
 * 封装http请求
 * 
 * @author Wangzhe
 * 
 */
public class IOUtil {
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @param charset 加密方式
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr, String charset) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
// 创建SSLContext对象，并使用我们指定的信任管理器初始化
// TrustManager[] tm = { new MyX509TrustManager() };
// SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
// sslContext.init(null, tm, new java.security.SecureRandom());
// 从上述SSLContext对象中得到SSLSocketFactory对象
// SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
// HttpsURLConnection httpUrlConn = (HttpsURLConnection)
// url.openConnection();
			HttpURLConnection httpUrlConn = (HttpURLConnection) url
					.openConnection();
// httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes(charset));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			// 将inputStream decode为utf-8的形式
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "GBK");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 使返回的数据成json格式
			String prefix = "hxbase_json1(";
			String suffix = "}]})";
			str = buffer.substring(prefix.length()).toString();
			str = str.replace(suffix, "}]}");

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

			// 解析成json格式
			jsonObject = null;
			jsonObject = JSONObject.fromObject(str);
		} catch (ConnectException ce) {
			System.out.println("Weixin server connection timed out.");
		} catch (Exception e) {
			System.out.println("https request error:{}");
			// e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 发送Http请求，并把文件保存到本地
	 * @param requestUrl 请求地址
	 * @param fileURI 文件保存地址
	 * @return
	 * @throws IOException 
	 */
	public static void downloadFile(String requestUrl, String fileURI) throws IOException {
		FileOutputStream fileOutputStream = null;
		File file = new File(fileURI);
		
		// 只有文件不存在才下载
		if(!file.exists()) {
System.out.println(fileURI);			
			file.createNewFile();
			HttpURLConnection httpUrlConn = null;
			InputStream inputStream = null;
			BufferedInputStream bufferedInputStream = null;
			try {
				URL url = new URL(requestUrl);
				httpUrlConn = (HttpURLConnection) url.openConnection();
				httpUrlConn.setDoInput(true);
				httpUrlConn.setUseCaches(false);
				// 设置请求方式（GET/POST）
				httpUrlConn.setRequestMethod("GET");
				// 设置接收文件类型
				httpUrlConn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				// 设置浏览器类型
				httpUrlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0"
            			+ "AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.63 Safari/534.3");
				// 设置请求的语言
				httpUrlConn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
				// 保持连接
				httpUrlConn.setRequestProperty("Connection","keep-alive");
				// 设置连接服务器超时（ms）
				httpUrlConn.setConnectTimeout(5000);
				// 设置从服务器读取超时（ms）
				httpUrlConn.setReadTimeout(5000);
				
				httpUrlConn.connect();
				// 将返回的输入流转换成字符串
				inputStream = httpUrlConn.getInputStream();
				// 将inputStream decode为utf-8的形式
				bufferedInputStream = new BufferedInputStream(inputStream);
				// 将文件写入磁盘
				fileOutputStream = new FileOutputStream(file);
				byte[] bytes = new byte[1024];
				int bytesRead;
				while (-1 != (bytesRead = bufferedInputStream.read(bytes))) {
					fileOutputStream.write(bytes, 0, bytesRead);
				}
				
			} finally {
				// 释放资源
				try {
					if(null != fileOutputStream) {
						fileOutputStream.flush();
						fileOutputStream.close();
						fileOutputStream = null;
					}
					if(null != inputStream) {
						inputStream.close();
						inputStream = null;
					}
					if(null != httpUrlConn) {
						httpUrlConn.disconnect();
						httpUrlConn = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			return;
		}
	}
	
	/**
	 * 
	 * @param requestUrl 请求地址
	 * @param fileName 要保存成的文件名
	 * @param tryTime 尝试请求次数
	 * @return 下载的内容
	 * @throws Exception 
	 */
	public static void downloadFile(String requestUrl, String fileName, int tryTime) throws Exception {
		for(int trycount=0; trycount<tryTime; trycount++){
			try{
				// 下载成功则只执行一次html的请求
				IOUtil.downloadFile(requestUrl, fileName);
				// 伪装使用浏览器的人请求资源
				Thread.sleep(new Random().nextInt(200)+200);
				break;
			}catch(Exception e){
				e.printStackTrace();
				if(trycount == tryTime-1)
					throw new Exception("下载失败："+requestUrl);					
			}
		}
	}
	
	public static String saveURIToFile(String pageUrl,final String fileUrl) throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(pageUrl);
            HttpConnectionParams.setConnectionTimeout(httpget.getParams(), 5000);  
            HttpConnectionParams.setSoTimeout(httpget.getParams(), 5000);  
//System.out.println("executing request " + httpget.getURI());
            httpget.setHeader("User-Agent", 
            			"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0"
            			+ "AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.63 Safari/534.3");

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                	int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        byte[] bytes = EntityUtils.toByteArray(entity);
                        FileOutputStream fos = new FileOutputStream(fileUrl);
                        fos.write(bytes);
                        fos.close();
                        return new String(bytes,"utf-8");
                        //return entity != null ? EntityUtils.toString(entity,"UTF-8") : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            String fileContent = httpclient.execute(httpget, responseHandler);
//            System.out.println("----------------------------------------");
//            System.out.println(responseBody);
//            System.out.println("----------------------------------------");
            return fileContent;
        } finally {
            httpclient.close();
        }
	}
	
	// 通过Url下载图片
	public static String saveImgToFile(String imgUrl,final String fileUrl){
		String fileContent = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(imgUrl);
            HttpConnectionParams.setConnectionTimeout(httpget.getParams(), 5000);  
            HttpConnectionParams.setSoTimeout(httpget.getParams(), 5000);  
//System.out.println("executing request " + httpget.getURI());
            httpget.setHeader("User-Agent", 
            			"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0"
            			+ "AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.63 Safari/534.3");

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                	int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        byte[] bytes = EntityUtils.toByteArray(entity);
                        FileOutputStream fos = new FileOutputStream(fileUrl);
                        fos.write(bytes);
                        fos.close();
                        return new String(bytes,"utf-8");
                        //return entity != null ? EntityUtils.toString(entity,"UTF-8") : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            fileContent = httpclient.execute(httpget, responseHandler);
//	            System.out.println("----------------------------------------");
//	            System.out.println(responseBody);
//	            System.out.println("----------------------------------------");
        } catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return fileContent;
	}
	
	/**
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式
	 * @param tryTime 尝试请求次数
	 * @return 下载的内容
	 * @throws Exception 
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String charset, int tryTime) throws Exception {
		String content = null;
		
		for(int trycount=0; trycount<tryTime; trycount++){
			try{
				content = IOUtil.httpRequest(requestUrl, requestMethod, charset);
				break;
			}catch(Exception e){
				e.printStackTrace();
				if(trycount == tryTime-1)
					throw new Exception("下载失败："+requestUrl);					
			}
		}
		return content;
	}
	
	/**
	 * 发送Http请求，返回String格式的data
	 * @param requestUrl
	 * @param requestMethod
	 * @return
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String charset) {
		StringBuffer buffer = new StringBuffer();
		HttpURLConnection httpUrlConn = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		String str;
		try {
			URL url = new URL(requestUrl);
			httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 将返回的输入流转换成字符串
			inputStream = httpUrlConn.getInputStream();
			// 将inputStream decode为utf-8的形式
			inputStreamReader = new InputStreamReader(inputStream, charset);
			bufferedReader = new BufferedReader(
					inputStreamReader);
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
		} catch (Exception e) {
			System.out.println("https request error:{}");
			// e.printStackTrace();
		} finally {
			// 释放资源
			try {
				bufferedReader.close();
				inputStreamReader.close();
				inputStream.close();
				inputStream = null;
				httpUrlConn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buffer.toString();
	}
	
	public static String convertFileToString(String fileName,String charset) throws Exception {
		BufferedInputStream bins = null;
		ByteArrayOutputStream bos = null;
		try {
			bins = new BufferedInputStream(new FileInputStream(fileName));
			bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int bytesRead;
			while ((bytesRead = bins.read(b)) != -1) {
			   bos.write(b, 0, bytesRead);
			}
			byte[] bytes = bos.toByteArray();
			return new String(bytes,charset);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
        finally{
        	if (bins!=null) bins.close();
        	if (bos!=null) bos.close();
        }
	}
	
	public static String convertUTFFileToString(String fileName) throws Exception {
		return convertFileToString(fileName, "utf-8");
	}
	
}
