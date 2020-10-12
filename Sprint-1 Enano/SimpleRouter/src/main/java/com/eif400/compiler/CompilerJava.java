package com.eif400.compiler;

import javax.tools.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import java.util.Locale;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * Demo of API Java compiler
 *
 * @author loriacarlos@gmail.com
 * @since II-2020
 * <p>
 * References and Credits </p>
 * <ul>
 * <li>See
 * <a href="https://docs.oracle.com/javase/9/docs/api/javax/tools/package-summary.html">
 * javax.tools documentation
 * </a>
 * <li>See
 * <a href="https://www.developer.com/java/data/an-introduction-to-the-java-compiler-api.html">
 * Introduction to the Java Compiler (developer.com)
 * </a>
 * </ul>
 */
/*
    Carlos Zhou Zheng
    Jose Martinez Sarmiento
    David Morales Hidalgo
    Manuel Guzman Rodriguez
    David Cordero Jimenez
 */
public class CompilerJava {

    private final static String RELATIVE_PATH = "./javaFiles/";
    private final static String JAVATYPE = ".java";

    private static void createJavaFile(String name, String code) {
        var folder = new File(RELATIVE_PATH);
        if (!folder.exists()) {
            folder.mkdir();
        }
        var javaFile = new File(RELATIVE_PATH + name + JAVATYPE);
        try {
            javaFile.createNewFile();
            try (var writter = new FileWriter(RELATIVE_PATH + name + JAVATYPE)) {
                writter.write(code);
            }
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }
    private final static List<String> tokens = Arrays.asList("interface", "class", "enum");

    private static String findType(String code) {

        String patternString = "\\b(" + StringUtils.join(tokens, "|") + ")\\b";
        var pattern = Pattern.compile(patternString);
        var matcher = pattern.matcher(code);

        return matcher.find() ? matcher.group(1) : "NoTypeFound";
    }

    private static String findClass(String code) {
        String type = findType(code);
        var pattern = Pattern.compile(type + "\\W+(\\w+)");
        var matcher = pattern.matcher(code);

        return matcher.find() && !type.equals("NoTypeFound") ? matcher.group(1) : "NoClassFound";
    }

    public static String compileProcess(String code) {
        String className = findClass(code);
        String output;
        createJavaFile(className, code);

        System.out.println("*** Javacc API Demo ****");
        //////////////////////////////////////////////////////////////////

        var file = new File(RELATIVE_PATH + className + JAVATYPE);
        System.out.format("*** Compiling file '%s' ***%n", className + JAVATYPE);
        //////////////////////////////////////////////////////////////////
        // Get compiler and configure compilation task
        var compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagsCollector = new DiagnosticCollector<>();
        Locale locale = null;
        Charset charset = null;
        String outdir = "classes-compiler";
        String optionsString = String.format("-d %s", outdir);

        try {
            var fileManager = compiler.getStandardFileManager(diagsCollector, locale, charset);
            var sources = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(file));

            var writer = new PrintWriter(System.err);
            // Also check out compiler.isSupportedOption() if needed

            Iterable<String> options = Arrays.asList(optionsString.split(" "));
            Iterable<String> annotations = null;
            JavaCompiler.CompilationTask compileTask = compiler.getTask(writer,
                    fileManager,
                    diagsCollector,
                    options,
                    annotations,
                    sources);
            compileTask.call();
        } catch (Exception e) {
            System.err.format("%s%n", e);
            System.exit(-1);
        }
        // Report diagnostics
        if (diagsCollector.getDiagnostics().isEmpty()) {
            output = String.format("*** No errors found in %s ***%n", file);
            System.out.format("*** No errors found in %s ***%n", file);
        } else {
            output = diagsCollector.getDiagnostics().stream()
                    .filter(d -> d.getLineNumber() >= 0)
                    .map(d -> String.format("Line: %d ", d.getLineNumber()) + d.getMessage(locale) + " in source " + d.getSource().getName())
                    .reduce("", (d, i) -> d + "" + i + "\n");
            System.out.print(output);
        }
        return output;
    }

}
