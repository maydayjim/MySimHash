package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * 查找库中的新闻是否有重复，返回布尔值
 * 有判断两个新闻相似度的算法
 *
 */
public class SimCheckService {
    // 二进制哈希位数
    int hashBitNum = 64;

    // 最小阈值
    double minSupportValue  = 5;

    ArrayList<SimHashValue> AllSimHashValue = new ArrayList();
     /**
     * 判断是否有重复，两层循环，大循环是各seg，小循环是把符合seg的simhashvalue都计算一下，满足的就说重复了，不然就继续，没重复的入库
      *
      * 阈值改成3，主要的工作是循环变成四！
     * 
     */
    public boolean simCheck(Content news){
        boolean flag = false;
        SimHashValue[] waitSimHashValue = generateSimHashValue(news);

        for (int i = 0; i < 6; i++) {
            String waitfullsimhash = waitSimHashValue[i].fullHash;
            char[] fullsimhashArray = waitfullsimhash.toCharArray();
            String waitsegment = waitSimHashValue[i].segmentHash;
            int waitseq =  waitSimHashValue[i].seq;

            ArrayList<SimHashValue> consimHashValue = getSimHashValue(waitseq,waitsegment);
            if(consimHashValue.size() == 0){
                continue;
            }
            for (int j = 0; j <consimHashValue.size() ; j++) {
                String confullsimhash = consimHashValue.get(j).fullHash;
                char[] confullhashArray = confullsimhash.toCharArray();
                int sameNum = 0;
                for (int k = 0; k <64 ; k++) {
                    if(fullsimhashArray[k] == confullhashArray[k]){
                        sameNum++;
                    }
                }
                if(sameNum >= 5){
                    flag = true;
                    System.out.println(String.format("重复度高达%s",sameNum));
                    return flag;
                }
            }

        }
        if(flag == false){
            for (int i = 0; i < 6; i++) {
                AllSimHashValue.add(waitSimHashValue[i]);
            }
        }
        System.out.println("没有重复并录入其哈希值");
        return  flag;
    }

    /**
     *初始化哈希类,创建六个simhashvalue来存储各seg
     */
     private SimHashValue[] generateSimHashValue(Content news){
        long contentID = news.contentID;
        SimHashValue[] simHashArray = new SimHashValue[6];
        int[] fullhashArray = calSimHashValue(news);
        String fullhash = "";
        for (int i = 0; i < 64; i++) {
             fullhash = fullhash + fullhashArray[i];
        }
        int startIndex = 0;
        int endIndex = 10;

        //一次循环给大家都赋值好

         for (int i = 0; i < 6 ; i++) {
             SimHashValue tempsimHash =new SimHashValue();
             tempsimHash.contentID = contentID;
             tempsimHash.seq = i;
             tempsimHash.fullHash =  fullhash;
             tempsimHash.segmentHash = fullhash.substring(startIndex,endIndex);
             startIndex = endIndex;
             endIndex += 10;
             if(i == 4){
                 endIndex = 64;
             }
             simHashArray[i] = tempsimHash;
         }
         return simHashArray;
     }


    /**
     * 返回某段seg与待匹配seg相等的所有集合
     */
    private ArrayList<SimHashValue> getSimHashValue(int seq, String segmentHash){
        //获取符合某片段的全部数据集合
        ArrayList<SimHashValue> conSimHashValue = new ArrayList<SimHashValue>();
        SimHashValue simHashValue = new SimHashValue();
        for (int i = 0; i < AllSimHashValue.size(); i++) {
            simHashValue = AllSimHashValue.get(i);
            if(simHashValue.seq == seq && simHashValue.segmentHash.equals(segmentHash)){
                    conSimHashValue.add(simHashValue);
            }
        }
        return conSimHashValue;

    }

    /**
     * 传输Content类计算哈希值
     *
     *
     */
    private  int[] calSimHashValue(Content news){
        int index;
        long hashValue;
        double weight;

        int[] binaryArray  = new int[hashBitNum];
        int[] resultValue  = new int[hashBitNum];
        double[] hashArray  = new double[hashBitNum];

        String w;
        String[] words;

        news.statWords();

        words = news.content.split(" ");
        for (String str : words) {
            index = str.indexOf('/');
            if (index == -1) {
                continue;
            }
            w = str.substring(0, index);

            // 获取权重值，根据词频所得
            weight = news.getWordFrequentValue(w);
            if(weight == -1){
                continue;
            }
            // 进行哈希值的计算
            hashValue = BKDRHash(w);
            // 取余把位数变为n位
            hashValue %= Math.pow(2, 64);

            // 转为二进制的形式

            numToBinaryArray(binaryArray, (int) hashValue);

            for (int i = 0; i < binaryArray.length; i++) {
                // 如果此位置上为1，加权重
                if (binaryArray[i] == 1) {
                    hashArray[i] += weight;
                } else {
                    // 为0则减权重操作
                    hashArray[i] -= weight;
                }
            }
        }

        // 进行数组收缩操作，根据值的正负号，重新改为二进制数据形式
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] > 0) {
                resultValue[i] = 1;
            } else {
                resultValue[i] = 0;
            }
        }
        return resultValue;
    }

    /**
     * 数字转为二进制形式
     *
     * @param binaryArray
     *            转化后的二进制数组形式
     * @param num
     *            待转化数字
     */
    private void numToBinaryArray(int[] binaryArray, int num) {
        int index = 0;
        int temp = 0;
        while (num != 0) {
            binaryArray[index] = num % 2;
            index++;
            num /= 2;
        }

        // 进行数组前和尾部的调换
        for (int i = 0; i < binaryArray.length / 2; i++) {
            temp = binaryArray[i];
            binaryArray[i] = binaryArray[binaryArray.length - 1 - i];
            binaryArray[binaryArray.length - 1 - i] = temp;
        }
    }




    /**
     * BKDR字符哈希算法
     *
     * @param str
     * @return
     */

    private  long BKDRHash(String str) {
        int seed = 31; /* 31 131 1313 13131 131313 etc.. */
        long hash = 0;
        int i = 0;

        for (i = 0; i < str.length(); i++) {
            hash = (hash * seed) + (str.charAt(i));
        }

        hash = Math.abs(hash);
        return hash;
    }

    /**
     * 从文件中读取数据
     */
    String readDataFile(String filePath) {
        File file = new File(filePath);
        StringBuilder strBuilder = null;

        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            strBuilder = new StringBuilder();
            while ((str = in.readLine()) != null) {
                strBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.getStackTrace();
        }

        return strBuilder.toString();
    }


}
