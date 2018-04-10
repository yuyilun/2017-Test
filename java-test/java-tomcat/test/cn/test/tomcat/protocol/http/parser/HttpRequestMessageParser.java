package cn.test.tomcat.protocol.http.parser;

import java.io.IOException;
import java.io.InputStream;
import cn.test.tomcat.protocol.http.HttpMessage;

public interface HttpRequestMessageParser {
	 /**
     * 解析输入流中的内容，并构建Http Message对象
     * @param inputStream
     * @return
     * @throws IOException
     */
    HttpMessage parse(InputStream inputStream) throws IOException;
}
