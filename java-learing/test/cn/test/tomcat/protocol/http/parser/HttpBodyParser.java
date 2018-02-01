package cn.test.tomcat.protocol.http.parser;

import cn.test.tomcat.protocol.http.body.HttpBody;

public interface HttpBodyParser {
	  /**
     * 解析并构建HttpBody对象
     *
     * @return
     */
    HttpBody parse();
}
