package com.vote.util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SqlInjectionFilter implements Filter {

	
	public void destroy() {
	
	}

	@SuppressWarnings("rawtypes")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String name = params.nextElement().toString();
			String[] values = request.getParameterValues(name);
			for (int i = 0; i < values.length; i++) {
				values[i] = full2HalfChange(values[i]);
				values[i] = values[i].replaceAll("'", "\"").replaceAll(";", "；").replaceAll("\\*", "×")
				.replaceAll("\\%", "％").replaceAll("and ", "").replaceAll("exec ", "")
				.replaceAll("insert ", "").replaceAll("select", "").replaceAll("delete ", "")
				.replaceAll("update ", "").replaceAll("chr", "").replaceAll("mid ", "")
				.replaceAll("master ", "").replaceAll("truncate", "").replaceAll("char ", "")
				.replaceAll("declare ", "").replaceAll("drop table", "").replaceAll("xp_cmdshell", "")
				.replaceAll("netlocalgroup ", "").replaceAll("administrators ", "")
				.replaceAll("net user", "").replaceAll("\\[", "").replaceAll("\\^", "");
				
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

	// 全角转半角的 转换函数

	public static final String full2HalfChange(String QJstr)
			throws UnsupportedEncodingException {
		
		if (QJstr == null) {
			return QJstr;
		}

		StringBuffer outStrBuf = new StringBuffer("");

		String Tstr = "";

		byte[] b = null;

		for (int i = 0; i < QJstr.length(); i++) {

			Tstr = QJstr.substring(i, i + 1);

			// 全角空格转换成半角空格

			if (Tstr.equals("　")) {

				outStrBuf.append(" ");

				continue;

			}

			b = Tstr.getBytes("unicode");

			// 得到 unicode 字节数据

			if (b[2] == -1) {

				// 表示全角？

				b[3] = (byte) (b[3] + 32);

				b[2] = 0;

				outStrBuf.append(new String(b, "unicode"));

			} else {

				outStrBuf.append(Tstr);

			}

		} // end for.

		return outStrBuf.toString();

	}
}
