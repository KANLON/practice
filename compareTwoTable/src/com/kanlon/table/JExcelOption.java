package com.kanlon.table;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * java操作的excel的工具类
 *
 * @author zhangcanlong
 * @date 2018年9月30日
 */
public class JExcelOption {
	/*
	 * public static void main(String[] args) { JExcelOption option = new
	 * JExcelOption(); // 本地excel地址 String path = "1.xls";
	 * System.out.println(option.readExcel(path));
	 *
	 * // List<HashMap<String, String>> list = new ArrayList<>(); // list =
	 * option.readExcel2(path); // for (int i = 0; i < list.size(); i++) { //
	 * System.out.println(list.get(i)); // } }
	 */

	/**
	 * 根据excel表位置，读取excel表格
	 *
	 * @param path
	 *            本地excel路径
	 * @return 返回嵌套list集合
	 */
	public List<ArrayList<String>> readExcel(String path) throws BiffException, IOException {
		File f = new File(path);
		Workbook book = Workbook.getWorkbook(f);//
		Sheet sheet = book.getSheet(0); // 获得第一个工作表对象
		// 存放所有excel表信息
		List<ArrayList<String>> list = new ArrayList<>();
		for (int i = 0; i < sheet.getRows(); i++) {
			// 存放一行的数据
			ArrayList<String> rowList = new ArrayList<>();
			for (int j = 0; j < sheet.getColumns(); j++) {
				Cell cell = sheet.getCell(j, i); // 获得单元格
				rowList.add(cell.getContents());
				// System.out.print(cell.getContents() + " ");
				// 得到单元格的类型
				// System.out.println(cell.getType());
			}
			list.add(rowList);
			// System.out.print("\n");
		}
		return list;
	}

	/**
	 * 根据excel表位置，读取excel表格
	 *
	 * @param path
	 *            本地excel路径
	 * @return 返回每行的map集合
	 * @throws IOException
	 * @throws BiffException
	 */
	public List<HashMap<String, String>> readExcel2(String path) throws BiffException, IOException {
		File f = new File(path);
		Workbook book = Workbook.getWorkbook(f);//
		Sheet sheet = book.getSheet(0); // 获得第一个工作表对象
		// 存放所有excel表信息
		List<HashMap<String, String>> list = new ArrayList<>();
		for (int i = 1; i < sheet.getRows(); i++) {
			// 存放一行的数据
			HashMap<String, String> map = new HashMap<>();
			for (int j = 0; j < sheet.getColumns(); j++) {
				Cell cell = sheet.getCell(j, i); // 获得单元格
				map.put(sheet.getCell(j, 0).getContents(), cell.getContents());
				// System.out.print(cell.getContents() + " ");
				// 得到单元格的类型
				// System.out.println(cell.getType());
			}
			list.add(map);
			// System.out.print("\n");
		}
		return list;
	}

	/**
	 * 将 嵌套list集合输出到excel中
	 *
	 * @param outList
	 *            要输出的list集合
	 * @param targetExcelPath
	 *            要输出到那个excel中
	 * @throws RowsExceededException
	 * @throws WriteException
	 * @throws IOException
	 */
	public boolean writeExcel(List<ArrayList<String>> outList, String targetExcelPath)
			throws RowsExceededException, WriteException, IOException {
		if (outList == null || outList.size() == 0) {
			return true;
		}

		WritableWorkbook workbook = null;
		workbook = Workbook.createWorkbook(new File(targetExcelPath));
		// 生成第一页的工作表，参数为0说明是第一页
		WritableSheet sheet = workbook.createSheet("纸质版识别内容及核对结果", 0);
		for (int i = 0; i < outList.size(); i++) {
			for (int j = 0; j < outList.get(i).size(); j++) {
				ArrayList<String> tempList = outList.get(i);
				Label label = new Label(j, i, tempList.get(j));
				sheet.addCell(label);
			}
		}
		// 数字类型
		// jxl.write.Number number = new jxl.write.Number(0,1,789.123);
		workbook.write();
		workbook.close();
		return true;
	}

