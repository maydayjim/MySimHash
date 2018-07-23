package main;

import java.io.*;
import java.util.ArrayList;
import java.math.BigDecimal;
import main.Clibrary;
import com.sun.jna.Native;


/**
 * 1,2为原始文档，3为1+2，4是其他文档加上一点点的1和2（短文本），5是其他文档原始（段文档）
 */

public class test {
    public static void main(String[] args) {
        SimCheckService simCheckService = new SimCheckService();

        /**
         * 测试一下分词模块的实现
         */
        Clibrary instance = (Clibrary)Native.loadLibrary(System.getProperty("user.dir")+"\\source\\NLPIR", Clibrary.class);
        int init_flag = instance.NLPIR_Init("", 1, "0");
        String resultString = null;
        if (0 == init_flag) {
            resultString = instance.NLPIR_GetLastErrorMsg();
            System.err.println("初始化失败！\n"+resultString);
            return;
        }
        String sInput = simCheckService.readDataFile("src/main/Text/trainText_before");

        resultString = instance.NLPIR_ParagraphProcess(sInput, 1);

        /**
         * 顺便也提取一下关键词吧
         */

        String keyWords= instance.NLPIR_GetKeyWords(sInput,10,false);
        System.out.println("从段落中提取的关键词：\n" + keyWords);


        try{
            File f = new File("E:\\MycodePractice\\mysimHash\\src\\main\\Text\\trainText_after");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(resultString);
            bw.flush();
            bw.close();
        }catch (IOException e){
            System.out.println("io exception~~~~~");
        }

        System.out.println(resultString);
        System.out.println("............................");



        Content content1 = new Content(121,simCheckService.readDataFile("src/main/Text/trainText1"));
        Content content2 = new Content(122,simCheckService.readDataFile("src/main/Text/trainText2"));
        Content content3 = new Content(123,simCheckService.readDataFile("src/main/Text/trainText3"));
        Content content4 = new Content(124,simCheckService.readDataFile("src/main/Text/trainText4"));
        Content content5 = new Content(125,simCheckService.readDataFile("src/main/Text/trainText5"));
        Content content6 = new Content(126,simCheckService.readDataFile("src/main/Text/trainText_after"));
        Content content7 = new Content(127,simCheckService.readDataFile("src/main/Text/trainText_comp"));
        simCheckService.simCheck(content1);
        System.out.println("************");
       // System.out.println(simCheckService.AllSimHashValue.get(1).segmentHash);
       // System.out.println(simCheckService.AllSimHashValue.get(2).segmentHash);
        simCheckService.simCheck(content2);
        System.out.println("************");
        simCheckService.simCheck(content3);
        System.out.println("************");
        simCheckService.simCheck(content4);
        System.out.println("************");
        simCheckService.simCheck(content5);
        System.out.println("************");
        simCheckService.simCheck(content6);
        System.out.println("************");
        simCheckService.simCheck(content7);


        }
    }

