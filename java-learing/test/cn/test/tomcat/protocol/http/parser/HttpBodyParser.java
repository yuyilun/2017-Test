package cn.test.tomcat.protocol.http.parser;

import cn.test.tomcat.protocol.http.body.HttpBody;

public interface HttpBodyParser {
	  /**
     * ����������HttpBody����
     *
     * @return
     */
    HttpBody parse();
}
