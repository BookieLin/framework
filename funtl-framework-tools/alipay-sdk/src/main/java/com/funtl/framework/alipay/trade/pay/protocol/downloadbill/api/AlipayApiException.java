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

package com.funtl.framework.alipay.trade.pay.protocol.downloadbill.api;


/**
 * @author runzhi
 */
public class AlipayApiException extends Exception {

	private static final long serialVersionUID = -238091758285157331L;

	private String errCode;
	private String errMsg;

	public AlipayApiException() {
		super();
	}

	public AlipayApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlipayApiException(String message) {
		super(message);
	}

	public AlipayApiException(Throwable cause) {
		super(cause);
	}

	public AlipayApiException(String errCode, String errMsg) {
		super(errCode + ":" + errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return this.errCode;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

}