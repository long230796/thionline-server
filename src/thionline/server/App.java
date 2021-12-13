package thionline.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import thionline.database.Database;
import thionline.proxy.Proxy;

public class App {

	public static void main(String[] args) throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		ServerSocket serSock = new ServerSocket(9090);
		while (true) {
			try {
				Socket sock = serSock.accept();
				executorService.execute(new Proxy(sock));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

	}

}
