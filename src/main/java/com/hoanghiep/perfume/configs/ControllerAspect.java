package com.hoanghiep.perfume.configs;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {

	@Pointcut(value = "execution(* com.hoanghiep.perfume.controllers.*.*(..))")
	public void executeLogging() {
		
	}
	
	@Around("executeLogging()")
	public Object loggingAround(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Object proceed = joinPoint.proceed();
		
		String className = "CLASS: ["+joinPoint.getTarget().getClass().getSimpleName()+"],\n";
		String method = "METHOD: ["+joinPoint.getSignature().getName()+"()],\n";
		
		System.out.print(className+ method + "REQUEST: ");
		if(joinPoint.getArgs().length > 0) {
			Arrays.stream(joinPoint.getArgs()).forEach(System.out::println);
		} else {
			System.out.println("[]");
		}
		
		System.out.println(className + method + "RESPONSE: "+ proceed.toString());
		
		return proceed;
	}
}
