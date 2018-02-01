package cn.test.tomcat.protocol.http.parser;

import cn.test.tomcat.protocol.http.HttpQueryParameter;
import cn.test.tomcat.protocol.http.HttpQueryParameters;

public class DefaultHttpQueryParameterParser extends AbstractParser implements HttpQueryParameterParser{
	private final HttpQueryParameters httpQueryParameters;
	private static final String SPLITER = "&";
	
	public DefaultHttpQueryParameterParser() {
		this.httpQueryParameters = new HttpQueryParameters();
	}
	
	@Override
	public HttpQueryParameters parse() {
		String queryString = HttpParserContext.getRequestQueryString();
		if(queryString != null) {
			String[] keyValues = queryString.split(SPLITER);
			for(String keyValue : keyValues) {
				if(keyValue.contains(KV_SPLITER)) {
					String[] temp = keyValue.split(KV_SPLITER);
					if(temp.length == 2) {
						this.httpQueryParameters.addQueryParameter(new HttpQueryParameter(temp[0], temp[1]));
					}
				}
			}
		}
		return this.httpQueryParameters;
	}	
}
