package com.wrq.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.pdf.PdfReader;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * Created by wangqian on 2019/4/6.
 */
@Slf4j
public class PageCountUtil {

    /**
     * pdf格式文档的页数
     * @param filepath 文件路径
     * @return pageCount页数
     */
    public static int getPdfPage(String filepath){
        int pageCount = 0;
        PdfReader reader;
        try {
            reader = new PdfReader(filepath);
            pageCount= reader.getNumberOfPages();
        } catch (IOException e) {
            log.error("获取pdf格式文档的页数发生错误");
            e.printStackTrace();
        }
        return pageCount;
    }

    /**
     * 计算Excel格式文档的页数
     * @param filePath 文件路径
     * @return result 页数
     */
    public static int getExcelPageCount(String filePath){
        try{
            InputStream excel = new FileInputStream(filePath);
            XSSFWorkbook wb     = new XSSFWorkbook(excel);

            int result = wb.getNumberOfSheets() ;

            return result ;

        }catch(Exception e){
            log.error("获取excel格式文档的页数发生错误");
            e.printStackTrace() ;
            return -1 ;
        }
    }
    /**
     * 计算PPT格式文档的页数
     * @param filePath 文件路径
     * @return pages 页数
     */
    public static int getPPTCount(String filePath) throws IOException{
        FileInputStream fis = new FileInputStream(filePath);
        XMLSlideShow ppt= new XMLSlideShow(fis);
        int pages = ppt.getSlides().length;
        return pages;
    }

    public static int getDocxCount(String filePath)  throws Exception {

        XWPFDocument docx = new XWPFDocument(POIXMLDocument.openPackage(filePath));
        int pages = docx.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();//总页数
        return pages;
    }

    public static int getDocCount(String filePath)  throws Exception {
        WordExtractor doc = new WordExtractor(new FileInputStream(filePath));
        int pages = doc.getSummaryInformation().getPageCount();//总页数
        return pages;
    }


}
