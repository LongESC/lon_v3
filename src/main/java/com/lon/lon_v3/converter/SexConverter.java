package com.lon.lon_v3.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.lon.lon_v3.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.converter
 * @className: SexConverter
 * @author: LONZT
 * @description: TODO
 * @date: 2023/6/14 11:42
 * @version: 1.0
 */
public class SexConverter implements Converter<Integer> {

    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return "男".equals(cellData.getStringValue()) ? 1 : 0;
    }

    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {

        return new CellData(value.equals(1) ? "男" : "女");
    }
}

