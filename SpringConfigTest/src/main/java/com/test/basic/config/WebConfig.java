package com.test.basic.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//	web.xml 을 자바로 구현한 클래스
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
//	root-context 객체
	@Override
	protected Class<?>[] getRootConfigClasses() { // root-context.xml
		return new Class[] { RootConfig.class };
	}

//	servlet-context 객체
	@Override
	protected Class<?>[] getServletConfigClasses() { // servlet-context.xml
		return new Class[] { ServletConfig.class };
	}

//	핸들러 매핑 객체
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

//	한글 인코딩 필터
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		return new Filter[] { encodingFilter };
	}
}