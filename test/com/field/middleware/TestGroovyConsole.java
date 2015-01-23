package com.field.middleware;

import groovy.lang.*;
import groovy.ui.Console;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.groovy.runtime.StackTraceUtils;
import org.codehaus.groovy.runtime.StringBufferWriter;
import org.codehaus.groovy.tools.shell.Groovysh;
import org.codehaus.groovy.tools.shell.util.SimpleCompletor;
import org.slf4j.LoggerFactory;

import javax.script.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by bqw on 14-10-9.
 */
public class TestGroovyConsole {
    public static void  main(String[] urls) throws IOException {

        GroovyScriptEngine gse = new GroovyScriptEngine("/Users/bqw/work/code/git-code/field.dsgrp.cn/field-project/middleware/script");


//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("Groovy");
//        PrintWriter writer = new PrintWriter(new StringWriter());
//        engine.getContext().setWriter(writer);
//        engine.getContext().setErrorWriter(writer);

//        gse = (GroovyScriptEngine)engine;
//
//        gse.getConfig().set



                ByteArrayOutputStream output = new ByteArrayOutputStream();

//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String line;
//        while (true) {
//            System.out.print("groovy> ");
//
//                if ((line = br.readLine()) == null || line.equals("quit"))
//                    break;
//
            try {
                StringBuffer outResult = new StringBuffer();
                StringWriter stringWriter = new StringWriter();

                gse.getConfig().setOutput(new PrintWriter(stringWriter));
                Binding binding = new Binding();
                binding.setProperty("logger", LoggerFactory.getLogger("Renderer.groovy"));
                binding.setProperty("out", new PrintWriter(stringWriter));
                gse.run("Renderer.groovy", binding);

                System.out.println("print result:" + stringWriter.toString());
            } catch (ResourceException e) {
                System.out.println(e.getMessage());
            }catch (GroovyRuntimeException e){
                for (String message: ExceptionUtils.getRootCauseStackTrace(e)){
                    if(message.indexOf("groovy.util.GroovyScriptEngine")> -1 ){
                        break;
                    }
                    System.out.println(message);
                }

            }catch (Exception e){}
//        }

//        String groovyScript = "printl \"hellow ${a}\"";
//
//        GroovyShell groovyShell = new GroovyShell();
//
//
////        groovyShell.run(groovyScript, "abc", new ArrayList());
//
//        Script script = groovyShell.parse(groovyScript);
//
//        Console console = new Console();
//
//        Groovysh groovysh = new Groovysh();
//        groovysh.execute(groovyScript);
////        groovysh.execute()




//        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
//        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("groovy");
//
//        try {
//            System.out.println( scriptEngine.eval("printl \"hellow ${a}\"") );
//
////            scriptEngine.getContext().setWriter(new PrintWriter(System.out));
//
//            scriptEngine.getContext().setErrorWriter(new PrintWriter(System.out));
//
//            if (scriptEngine instanceof Compilable){
//                Compilable compilable = (Compilable) scriptEngine;
//                CompiledScript compiledScript = compilable.compile("printl \"hellow ${a}\"");
//                compiledScript.eval();
//            }
//
//
//        } catch (ScriptException e) {
////            e.printStackTrace();
//        }
    }
}
