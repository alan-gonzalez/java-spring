package com.test.product.aspect;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class LoggingAspect {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void getAction() {
	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postAction() {
	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
	public void putAction() {
	}

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController  *)")
	public void controller() {
	}

	@Around("getAction()")
	public Object logGetAction(ProceedingJoinPoint joinPoint) throws Throwable {
		generateRequest("GET", joinPoint);
		Object object = joinPoint.proceed();
		generateResponse(object);
		return object;
	}

	@Around("postAction()")
	public Object logPostAction(ProceedingJoinPoint joinPoint) throws Throwable {
		generateRequest("POST", joinPoint);
		Object object = joinPoint.proceed();
		generateResponse(object);
		return object;
	}

	@Around("putAction()")
	public Object logPutAction(ProceedingJoinPoint joinPoint) throws Throwable {
		generateRequest("PUT", joinPoint);
		Object object = joinPoint.proceed();
		generateResponse(object);
		return object;
	}

	@AfterThrowing(pointcut = "controller()", throwing = "exception")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
		logger.error("An exception has been thrown in " + joinPoint.getSignature().getName() + " ()");
		logger.error("Cause : " + exception.getMessage());
	}

	private void generateRequest(String method, ProceedingJoinPoint joinPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		logger.info("New Request: ");
		
		if (request != null) {
			logger.info("Start Header Section of request ");
			logger.info("Method Type : " + request.getMethod());
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				String headerValue = request.getHeader(headerName);
				logger.info("Header Name: " + headerName + " Header Value : " + headerValue);
			}
			logger.info("Request Path info :" + request.getServletPath());
			logger.info("End Header Section of request ");
		}
		logger.info(method + " " + getRequestUrl(joinPoint, joinPoint.getTarget().getClass()) + " - "
				+ joinPoint.getSignature().getName() + "()");
		logger.info("Payload: ");
		logger.info(getPayload(joinPoint));
	}

	private void generateResponse(Object object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		logger.info("Response: ");
		logger.info(mapper.writeValueAsString(object));

	}

	private String getRequestUrl(JoinPoint joinPoint, Class<? extends Object> clazz) {
		RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
		return getActionUrl(requestMapping);
	}

	private String getActionUrl(RequestMapping requestMapping) {
		return getUrl(requestMapping.value());
	}

	private String getUrl(String[] urls) {
		return urls.length == 0 ? "" : urls[0];
	}

	private String getPayload(JoinPoint joinPoint) {
		CodeSignature signature = (CodeSignature) joinPoint.getSignature();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < joinPoint.getArgs().length; i++) {
			String parameterName = signature.getParameterNames()[i];
			builder.append(parameterName);
			builder.append(": ");
			builder.append(joinPoint.getArgs()[i].toString());
			builder.append(", ");
		}
		return builder.toString();
	}

}
