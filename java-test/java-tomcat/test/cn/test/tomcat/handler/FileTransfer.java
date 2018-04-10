package cn.test.tomcat.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import cn.test.tomcat.utils.IoUtils;

public class FileTransfer {
	
	public FileTransfer() {}
	
	void getFile(String docBase, InputStream inputStream, OutputStream outputStream) {
		try {
			Scanner scanner = new Scanner(inputStream);
			PrintWriter printWriter = new PrintWriter(outputStream);
			
			printWriter.append("Server connected.Welcome to File Server.\n");
			printWriter.flush();
			
			while(scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				if("stop".equals(nextLine)) {
					printWriter.append("bye bye.\n");
					printWriter.flush();
					break;
				}else {
					Path filePath = Paths.get(docBase,nextLine);
					if(Files.isDirectory(filePath)) {
						printWriter.append("目录 ").append(filePath.toString())
							.append(" 下有文件： ").append("\n");
						Files.list(filePath).forEach( 
								fileName -> {
									printWriter.append(fileName.getFileName().toString())
										.append("\n").flush();
								});
					}else if(Files.isReadable(filePath)) {
						printWriter.append("File ").append(filePath.toString())
							.append(" 的内容是： ").append("\n").flush();
						Files.copy(filePath, outputStream);
						printWriter.append("\n");
					}else {
						printWriter.append("File ").append(filePath.toString())
							.append(" is not found. ").append("\n").flush();
					}
				}
			}
		} catch (IOException e) {
			 throw new HandlerException(e);
		}finally {
			 IoUtils.closeQuietly(inputStream);
			 IoUtils.closeQuietly(outputStream);
		}
	}
}
