package mediastore.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelHelper {

	// ��ȡ��Ԫ���ֵ
    private String getValue(Cell cell) {
        String result = "";

        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_BOOLEAN:
            result = cell.getBooleanCellValue() + "";
            break;
        case Cell.CELL_TYPE_STRING:
            result = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_FORMULA:
            result = cell.getCellFormula();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            // ��������ͨ���֣�Ҳ����������
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                result = DateUtil.getJavaDate(cell.getNumericCellValue())
                        .toString();
            } else {
                result = cell.getNumericCellValue() + "";
            }
            break;
        }
        return result;
    }

    /***
     * ���ַ���֧��03����07�汾��excel��ȡ
     * ���Ƕ��ںϲ��ĵ�Ԫ�񣬳��˵�һ�е�һ��֮�⣬�������ֶ�ȡ��ֵΪ��
     * @param is
     */
    public List<HashMap> importXlsx(InputStream is) {
    	List<HashMap> dataList=new ArrayList<HashMap>();
        try {
            Workbook wb = WorkbookFactory.create(is);
            // OPCPackage pkg = OPCPackage.open(is);
            // XSSFWorkbook wb = new XSSFWorkbook(pkg);
            
            for (int i = 0, len = wb.getNumberOfSheets(); i < len; i++) {
                Sheet sheet = wb.getSheetAt(i);
                String fieldName[]=null;
                for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                    if (sheet == null) {
                        return dataList;
                    }
                    Row row = sheet.getRow(j);
                    if(row==null){
                        return dataList;
                    }
                    // ��ȡÿһ����Ԫ��
                    if(j==0)
                    	fieldName=new String[row.getLastCellNum()];
                    HashMap hm = new HashMap();
                    hm.put("����", j+1);
                    for (int k = 0; k < row.getLastCellNum(); k++) {
                        Cell cell = row.getCell(k);
                        if (cell == null) {
                            return dataList;
                        }
                        if(j==0)
                        {
                        	fieldName[k]=getValue(cell);
                        }
                        else
                        	hm.put(fieldName[k], getValue(cell));

                    }
                    if(j>0)
                    	dataList.add(hm);
                }
                
                
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dataList;
    }
}
