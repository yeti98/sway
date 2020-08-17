package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.manager.service.threadpool.MainExecutor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class FileService {

  public Map<Integer, Object> readExcel(MultipartFile file) {
    try {
      FileInputStream excel = (FileInputStream) file.getInputStream();

      XSSFWorkbook workbook = new XSSFWorkbook(excel);
      workbook.setMissingCellPolicy(Row.RETURN_BLANK_AS_NULL);

      Map<Integer, Object> map = new HashMap<>();
      for (int index = 0; index < workbook.getNumberOfSheets(); index++) {
        XSSFSheet sheet = workbook.getSheetAt(index);

        CompletableFuture<List<Object>> future =
            CompletableFuture.supplyAsync(() -> readQuestion(sheet), MainExecutor.getInstance());

        map.put(index, future.get());
      }
      excel.close();
      return map;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private List<Object> readQuestion(XSSFSheet sheet) {
    int cnt = 0;
    List<Object> results = new ArrayList<>();
    for (Row row : sheet) {
      // For each row, iterate through all the columns
      if (cnt != 0) { // Ignore the header
        Iterator<Cell> cellIterator = row.cellIterator();
        List<Object> currentRow = new ArrayList<>();
        while (cellIterator.hasNext()) {
          Cell cell = cellIterator.next();
          switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
              currentRow.add(cell.getNumericCellValue());
              break;
            case Cell.CELL_TYPE_STRING:
            default:
              currentRow.add(cell.getStringCellValue());
          }
        }
        results.add(currentRow);
      }
      cnt++;
    }
    return results;
  }
}
