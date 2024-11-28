package ru.effective_mobile.todo.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

    @Pointcut("execution(* ru.effective_mobile.todo.service.impl.TodoServiceImpl.*(..))")
    public void serviceLayerExecution() {
    }
}