package com.grant.outsourcing.gs.utils;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Param;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

@Component
public class AsyncHttpHelper
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncHttpHelper.class);

	private static AsyncHttpClient asyncHttpClient;

	@Autowired
	public void setAsyncHttpClient(AsyncHttpClient newAsyncHttpClient) {
		asyncHttpClient = newAsyncHttpClient;
	}

	public static String get(String url, List<Param> queryParams) {
		String result = null;
		try {
			AsyncHttpClient.BoundRequestBuilder builder = asyncHttpClient.prepareGet(url);
			builder.setQueryParams(queryParams);
			Future<Response> f = builder.execute();
			Response response = f.get();

			result = getResponseContent(response, url);
		} catch (Exception e) {
			LOGGER.error(String.format("fail to api, url: %s, msg: %s", url, e.getMessage()), e);
		}
		return result;
	}

	public static String delete(String url, List<Param> queryParams) {
		String result = null;
		try {
			AsyncHttpClient.BoundRequestBuilder builder = asyncHttpClient.prepareDelete(url);
			builder.setQueryParams(queryParams);
			Future<Response> f = builder.execute();
			Response response = f.get();

			result = getResponseContent(response, url);
		} catch (Exception e) {
			LOGGER.error(String.format("fail to api, url: %s, msg: %s", url, e.getMessage()), e);
		}
		return result;
	}

	public static String post(String url, List<Param> params) {
		String result = null;
		try {
			AsyncHttpClient.BoundRequestBuilder builder = asyncHttpClient.preparePost(url);
			builder.setBodyEncoding("utf-8");
			builder.setFormParams(params);
			Future<Response> f = builder.execute();
			Response response = f.get();

			result = getResponseContent(response, url);
		} catch (Exception e) {
			LOGGER.error(String.format("fail to api, url: %s, msg: %s", url, e.getMessage()), e);
		}
		return result;
	}

	public static String put(String url, List<Param> params) {
		String result = null;
		try {
			AsyncHttpClient.BoundRequestBuilder builder = asyncHttpClient.preparePut(url);
			builder.setBodyEncoding("utf-8");
			builder.setFormParams(params);
			Future<Response> f = builder.execute();
			Response response = f.get();

			result = getResponseContent(response, url);
		} catch (Exception e) {
			LOGGER.error(String.format("fail to api, url: %s, msg: %s", url, e.getMessage()), e);
		}
		return result;
	}

	public static String postJson(String url, String json) {
		String result = null;
		try {
			AsyncHttpClient.BoundRequestBuilder builder = asyncHttpClient.preparePost(url);
			builder.setBodyEncoding("utf-8");
			builder.setBody(json);
			builder.setHeader("Content-Type", "application/json; charset=utf-8");
			Future<Response> f = builder.execute();
			Response response = f.get();

			result = getResponseContent(response, url);
		} catch (Exception e) {
			LOGGER.error(String.format("fail to api, url: %s, msg: %s", url, e.getMessage()), e);
		}
		return result;
	}

	public static String putJson(String url, String json) {
		String result = null;
		try {
			AsyncHttpClient.BoundRequestBuilder builder = asyncHttpClient.preparePut(url);
			builder.setBodyEncoding("utf-8");
			builder.setBody(json);
			builder.setHeader("Content-Type", "application/json; charset=utf-8");
			Future<Response> f = builder.execute();
			Response response = f.get();

			result = getResponseContent(response, url);
		} catch (Exception e) {
			LOGGER.error(String.format("fail to api, url: %s, msg: %s", url, e.getMessage()), e);
		}
		return result;
	}

	private static String getResponseContent(Response response, String url) throws IOException {
		String content = null;
		if (response != null) {
			int code = response.getStatusCode();
			if (code / 100 == 2) {
				content = response.getResponseBody();
				//LOGGER.debug("api successfully! code: {}, data: {}", code, result);
			} else {
				LOGGER.debug("http code is invalid, code: {}, url: {}", code, url);
				content = response.getResponseBody();
			}
		} else {
			LOGGER.debug("fail to api, response is null, url: {}", url);
		}
		return content;
	}
}