	/**
	 * 根据两个表格的路径核对两个表格第一个表内的内容是否相等.如果相等则返回空字符串"",如果不相等则返回比较出来的不同信息
	 *
	 * @param excelPath1
	 *            表1 的路径
	 * @param excelPath2
	 *            表2的路径
	 * @return diffStr 如果相等则返回空字符串"",如果不相等则返回比较出来的不同信息。
	 * @throws IOException
	 * @throws BiffException
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	public Boolean compareTwoTable(String excelPath1, String excelPath2, String compareResultPath)
			throws BiffException, IOException, RowsExceededException, WriteException {
		List<ArrayList<String>> list1 = new ArrayList<>();
		List<ArrayList<String>> list2 = new ArrayList<>();
		// 两个表是否完全一样的标记，默认一样
		Boolean flag = true;
		// 新的表1的数据，加上了比较结果
		// List<ArrayList<String>> newList1 = new ArrayList<>();
		// 保存表1和表2的标题及其对应列数
		HashMap<String, Integer> tableTitle1 = new HashMap<>();
		HashMap<String, Integer> tableTitle2 = new HashMap<>();
		// 保存表1有顺序的标题
		List<String> tableTitleList1 = new ArrayList<>();
		// 保存表2有顺序的标题
		List<String> tableTitleList2 = new ArrayList<>();
		// 保存表1第一列所有数据及其所在行数作为主键
		HashMap<String, Integer> keyMap1 = new HashMap<>();
		// 保存表2第一列所有数据,及其行数，作为主键，除了标题
		HashMap<String, Integer> keyMap2 = new HashMap<>();
		list1 = this.readExcel(excelPath1);
		list2 = this.readExcel(excelPath2);
		if ((list1 == null || list1.size() <= 1) || (list2 == null || list2.size() <= 1)) {
			throw new InvalidParameterException("表1或表2 没有要比较的数据！");
		}
		// 获取表1和表2的标题及其对应列数
		for (int i = 0; i < list1.get(0).size(); i++) {
			tableTitle1.put(list1.get(0).get(i), i);
			tableTitleList1.add(list1.get(0).get(i));
		}
		for (int i = 0; i < list2.get(0).size(); i++) {
			tableTitle2.put(list2.get(0).get(i), i);
			tableTitleList2.add(list2.get(0).get(i));
		}
		// 获取表1所有的主键
		for (int i = 0; i < list1.size(); i++) {
			if (keyMap1.containsKey(list1.get(i).get(0).replaceAll("\\s", ""))) {
				throw new RuntimeException("存在重复的主键（即第一列含有重复的值），重新定义主键后再执行！！");
			}
			keyMap1.put(list1.get(i).get(0).replaceAll("\\s", ""), i);
		}
		// 获取表2所有的主键
		for (int i = 0; i < list2.size(); i++) {
			if (keyMap2.containsKey(list2.get(i).get(0).replaceAll("\\s", ""))) {
				throw new RuntimeException("存在重复的主键（即第一列含有重复的值），重新定义主键后再执行！！");
			}
			keyMap2.put(list2.get(i).get(0).replaceAll("\\s", ""), i);
		}
		// 表1行标题
		ArrayList<String> titleRowList1 = list1.get(0);
		Set<String> set1 = new LinkedHashSet<>(tableTitleList1);
		Set<String> set2 = new LinkedHashSet<>(tableTitleList2);
		set1.retainAll(set2);
		titleRowList1.add("以表1为基准要核对的列有为：" + set1);
		// 从第二行开始比较（第一行为标题）
		for (int i = 1; i < list1.size(); i++) {
			// 表1的行
			ArrayList<String> rowList1 = list1.get(i);
			String key = rowList1.get(0).replaceAll("\\s", "");
			StringBuffer rowDiff = new StringBuffer("");
			if (!keyMap2.containsKey(key)) {
				rowDiff.append("该行在表2中不存在对应的主键相关信息");
			} else {
				// 表2的行
				ArrayList<String> rowList2 = list2.get(keyMap2.get(key));
				// 遍历表1所有标题，如果表2中含有这个标题并且比较出不相等，才加上比较结果
				for (String title1 : tableTitleList1) {
					if (tableTitle2.containsKey(title1)) {
						String value1 = rowList1.get(tableTitle1.get(title1));
						String value2 = rowList2.get(tableTitle2.get(title1));
						// 不区分大小写比较
						if (!value1.equalsIgnoreCase(value2)) {
							rowDiff.append(title1 + "不一样（表1的为" + value1 + ",表2的为" + value2 + "）；");
						}
					}
				}
			}
			if (!"".equals(rowDiff.toString())) {
				flag = false;
			}
			rowList1.add(rowDiff.toString());
		}
		this.writeExcel(list1, compareResultPath);
		return flag;
	}

	/**
	 * 比较电子版纸质版是否相等，并输入不同
	 *
	 * @param elecPath1
	 *            电子版路径
	 * @param paperPath2
	 *            纸质版路径
	 * @return 返回是否完全相等
	 * @throws IOException
	 * @throws BiffException
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	public Boolean compareAwardTable(String paperPath, String elecPath)
			throws BiffException, IOException, RowsExceededException, WriteException {
		List<ArrayList<String>> list1 = new ArrayList<>();
		List<ArrayList<String>> list2 = new ArrayList<>();
		Boolean flag = true;
		list1 = this.readExcel(paperPath);
		list2 = this.readExcel(elecPath);
		// 获取电子版的标题
		List<String> tableTitleList2 = new ArrayList<>();
		for (int i = 0; i < list2.get(0).size(); i++) {
			tableTitleList2.add(list2.get(0).get(i));
		}
		for (int i = 1; i < list1.size(); i++) {
			ArrayList<String> rowList1 = list1.get(i);
			ArrayList<String> rowList2 = list2.get(i);
			StringBuffer diffBuffer = new StringBuffer("");
			for (int j = 0; j < tableTitleList2.size(); j++) {
				// 得到纸质版单元格对应内容
				String cellValue1 = rowList1.get(j + 8);
				// 电子版单元格内容
				String cellValue2 = rowList2.get(j);
				if (!cellValue1.equals(cellValue2)) {
					diffBuffer.append(tableTitleList2.get(j) + "不同（电子版为：" + cellValue2 + ",纸质版为：" + cellValue1 + "）;");
					flag = false;
				}
			}
			if (!"".equals(diffBuffer.toString())) {
				// 更新之前的与电子版不一样的
				rowList1.set(22, diffBuffer.toString());
			}
		}
		this.writeExcel(list1, paperPath);
		return flag;
	}
}