

import static groovyx.net.http.ContentType.JSON
import groovy.json.JsonBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient

import org.apache.http.client.HttpResponseException

class RestServices {

	static enum HttpCodes {
		HTTP_OK(200),
		HTTP_CREATED(201)

		def code
		public HttpCodes(code) {
			this.code = code
		}
	}

	static RESTClient client = makeClient()
	
	static def makeClient() {
		def localAddress = LocalNetConfig.getLocalAddress()
		def port = 80
		
		//can use HTTPBuilder in alternative
		def endpoint = new RESTClient( "http://localhost" )

		endpoint.auth.basic 'ADMINISTRATOR', 'pwd'
		endpoint.handler.failure = { resp, data ->
			resp.setData(data)
			
			String headers = ''
			resp.headers.each {
				headers = headers + "${it.name} : ${it.value}\n"
			}
			
			throw new HttpResponseException(resp.getStatus(),
				"HTTP call failed. Status code: ${resp.getStatus()}\n" +
				"${headers}\n"+
				"Response: " + (resp as HttpResponseDecorator).getData())
			
			return resp
		}
		return endpoint
	}
	
	static def the(param) {
		param.metaClass.'static'.is = { obj ->
			assert param.status == obj.code
			assert param.contentType == 'application/json'
			true
		}
		param
	}
	
}
