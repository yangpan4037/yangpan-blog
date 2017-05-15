package com.yangpan.background.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.yangpan.background.dao.IBaseDao;
import com.yangpan.background.query.BaseQuery;
import com.yangpan.background.query.PageList;
import com.yangpan.background.service.IBaseService;

/**
 * 公共的业务层实现类
 * 实现公共的业务层接口，实现公共基础的CRUD的方法
 */
public abstract class BaseServiceImpl<T> implements IBaseService<T> {
	
	//从子类获取entityClass
	private Class<T> entityClass;
	
	//定义dao
	@Autowired
	protected IBaseDao<T> baseDao;
	
	/**
	 *	通过构造方法 ，获得子类的class
	 */
	public BaseServiceImpl() {
		// getClass() 返回此 Object 的运行时类。 new 出来是哪个getClass返回就是哪个
		// public final native Class<?> getClass();
		// 父类BaseServiceImpl是抽象类，不能new，getClass()不能返回BaseServiceImpl是实例，只能是子类的实例
		Class clazz = getClass();
		System.out.println("BaseServiceImpl clazz:" + clazz);
		// 通过子类获取父类的类型Type
		// EmployeeServiceImpl extends BaseServiceImpl<Employee>
		// getGenericSuperclass() 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接父类的
		// Type。
		Type type = clazz.getGenericSuperclass();
		System.out.println("BaseServiceImpl type:" + type);
		// ParameterizedType 表示参数化类型，如
		// Collection<String>,BaseServiceImpl<Employee>
		if (type instanceof ParameterizedType) {// 判断子类型(带了泛型参数的Type)
			ParameterizedType parameterizedType = (ParameterizedType) type;
			// getActualTypeArguments() 返回表示此类型实际类型参数的 Type
			// 对象的数组。：<String>,<Employee>
			entityClass = (Class) parameterizedType.getActualTypeArguments()[0];
		}
		System.out.println("BaseServiceImpl entityClass:" + entityClass);
	}
	
	//依赖注入dao
	public void setBaseDao(IBaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public void saveOrUpdate(T obj) {
		baseDao.saveOrUpdate(obj);
	}

	@Override
	public void delete(Serializable id) {
		baseDao.delete(entityClass, id);
	}

	@Override
	public T get(Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public List<T> getAll() {
		return baseDao.getAll(entityClass);
	}
	
	@Override
	public PageList findByQuery(BaseQuery baseQuery) {
		return baseDao.findByQuery(baseQuery);
	}
	
	/**
	 *	导出下载xlsx 
	 */
	@Override
	public InputStream download(List<String[]> list, String[] heads) throws Exception {
		
		//创建execl-2007文件对象
		SXSSFWorkbook wb = new SXSSFWorkbook();
		
		//创建sheet对象
		Sheet sheet = wb.createSheet();
		
		//创建行对象
		Row row = sheet.createRow(0);// 第一行
		
		//处理表头
		for (int i = 0; i < heads.length; i++) {
			Cell cell = row.createCell(i);//创建单元格
			cell.setCellValue(heads[i]);//设置单元格的值
		}
		
		//处理行
		for (int rownum = 0; rownum < list.size(); rownum++) {
			
			//排除第一行：表头
			row = sheet.createRow(rownum + 1);
			String[] strings = list.get(rownum);
			
			//处理列
			for (int column = 0; column < strings.length; column++) {
				Cell cell = row.createCell(column);
				
				//设置单元格值
				cell.setCellValue(strings[column]);
			}
		}
		
		//准备字节数组输出流
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		//写出去
		wb.write(outputStream);
		outputStream.close();
		
		//使用因为struts2做下载，必须提供一个InputStream的对象
		return new ByteArrayInputStream(outputStream.toByteArray());
	}
	
	/**
	 * 上传xlsx文件
	 * @throws Exception 
	 */
	@Override
	public List<String[]> imp(File upload) throws Exception  {
		
		//定义储存上传文件行的内容集合
		List<String[]> list = new ArrayList<String[]>();
		
		//得到指定文件的输入流
		FileInputStream fis = new FileInputStream(upload);
		
		//创建execl-2007文件对象
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		
		//获取sheet
		XSSFSheet sheetAt = wb.getSheetAt(0);
		
		//获取总行数
		int rowNum = sheetAt.getLastRowNum();
		
		//排除表头从1开始
		for (int i = 1; i <= rowNum; i++) {
			
			//获取行对象
			XSSFRow row = sheetAt.getRow(i);
			
			//获取总的列数
			short cellNum = row.getLastCellNum();
			String[] strings = new String[cellNum];
			for (int j = 0; j < strings.length; j++) {
				
				//得到单元格
				XSSFCell cell = row.getCell(j);
				
				//判断类型，把数据保存到数组
				if (cell != null) {
					if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						strings[j] = cell.getNumericCellValue() + "";
					} else {
						strings[j] = cell.getStringCellValue();
					}
				}
			}
			
			//把行组添加到集合
			list.add(strings);
		}
		return list;
	}
}
