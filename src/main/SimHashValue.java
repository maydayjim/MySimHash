package main;



public class SimHashValue {
    long contentID ;
    int segNum ;
    String segmentHash = "";
    String fullHash = "";

}
// int[] fullHashArray;
//HashMap<Integer,String>num2seg = new  HashMap<Integer,String>();

/*
    public SimHashValue(Content news){
        this.contentId = news.contentID;
        this.fullHashArray  = calSimHashValue(news);
        this.segmentHashArray = this.fullHashArray;

    }

    public SimHashValue(){

    }

    */
/**
 * 传输Content类计算哈希值
 * @param news
 * @return
 *//*

    private  int[] calSimHashValue(Content news){
        int index;
        long hashValue;
        double weight;

        int[] binaryArray;
        int[] resultValue  = new int[64];
        double[] hashArray  = new double[64];

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
            binaryArray = new int[64];
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

    */
/**
 * 数字转为二进制形式
 *
 * @param binaryArray
 *            转化后的二进制数组形式
 * @param num
 *            待转化数字
 *//*

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




    */
/**
 * BKDR字符哈希算法
 *
 * @param str
 * @return
 *//*


    private  long BKDRHash(String str) {
        int seed = 31; */
/* 31 131 1313 13131 131313 etc.. *//*

        long hash = 0;
        int i = 0;

        for (i = 0; i < str.length(); i++) {
            hash = (hash * seed) + (str.charAt(i));
        }

        hash = Math.abs(hash);
        return hash;
    }
*/

