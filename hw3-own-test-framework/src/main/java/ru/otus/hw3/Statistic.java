package ru.otus.hw3;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.lang.StringBuilder;

public class Statistic {

    public Statistic(String category){
        this.category = category;
        results = new ArrayList<>();
    }

    public void addSuccess(String methodName){
        results.add(new InvocationResult(methodName, RESULT.SUCCESS));
    }

    public void addFault(String methodName, Throwable cause){
        results.add(new InvocationResult(methodName, RESULT.FAIL, cause));
    }

    @Override
    public String toString(){
        StringBuilder report = new StringBuilder();
        report.append(category);
        for(InvocationResult result: results){
            report.append(result.toString());
            report.append("\n");
        }

        return report.toString();
    }

    private enum RESULT{
        SUCCESS,
        FAIL
    }

    private class InvocationResult{
        public InvocationResult(String methodName, RESULT result){
            invocationResult = result;
            invokedMethod = methodName;
        }

        public InvocationResult(String methodName, RESULT result, Throwable failCause){
            this(methodName, result);
            faultCause = failCause;

        }

        @Override
        public String toString(){
            String msg = "Method: " + invokedMethod + " [" + invocationResult.toString() + "]";
            if(invocationResult == RESULT.FAIL){
                msg += " cause " + faultCause.toString();
            }

            return msg;
        }

        public Throwable faultCause;
        private String invokedMethod;
        private RESULT invocationResult;
    }

    private String category;
    private List<InvocationResult> results;
}
