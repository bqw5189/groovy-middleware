package com.field.middleware;

import java.io.StringWriter;

/**
 * Created by bqw on 14-10-14.
 */
public class ScriptRunResult {
    /**
     * 控制台输出信息
     */
    private StringWriter console = new StringWriter();
    /**
     * 脚本异常信息
     */
    private StringBuffer exceptionStackTrace = new StringBuffer();

    /**
     * 运行结果信息
     */
    private Object runResult;


    public StringWriter getConsole() {
        return console;
    }

    public void setConsole(StringWriter console) {
        this.console = console;
    }

    public Object getRunResult() {
        return runResult;
    }

    public void setRunResult(Object runResult) {
        this.runResult = runResult;
    }

    public StringBuffer getExceptionStackTrace() {
        return exceptionStackTrace;
    }

    public void setExceptionStackTrace(StringBuffer exceptionStackTrace) {
        this.exceptionStackTrace = exceptionStackTrace;
    }
}
