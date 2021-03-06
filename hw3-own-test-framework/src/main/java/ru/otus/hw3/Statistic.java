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

    public long getSuccessCount(){
        return results.stream().
                filter(x -> x.isSuccess())
                .count();
    }

    public long getFailedCount(){
        return results.stream()
                .filter(x -> x.isFailed())
                .count();
    }

    @Override
    public String toString(){
        StringBuilder report = new StringBuilder();
        report.append("CATEGORY: " + category);
        report.append("\n");
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

        public boolean isSuccess(){
            return invocationResult == RESULT.SUCCESS;
        }

        public boolean isFailed(){
            return invocationResult == RESULT.FAIL;
        }

        public Throwable faultCause;
        private String invokedMethod;
        private RESULT invocationResult;
    }

    private String category;
    private List<InvocationResult> results;
}
