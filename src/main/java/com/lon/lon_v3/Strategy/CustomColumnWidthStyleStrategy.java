package com.lon.lon_v3.Strategy;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.Strategy
 * @className: AdaptiveColumnWidthStrategy
 * @author: LONZT
 * @description: TODO
 * @date: 2023/6/15 16:32
 * @version: 1.0
 */

// 自适应表头列宽策略
public class CustomColumnWidthStyleStrategy extends AbstractColumnWidthStyleStrategy {

    // 可以根据这里的最大宽度，按自己需要进行调整,搭配单元格样式实现类中的，自动换行，效果更好
    private static final int MAX_COLUMN_WIDTH = 60;
    private  Map<Integer, Map<Integer, Integer>> CACHE = new HashMap(8);

    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        boolean needSetWidth = isHead || !CollectionUtils.isEmpty(cellDataList);
        if (needSetWidth) {
            Map<Integer, Integer> maxColumnWidthMap = (Map)CACHE.get(writeSheetHolder.getSheetNo());
            if (maxColumnWidthMap == null) {
                maxColumnWidthMap = new HashMap(16);
                CACHE.put(writeSheetHolder.getSheetNo(), maxColumnWidthMap);
            }

            Integer columnWidth = this.dataLength(cellDataList, cell, isHead);
            if (columnWidth >= 0) {
                if (columnWidth > MAX_COLUMN_WIDTH) {
                    columnWidth = MAX_COLUMN_WIDTH;
                }
                Integer maxColumnWidth = (Integer)((Map)maxColumnWidthMap).get(cell.getColumnIndex());
                if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
                    ((Map)maxColumnWidthMap).put(cell.getColumnIndex(), columnWidth);
                    writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), columnWidth*300);
                }

            }
        }
    }

    private Integer dataLength(List<CellData> cellDataList, Cell cell, Boolean isHead) {
        if (isHead) {
            return cell.getStringCellValue().getBytes().length;
        } else {
            CellData cellData = (CellData)cellDataList.get(0);
            CellDataTypeEnum type = cellData.getType();
            if (type == null) {
                return -1;
            } else {
                switch(type) {
                    case STRING:
                        return cellData.getStringValue().getBytes().length;
                    case BOOLEAN:
                        return cellData.getBooleanValue().toString().getBytes().length;
                    case NUMBER:
                        return cellData.getNumberValue().toString().getBytes().length*3/2;
                    default:
                        return -1;
                }
            }
        }
    }



}


