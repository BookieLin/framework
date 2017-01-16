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

package com.funtl.framework.tencent.wechat.pay.exception;

import javax.xml.bind.annotation.XmlRootElement;

import com.funtl.framework.tencent.wechat.pay.PayCode;

/**
 * 支付业务异常
 * result_code=FAIL
 * <p/>
 * Created by xuwen on 2015-12-13.
 */
@XmlRootElement(name = "xml")
public class PayBusinessException extends Exception {

	public PayBusinessException() {
	}

	public PayBusinessException(PayCode payCode, String err_code, String err_code_des) {
		this.result_code = payCode.toString();
		this.err_code = err_code;
		this.err_code_des = err_code_des;
	}

	private String result_code;
	private String err_code;
	private String err_code_des;

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "{" + "result_code='" + result_code + '\'' + ", err_code='" + err_code + '\'' + ", err_code_des='" + err_code_des + '\'' + '}';
	}
}