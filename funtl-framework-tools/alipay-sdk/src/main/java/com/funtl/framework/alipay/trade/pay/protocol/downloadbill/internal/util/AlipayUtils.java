/*
 * Copyright 2015-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.funtl.framework.alipay.trade.pay.protocol.downloadbill.internal.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.funtl.framework.alipay.trade.pay.protocol.downloadbill.api.AlipayApiException;
import com.funtl.framework.alipay.trade.pay.protocol.downloadbill.api.AlipayResponse;
import com.funtl.framework.alipay.trade.pay.protocol.downloadbill.api.FileItem;
import com.funtl.framework.alipay.trade.pay.protocol.downloadbill.internal.parser.json.ObjectJsonParser;
import com.funtl.framework.alipay.trade.pay.protocol.downloadbill.internal.util.json.JSONReader;
import com.funtl.framework.alipay.trade.pay.protocol.downloadbill.internal.util.json.JSONValidatingReader;

/**
 * 系统工具类。
 *
 * @author carver.gu
 * @since 1.0, Sep 12, 2009
 */
public abstract class AlipayUtils {
	private static String localIp;

	private AlipayUtils() {
	}

	/**
	 * 获取文件的真实后缀名。目前只支持JPG, GIF, PNG, BMP四种图片文件。
	 *
	 * @param bytes 文件字节流
	 * @return JPG, GIF, PNG or null
	 */
	public static String getFileSuffix(byte[] bytes) {
		if (bytes == null || bytes.length < 10) {
			return null;
		}

		if (bytes[0] == 'G' && bytes[1] == 'I' && bytes[2] == 'F') {
			return "GIF";
		} else if (bytes[1] == 'P' && bytes[2] == 'N' && bytes[3] == 'G') {
			return "PNG";
		} else if (bytes[6] == 'J' && bytes[7] == 'F' && bytes[8] == 'I' && bytes[9] == 'F') {
			return "JPG";
		} else if (bytes[0] == 'B' && bytes[1] == 'M') {
			return "BMP";
		} else {
			return null;
		}
	}

	/**
	 * 获取文件的真实媒体类型。目前只支持JPG, GIF, PNG, BMP四种图片文件。
	 *
	 * @param bytes 文件字节流
	 * @return 媒体类型(MEME-TYPE)
	 */
	public static String getMimeType(byte[] bytes) {
		String suffix = getFileSuffix(bytes);
		String mimeType;

		if ("JPG".equals(suffix)) {
			mimeType = "image/jpeg";
		} else if ("GIF".equals(suffix)) {
			mimeType = "image/gif";
		} else if ("PNG".equals(suffix)) {
			mimeType = "image/png";
		} else if ("BMP".equals(suffix)) {
			mimeType = "image/bmp";
		} else {
			mimeType = "application/octet-stream";
		}

		return mimeType;
	}

	/**
	 * 清除字典中值为空的项。
	 *
	 * @param
	 * @param map 待清除的字典
	 * @return 清除后的字典
	 */
	public static Map<String, FileItem> cleanupMap(Map<String, FileItem> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}

		Map<String, FileItem> result = new HashMap<String, FileItem>(map.size());
		Set<Entry<String, FileItem>> entries = map.entrySet();

		for (Entry<String, FileItem> entry : entries) {
			if (entry.getValue() != null) {
				result.put(entry.getKey(), entry.getValue());
			}
		}

		return result;
	}

	/**
	 * 把JSON字符串转化为Map结构。
	 *
	 * @param body JSON字符串
	 * @return Map结构
	 */
	public static Map<?, ?> parseJson(String body) {
		JSONReader jr = new JSONValidatingReader();
		Object obj = jr.read(body);
		if (obj instanceof Map<?, ?>) {
			return (Map<?, ?>) obj;
		} else {
			return null;
		}
	}

	/**
	 * 把JSON字符串解释为对象结构。
	 *
	 * @param <T>   API响应类型
	 * @param json  JSON字符串
	 * @param clazz API响应类
	 * @return API响应对象
	 */
	public static <T extends AlipayResponse> T parseResponse(String json, Class<T> clazz) throws AlipayApiException {
		ObjectJsonParser<T> parser = new ObjectJsonParser<T>(clazz);
		return parser.parse(json);
	}

	/**
	 * 获取本机的网络IP
	 */
	public static String getLocalNetWorkIp() {
		if (localIp != null) {
			return localIp;
		}
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (netInterfaces.hasMoreElements()) {// 遍历所有的网卡
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				if (ni.isLoopback() || ni.isVirtual()) {// 如果是回环和虚拟网络地址的话继续
					continue;
				}
				Enumeration<InetAddress> addresss = ni.getInetAddresses();
				while (addresss.hasMoreElements()) {
					InetAddress address = addresss.nextElement();
					if (address instanceof Inet4Address) {// 这里暂时只获取ipv4地址
						ip = address;
						break;
					}
				}
				if (ip != null) {
					break;
				}
			}
			if (ip != null) {
				localIp = ip.getHostAddress();
			} else {
				localIp = "127.0.0.1";
			}
		} catch (Exception e) {
			localIp = "127.0.0.1";
		}
		return localIp;
	}

}