package cn.test.tomcat.protocol.http.parser;

import java.io.IOException;
import java.io.InputStream;
import cn.test.tomcat.protocol.http.HttpMessage;

public interface HttpRequestMessageParser {
	 /**
     * �����������е����ݣ�������Http Message����
     * @param inputStream
     * @return
     * @throws IOException
     */
    HttpMessage parse(InputStream inputStream) throws IOException;
}
