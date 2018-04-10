package cn.test.ssl;

import java.io.FileInputStream;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class Test {

	public static void main(String[] args) {
		
		/**
		 * 1 openssl pkcs12 -export -clcerts -name chenkangxian
		 *  -inkey private/client-key.pem 
		 *  -in certs/client.cer
		 *	-out /home/longlong/temp/testssl/client.keystore
		 * 2 openssl pkcs12 -export -clcerts -name www.codeaholic
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		
		
		

	}
	
	@org.junit.Test
	public static void init() throws Exception{
		String host = "127.0.0.1";
		int port = 1234;
		String con_sunx509="sunx509";
		String con_pkcs12="pkcs12";
		String con_jks="jks";
		
		String keystorePath = "";
		String truststorePath = "";
		String keystorePassword = "123456";
		
		SSLContext sslContext = SSLContext.getInstance("SSL");
		
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(con_sunx509);
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(con_sunx509);
		
		KeyStore keyStore = KeyStore.getInstance(con_pkcs12);
		KeyStore trustKeyStore = KeyStore.getInstance(con_jks);
		
		FileInputStream keystoreFis = new FileInputStream(keystorePath); 
		keyStore.load(keystoreFis, keystorePassword.toCharArray());
		
		FileInputStream trustKeystoreFis = new FileInputStream(truststorePath);
		trustKeyStore.load(trustKeystoreFis, keystorePassword.toCharArray());
		
		keyManagerFactory.init(keyStore, keystorePassword.toCharArray());
		trustManagerFactory.init(trustKeyStore);
		
		
		//
		sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
		
		Socket socket = sslContext.getSocketFactory().createSocket(host, port);
		
		
		
		
	}
	
	
}
