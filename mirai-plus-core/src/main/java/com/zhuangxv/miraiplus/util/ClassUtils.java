package com.zhuangxv.miraiplus.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author xiaoxu
 **/
public class ClassUtils {
	private static final String FILE_STR= "file";
	private static final String JAR_STR = "jar";

	/**
	 * 获取某包下所有类
	 * @param packageName 包名
	 * @param isRecursion 是否遍历子包
	 * @return 类的完整名称
	 */
	public static Set<String> getClassName(String packageName, boolean isRecursion) {
		Set<String> classNames = null;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String packagePath = packageName.replace(".", "/");
		URL url = loader.getResource(packagePath);
		if (url != null) {
			String protocol = url.getProtocol();
			if (FILE_STR.equals(protocol)) {
				classNames = getClassNameFromDir(url.getPath(), packageName, isRecursion);
			} else if (JAR_STR.equals(protocol)) {
				JarFile jarFile = null;
				try{
	                jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
				} catch(Exception e){
					e.printStackTrace();
				}
				if(jarFile != null){
					classNames = getClassNameFromJar(jarFile.entries(), packageName, isRecursion);
				}
			}
		} else {
			/*从所有的jar包中查找包名*/
			classNames = getClassNameFromJars(((URLClassLoader)loader).getURLs(), packageName, isRecursion);
		}
		return classNames;
	}

	/**
	 * 从项目文件获取某包下所有类
	 * @param filePath 文件路径
	 * @param isRecursion 是否遍历子包
	 * @return 类的完整名称
	 */
	private static Set<String> getClassNameFromDir(String filePath, String packageName, boolean isRecursion) {
		Set<String> className = new HashSet<>();
		File file = new File(filePath);
		File[] files = file.listFiles();
		if(files==null){return className;}
		for (File childFile : files) {
			if (childFile.isDirectory()) {
				if (isRecursion) {
					className.addAll(getClassNameFromDir(childFile.getPath(), packageName+"."+childFile.getName(), isRecursion));
				}
			} else {
				String fileName = childFile.getName();
				if (fileName.endsWith(".class") && !fileName.contains("$")) {
					className.add(packageName+ "." + fileName.replace(".class", ""));
				}
			}
		}
		return className;
	}

	private static Set<String> getClassNameFromJar(Enumeration<JarEntry> jarEntries, String packageName, boolean isRecursion){
		Set<String> classNames = new HashSet<>();

		while (jarEntries.hasMoreElements()) {
			JarEntry jarEntry = jarEntries.nextElement();
			if(!jarEntry.isDirectory()){
				String entryName = jarEntry.getName().replace("/", ".");
				if (entryName.endsWith(".class") && !entryName.contains("$") && entryName.startsWith(packageName)) {
					entryName = entryName.replace(".class", "");
					if(isRecursion){
						classNames.add(entryName);
					} else if(!entryName.replace(packageName+".", "").contains(".")){
						classNames.add(entryName);
					}
				}
			}
		}
		return classNames;
	}

	/**
	 * 从所有jar中搜索该包，并获取该包下所有类
	 * @param urls URL集合
	 * @param packageName 包路径
	 * @param isRecursion 是否遍历子包
	 * @return 类的完整名称
	 */
	private static Set<String> getClassNameFromJars(URL[] urls, String packageName, boolean isRecursion) {
		Set<String> classNames = new HashSet<>();
		for (URL url : urls) {
			String classPath = url.getPath();
			//不必搜索classes文件夹
			if (classPath.endsWith("classes/")) {
				continue;
			}
			JarFile jarFile = null;
			try {
				jarFile = new JarFile(classPath.substring(classPath.indexOf("/")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (jarFile != null) {
				classNames.addAll(getClassNameFromJar(jarFile.entries(), packageName, isRecursion));
			}
		}
		return classNames;
	}
}