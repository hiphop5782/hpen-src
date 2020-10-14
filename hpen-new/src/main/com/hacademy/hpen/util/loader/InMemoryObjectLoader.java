package com.hacademy.hpen.util.loader;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * detect annotation and inject
 * - Component
 * - Inject
 * @author hwang
 *
 */
@Slf4j
public class InMemoryObjectLoader {
	private String basePackage;
	private Class<?>[] annotations;
	private Map<Class<?>, Object> components;
	public InMemoryObjectLoader(String basePackage) throws IOException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException{
		this.basePackage = basePackage;
		this.annotations = new Class<?>[] {Component.class, Autowired.class};
		this.components = new HashMap<>();
		this.detect();
		this.inject();
	}
	
	/**
	 * detect packages
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void detect() throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		log.debug("detect() 시작");
		ClassLoader loader = this.getClass().getClassLoader();
		Enumeration<URL> urls = loader.getResources(basePackage.replace(".", "/"));
		
		while(urls.hasMoreElements()) {
			URL url = urls.nextElement();
			String urlString = url.toExternalForm();
			if(urlString.startsWith("jar:"))
				detectJarFile(urlString);
			else if(urlString.startsWith("file:"))
				detectFile(urlString);
		}
	}
	
	private void detectJarFile(String urlString) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		log.debug("jar file detect ... {}", urlString);
		//jar:file:/com/hakademy/utility/object/InMemoryObjectLoader.class!xxx 형태이므로 정리
		int index = urlString.indexOf("!");
		if(index < 0)
			urlString = urlString.substring("jar:file:/".length());
		else
			urlString = urlString.substring("jar:file:/".length(), index);
		
		//jar file load
		try(JarFile jar = new JarFile(urlString);){
			Enumeration<JarEntry> entries = jar.entries();
			String basePackageString = basePackage.replace(".", "/");
			while(entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String entryName = entry.getName();
				
				if(!entryName.startsWith(basePackageString)) continue;
				if(!entryName.endsWith(".class")) continue;
				if(entryName.contains("$")) continue;
				
				entryName = entryName.replace("/", ".");//패키지 양식으로 변경(aaa.bbb.ccc)
				entryName = entryName.substring(0, entryName.length() - ".class".length());//확장자 제거(.class)
				
				loadClass(entryName);
			}
		}
	}
	
	private void detectFile(String urlString) {
		log.debug("file detect ... {}", urlString);
		//file:/com/hakademy/utility/object/InMemoryObjectLoader.class!xxx 형태이므로 정리
		int index = urlString.indexOf("!");
		if(index < 0)
			urlString = urlString.substring("file:/".length());
		else
			urlString = urlString.substring("file:/".length(), index);
		
		File base = new File(urlString);
		recursiveDetectFile(base);
	}
	
	private void recursiveDetectFile(File file) {
		if(file.isFile()) {
			String path = file.getAbsolutePath().replace("\\", ".");
			path = path.substring(path.indexOf(basePackage));
			if(path.endsWith(".class"))
				path = path.substring(0, path.length() - ".class".length());
			loadClass(path);
		}
		else if(file.isDirectory()) {
			for(File f : file.listFiles()) {
				recursiveDetectFile(f);
			}
		}
	}
	
	private void loadClass(String className) {
		try {
			Class<?> classObject = Class.forName(className);
//			System.out.println("읽기 성공 : "+className);
			for(Annotation ann : classObject.getDeclaredAnnotations()) {
				/* Component 발견 시 객체 생성 및 pool 등록 */
				if(ann.annotationType().equals(Component.class)) {
					log.debug("[Component Load] {}", className);
					components.put(classObject, classObject.getDeclaredConstructor().newInstance());
//					System.out.println("[Detect] : "+className);
				}
			}
		}
		catch(Throwable e) {
			/* pass */
//			System.err.println("읽기 실패 : "+e.getMessage());
		}
	}
	
	/**
	 * component 간의 inect 설정
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private void inject() throws IllegalArgumentException, IllegalAccessException {
		for(Class<?> c : components.keySet()) {
			for(Field f : c.getDeclaredFields()) {
				if(components.containsKey(f.getType())) {
					f.setAccessible(true);
					Object o = components.get(c);
					f.set(o, components.get(f.getType()));
				}
			}
		}
	}
	
	public int componentSize() {
		return components.size();
	}
	public Map<Class<?>, Object> getComponents(){
		return this.components;
	}
	
	public <T>T getBean(Class<T> clazz){
		return clazz.cast(components.get(clazz));
	}

}
