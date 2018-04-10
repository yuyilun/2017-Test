package cn.test.tomcat.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.junit.Test;
import cn.test.tomcat.protocol.http.HttpQueryParameter;
import cn.test.tomcat.protocol.http.HttpQueryParameters;
import cn.test.tomcat.protocol.http.parser.DefaultHttpQueryParameterParser;
import cn.test.tomcat.protocol.http.parser.HttpParserContext;

public class TestDefaultHttpQueryParameterParser {

    @Test
    public void test() {
        String queryStr = "a=123&a1=1&b=456&a=321";
        HttpParserContext.setRequestQueryString(queryStr);
        DefaultHttpQueryParameterParser httpRequestParameterParser
                = new DefaultHttpQueryParameterParser();
        HttpQueryParameters result = httpRequestParameterParser.parse();
        List<HttpQueryParameter> parameters = result.getQueryParameter("a");
        assertNotNull(parameters);
        assertEquals(2, parameters.size());
        assertEquals("123", parameters.get(0).getValue());
        assertEquals("321", parameters.get(1).getValue());
    }
}
