package thionline.proxy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.cert.CRL;
import java.util.List;

import com.google.gson.Gson;

import thionline.controller.Controller;


public class Proxy implements Runnable {
	Socket sock;
	Gson gson;
	boolean isRun;
	
	public Proxy(Socket sock) {
		this.sock = sock;
		this.gson = new Gson();
		this.isRun = true;
	}
	
	public void run() {
		DataInputStream din = null;
		DataOutputStream dout = null;
		
		try {
			din = new DataInputStream(sock.getInputStream());
			dout = new DataOutputStream(sock.getOutputStream());
			while (isRun) {
				String inputJson = din.readUTF();
				if (!new String("Quit").equals(inputJson)) {
					System.out.println("Obtained request - " + inputJson);
					Controller ctl = new Controller(inputJson);
					String response = ctl.routing();
					System.out.println(response);
					dout.writeUTF(response);
				} else {
					System.out.println(inputJson);
					try {
						this.isRun = false;
						sock.close();
					} catch (Exception e) {}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}



}
